library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity ctrl_tec is
port(clk           : in std_logic;
     nRst          : in std_logic;
     tic           : in std_logic;
     columna       : in std_logic_vector(3 downto 0);
     fila          : in std_logic_vector(3 downto 0);
     tecla_pulsada : buffer std_logic;
     tecla         : buffer std_logic_vector(3 downto 0);
     pulso_largo   : buffer std_logic);  
end entity;

architecture rtl of ctrl_tec is
------ SEÑALES PARA SINCRONIZACION Y ANTIREBOTES ------
  -- signal colr_1: std_logic_vector(3 downto 0);
  -- signal colr_2: std_logic_vector(3 downto 0);
-------------------------------------------------------
  signal cnt_tiempo_filas:   std_logic_vector(6 downto 0);        -- Tiene que contar hasta 125 para muestrear cada 1.25 ms
  signal cnt_fila:           std_logic_vector(1 downto 0);        -- Indica en que fila hay que muestrear
  signal reg_num_fila:       std_logic_vector(1 downto 0);        -- Indica que fila ha sido pulsada
  signal reg_valor_fila:     std_logic;                           -- Indica el valor de reg_num_fila
  
  signal reg_valor_col:      std_logic;
  signal reg_num_col:        std_logic_vector(1 downto 0);        -- Indica la columna que se ha pulsado
  
  signal cnt_pulso_largo:    std_logic_vector(8 downto 0);
  signal reg_pulso_largo:    std_logic;                           -- Se pone a '1' cuando se detecta que se ha pulsado una tecla durante 2 secs.
  
begin

  process(clk, nRst)
  begin
    if nRst = '0' then
      tecla_pulsada <= '0';
      tecla <= (others => '0');
      pulso_largo <= '0';
      
    elsif clk'event and clk = '1' then
      if reg_valor_col'event and reg_valor_col = '0' then
        tecla_pulsada <= '1';
      else 
        tecla_pulsada <= '0';
      end if;
    end if;

end process;

  pulso_largo <= '1' when reg_pulso_largo = '1' and reg_valor_col = '1'
                 else '0';
  
  tecla <= X"1" when reg_num_col = 0 and reg_num_fila = 0 else
           X"2" when reg_num_col = 1 and reg_num_fila = 0 else
           X"3" when reg_num_col = 2 and reg_num_fila = 0 else
           x"F" when reg_num_col = 3 and reg_num_fila = 0 else
           X"4" when reg_num_col = 0 and reg_num_fila = 1 else
           X"5" when reg_num_col = 1 and reg_num_fila = 1 else
           X"6" when reg_num_col = 2 and reg_num_fila = 1 else
           x"E" when reg_num_col = 3 and reg_num_fila = 1 else
           X"7" when reg_num_col = 0 and reg_num_fila = 2 else
           X"8" when reg_num_col = 1 and reg_num_fila = 2 else
           X"9" when reg_num_col = 2 and reg_num_fila = 2 else
           x"D" when reg_num_col = 3 and reg_num_fila = 2 else
           X"A" when reg_num_col = 0 and reg_num_fila = 3 else
           X"B" when reg_num_col = 2 and reg_num_fila = 3 else
           x"C" when reg_num_col = 3 and reg_num_fila = 3 else
           X"0";
           
  -- Comprueba que fila se pulsa
  process(clk, nRst)
  begin
    if nRst = '0' then
      cnt_tiempo_filas <= (others => '0');
      cnt_fila <= (others => '0');
      reg_num_fila <= (others=>'0');
      
    elsif clk'event and clk = '1' then
      if tic = '1' then
        cnt_tiempo_filas <= (others => '0');
        cnt_fila <= (others => '0');
      elsif cnt_tiempo_filas < 125 then
        cnt_tiempo_filas <= cnt_tiempo_filas + 1;
      else
        cnt_tiempo_filas <= (others => '0');
        if cnt_fila < 4 then
          cnt_fila <= cnt_fila + 1;
          if reg_valor_fila = '0' then              -- Cuando hay una tecla pulsada, registra la columna y no aumenta el contador
            reg_num_fila <= cnt_fila;
          end if;
        else 
          cnt_fila <= (others => '0');
        end if;
      end if;
    end if;
end process;

  reg_valor_fila <= fila(0) when cnt_fila = 0 else 
                    fila(1) when cnt_fila = 1 else 
                    fila(2) when cnt_fila = 2 else 
                    fila(3); 
                    
  reg_num_col <= "00" when columna = 0 and reg_valor_fila = '0' else
                 "01" when columna = 1 and reg_valor_fila = '0' else
                 "10" when columna = 2 and reg_valor_fila = '0' else
                 "11";
  
  process(clk, nRst)
  begin
    if nRst = '0' then
      reg_valor_col <= '0';
      
    elsif clk'event and clk = '1' then
      if reg_valor_fila = '0' and tic = '1' then         -- reg_valor_fila funciona como habilitacion: si vale 0 es que se ha pulsado una tecla y por tanto hay un valor para las filas
        reg_valor_col <= '1';
      end if;
    end if;
  end process;
  
  process(clk, nRst)
  begin
    if nRst = '0' then
      cnt_pulso_largo <= (others => '0');
      reg_pulso_largo <= '0';
    elsif clk'event and clk = '1' then
      if tic = '1' and reg_valor_col = '1' then
        if cnt_pulso_largo < 400 then
          cnt_pulso_largo <= cnt_pulso_largo + 1;
        else
          reg_pulso_largo <= '1';
          cnt_pulso_largo <= (others => '0');
        end if;
      else 
        reg_pulso_largo <= '0';
      end if;
    end if;
  end process;
end rtl; 