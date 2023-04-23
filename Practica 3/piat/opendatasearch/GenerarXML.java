package piat.opendatasearch;

import java.util.HashMap;
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

	private String sXMLIntro = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<searchResults" + "/t" + "xmlns=\"http://www.piat.dte.upm.es/ResultadosBusquedaP3.xsd\" \r\n" +
			"xmls:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \r\n" +
			"xsi:schemaLocation=\"http://www.piat.dte.upm.es/ResultadosBusquedaP3.xsd ResultadosBusquedaP3.xsd \" >\n" +
			"<summary>" + "\n" +
			"<query> #QUERY# </query>" + "\n" +
			"<numConcepts> #NUMCONCEPTS# </numConcepts>" + "\n" +
			"<numDatasets> #NUMDATASETS# </numDatasets>" + "\n" +
			"</summary>" + "\n";

	private String sXMLResult = "<results>\n" +
			"<concepts>\n" + "#CONCEPTS#" + "\n" +
			"</concepts>\n" +
			"<datasets>\n" + "#DATASETS#" + "\n" +
			"</datasets>\n" +
			"</results>\n" +
			"</searchResults>";

	private String sXMLConcept = "<concept id = \"" + "#ID#" + "\">\n";

	private String sXMLDataset = "<dataset id = \"" + "IDDATASET#" + "\">\n" +
			"#CONTENIDODATASET#" + "\t\t</dataset>\n";

	private String sXMLElemento = "<#ELEMENTO> #CONTENIDOELEMENTO# </#ELEMENTO>\n";

	/**
	 * 
	 * Método que deberá ser invocado desde el programa principal
	 * 
	 * @param Colecciones con la información obtenida del documento XML de entrada
	 * @return String con el documento XML de salida
	 */
	private String conceptsOuput(List<String> lConcepts) {
		// CONCEPTS OUTPUT

		StringBuilder sbSalida = new StringBuilder();
		sbSalida.append("\n\t\t<concepts>");

		for (String unConcepto : lConcepts) {
			sbSalida.append("\t" + conceptPattern.replace("#IDCONCEPT#", unConcepto));
		}
		sbSalida.append("\n\t\t</concepts>");

		return sbSalida.toString();
	}

	private String datasetOutput (Map <String, HashMap<String, String>> mDatasets){
		// DATASETS OUTPUT
		StringBuilder sbTotalDataset = new StringBuilder();
		StringBuilder sbDataset = new StringBuilder();
		String contenido;

		for(Strinf idDataset : mDatasets.keySet()) {

		//TITLE
			if(mDatasets.get(idDataset).containsKey("title")) {
				sbDataset.append("\t\t" + sXMLElemento.replace("#ELEMENTO#", "title").replace("#CONTENIDOELEMENTO#", mDatasets.get(idDataset).get("title")));
			}
			else{
				sbDataset.append("\t\t" + sXMLElemento.replace("#ELEMENTO#", "title").replace("#CONTENIDOELEMENTO#", " "));
			}
		
		//DESCRIPTION
			if(mDatasets.get(idDataset).containsKey("description")) {
				sbDataset.append("\t\t" + sXMLElemento.replace("#ELEMENTO#", "description").replace("#CONTENIDOELEMENTO#", mDatasets.get(idDataset).get("description")));
			}
		
		//THEME
			if(mDatasets.get(idDataset).containsKey("theme")) {
				sbDataset.append("\t\t" + sXMLElemento.replace("#ELEMENTO#", "theme").replace("#CONTENIDOELEMENTO#", mDatasets.get(idDataset).get("theme")));
			}

		contenido = sbDataset.toString();
		sbDataset.setLength(0);
		sbTotalDataset.append(sXMLDataset.replace("#IDDATASET#", idDataset).replace("#CONTENIDODATASET#", contenido));

		return sbTotalDataset.toString();
		}

	public String generar(Map<String, HashMap<String, String>> mDatasets, List<String> lConcepts, String codigo,
			String contenido) {

		StringBuilder sbSalida = new StringBuilder();

		sbSalida.append(sXMLIntro.replace("#QUERY#", codigo).replace("#NUMCONCEPTS#", String.valueOf(lConcepts.size()))
				.replace("#NUMDATASETS#", String.valueOf(mDatasets.size())));

		sbSalida.append(sXMLResult.replace("#CONCEPTS#", conceptsOuput(lConcepts))
				.replace("#DATASETS#", datasetOutput(mDatasets)).replace("#CONTENIDODATASET#", contenido));

		return sbSalida.toString();
	}

}
