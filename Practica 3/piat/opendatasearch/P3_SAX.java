package piat.opendatasearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * @author Sara Jimenez Muñoz 51512521L
 *
 */

/**
 * Clase principal de la aplicación de extracción de información del
 * Portal de Datos Abiertos del Ayuntamiento de Madrid
 *
 */
public class P3_SAX {

	private static final String argumento0 = "^\\d{3,4}(-[A-Z0-9]{3,8})?$";
	private static final String argumento1 = ".*\\.xml$";
	private static final String argumento2 = ".*\\.xsd$";
	private static final String argumento3 = ".*\\.txt$";

	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		// *********************************************************************************
		// */
		// PASO 1: Verificar nº de argumentos correcto

		if (args.length != 4) {// se asegura de que se han introducido 4 argumentos
			String mensaje = "ERROR: Argumentos incorrectos.";
			if (args.length > 0)
				mensaje += " He recibido estos argumentos: " + Arrays.asList(args).toString() + "\n";
			mostrarUso(mensaje);
			System.exit(1);
		}
		// verifica que los argumentos tienen la estructura correcta
		if (args[0].matches(argumento0) &&
				args[1].matches(argumento1) &&
				args[2].matches(argumento2) &&
				args[3].matches(argumento3)) {
			System.out.println("Los argumentos son correctos");
		} else {
			System.out.println("Los argumentos no son correctos");
		}
		// verificar que el argumento 1 y 2 corresponde con el path de un fichero
		// que tiene permiso de lectura
		String path1 = args[1];
		String path2 = args[2];
		File file1 = new File(path1);
		File file2 = new File(path2);

		if (file1.canRead() && file2.canRead()) {
			System.out.println("Se tiene permiso de lectura sobre el archivo");
		} else {
			System.out.println("NO se tiene permiso de lectura sobre el archivo");
		}

		// verificar que el argumento 3 corresponde con el path de un fichero que
		// tiene permiso de escritura

		String path3 = args[3];
		File ficheroSalida = new File(path3);

		if (ficheroSalida.canWrite()) {
			System.out.println("Se tiene permiso de escritura sobre el archivo");
		} else {
			System.out.println("NO se tiene permiso de escritura sobre el archivo");
		}
		// ***********************************************************************************
		// */
		ManejadorXML manejadorXML = new ManejadorXML(args[0]);

		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

		saxParser.parse(file1, manejadorXML);

		List<String> listaConcpets = new ArrayList<String>();

		listaConcpets = manejadorXML.getConcepts();

		System.out.println(listaConcpets + " llegue hasta aqui"); // PARA DEBUGUEAR

		Map<String, Map<String, String>> mapaDatashet = manejadorXML.getDatasets();

		// Volcar al fichero de salida los datos en el formato XML especificado por
		// ResultadosBusquedaP3.xsd
		// GenerarXML.generar();
		// TODO
		/*
		 * Validar los argumentos recibidos en main() HECHO
		 * 
		 * Instanciar un objeto ManejadorXML pasando como parámetro el código de la
		 * categoría recibido en el primer argumento de main() HECHO
		 * 
		 * Instanciar un objeto SAXParser e invocar a su método parse() pasando como
		 * parámetro un descriptor de fichero, cuyo nombre se recibió en el primer
		 * argumento de main(), y la instancia del objeto ManejadorXML HECHO
		 * 
		 * Invocar al método getConcepts() del objeto ManejadorXML para obtener un
		 * List<String> con las uris de los elementos <concept> cuyo elemento <code>
		 * contiene el código de la categoría buscado HECHO
		 * 
		 * Invocar al método getDatasets() del objeto ManejadorXML para obtener un mapa
		 * con los datasets de la categoría buscada HECHO
		 * 
		 * Crear el fichero de salida con el nombre recibido en el cuarto argumento de
		 * main() (HECHO YA EN EL PASO DE VALIDAR )
		 * 
		 * Volcar al fichero de salida los datos en el formato XML especificado por
		 * ResultadosBusquedaP3.xsd
		 * 
		 * Validar el fichero generado con el esquema recibido en el tercer argumento de
		 * main()
		 */

		System.exit(0);
	}

	/**
	 * Muestra mensaje de los argumentos esperados por la aplicación.
	 * Deberá invocase en la fase de validación ante la detección de algún fallo
	 *
	 * @param mensaje Mensaje adicional informativo (null si no se desea)
	 */
	private static void mostrarUso(String mensaje) {
		Class<? extends Object> thisClass = new Object() {
		}.getClass();

		if (mensaje != null)
			System.err.println(mensaje + "\n");
		System.err.println(
				"Uso: " + thisClass.getEnclosingClass().getCanonicalName()
						+ " <códigoCategoría> <ficheroCatalogo> <ficheroXSDsalida> <ficheroXMLSalida>\n" +
						"donde:\n" +
						"\t códigoCategoría:\t código de la categoría de la que se desea obtener datos\n" +
						"\t ficheroCatalogo:\t path al fichero XML con el catálogo de datos\n" +
						"\t ficheroXSDsalida:\t nombre del fichero que contiene el esquema contra el que se tiene que validar el documento XML de salida\n"
						+
						"\t ficheroXMLSalida:\t nombre del fichero XML de salida\n");
	}

}
