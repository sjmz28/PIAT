--------------------------------------------------------------------------------------------------
-- Autor: DTE
-- Version:3.0
-- Fecha: 17-02-2021
--------------------------------------------------------------------------------------------------
-- Estimulos para el test del controlador de teclado.
-- El reloj y el reset asíncrono se aplican directamente en elnivel superior de la jerarquia del
-- test
--------------------------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use work.pack_test_teclado.all;

entity estimulos is port(
  clk:            in std_logic;
  tic:            in std_logic;
  duracion_test:  buffer time;
  tecla_test:     buffer std_logic_vector(3 downto 0);
  tecla_id:       buffer std_logic_vector(3 downto 0);
  pulsar_tecla:   buffer std_logic
  );
end entity;

architecture test of estimulos is

constant duracion_pulsacion_corta:     time := 5000 ns; 
constant duracion_pulsacion_larga:     time := 7000 ns;
--constant duracion_test:     time := 5000 ns;

begin

stim: process
  begin
    tecla_id <= (others => '0');
    pulsar_tecla <= '0';
    wait for 30*T_CLK;
    wait until clk'event and clk = '1';
    -- Para completar por los estudiantes (inicio)
    -- Primera prueba, comprobar que todas las teclas funcionan en pulsacion corta mas larga (situacion extemo)
    pulsa_tecla(clk, x"A", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"B", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"C", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"D", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"E", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"F", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);

report "He terminado de comprobar las letras";

    pulsa_tecla(clk, x"0", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"1", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"2", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"3", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"4", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"5", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"6", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"7", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"8", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
    espera_TIC(clk, tic, 10);
    pulsa_tecla(clk, x"9", duracion_pulsacion_corta, tecla_test, duracion_test, pulsar_tecla);
---------------------------------------------------------------------------------------------

    -- Para completar por los estudiantes (fin) 
    assert(false) report "******************************Fin del test************************" severity failure;
  end process;

end test;