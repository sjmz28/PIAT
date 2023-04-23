package piat.opendatasearch;

import java.util.List;

/**
 * @author Sara Jiménez Muñoz 51512521
 *
 */

/**
 * Clase estática para crear un String que contenga el documento xml a partir de
 * la información almacenadas en las colecciones
 *
 */
public class GenerarXML {

	private static final String conceptPattern = "\n\t\t\t<concept> #ID# </concept>";

	/**
	 * Método que deberá ser invocado desde el programa principal
	 * 
	 * @param Colecciones con la información obtenida del documento XML de entrada
	 * @return String con el documento XML de salida
	 */
	public static String generar(List<String> lConcepts) {
		StringBuilder sbSalida = new StringBuilder();
		sbSalida.append("\n\t\t<concepts>");
		for (String unConcepto : lConcepts) {
			sbSalida.append(conceptPattern.replace("#ID#", unConcepto));
		}
		sbSalida.append("\n\t\t</concepts>");

		return sbSalida.toString();

	}

}
