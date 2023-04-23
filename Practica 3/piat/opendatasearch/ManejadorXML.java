package piat.opendatasearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Sara Jiménez Muñoz 51512521
 *
 */

/**
 * Clase estática, que debe implementar la interfaz ParserCatalogo
 * Hereda de DefaultHandler por lo que se deben sobrescribir sus métodos para
 * procesar documentos XML
 *
 */
public class ManejadorXML extends DefaultHandler implements ParserCatalogo {
	private String sNombreCategoria; // Nombre de la categoría
	private List<String> lConcepts; // Lista con los uris de los elementos <concept> que pertenecen a la categoría
	private Map<String, Map<String, String>> mDatasets; // Mapa con información de los dataset que pertenecen a la
														// categoría

	private StringBuilder sb = new StringBuilder();

	private String sCodigoConcepto;

	// provisional
	private boolean isConcepts;
	private boolean isDatasets;
	private boolean isConcept;
	private boolean isCode;
	private boolean isLabel;
	private boolean isDatasetAGuardarMapa;
	private boolean isConceptAGuardarLista;
	private boolean guardarNombreCategoria;
	private boolean isDataset;
	private boolean isTitle;
	private boolean isDescription;
	private boolean isTheme;

	private int nivel;
	private int nivelRetorno;

	private String idConcept;
	private String idDataset;
	private String title;
	private String description;
	private String theme;

	/**
	 * @param sCodigoConcepto código de la categoría a procesar
	 * @throws SAXException, ParserConfigurationException
	 */
	public ManejadorXML(String sCodigoConcepto) throws SAXException, ParserConfigurationException {
		this.sCodigoConcepto = sCodigoConcepto;

	}

	// ===========================================================
	// Métodos a implementar de la interfaz ParserCatalogo
	// ===========================================================

	/**
	 * <code><b>getConcepts</b></code>
	 * Devuelve una lista con información de los <code><b>concepts</b></code>
	 * resultantes de la búsqueda.
	 * <br>
	 * Cada uno de los elementos de la lista contiene la <code><em>URI</em></code>
	 * del <code>concept</code>
	 * 
	 * <br>
	 * Se considerarán pertinentes el <code><b>concept</b></code> cuyo código
	 * sea igual al criterio de búsqueda y todos sus <code>concept</code>
	 * descendientes.
	 * 
	 * @return
	 *         - List con la <em>URI</em> de los concepts pertinentes.
	 *         <br>
	 *         - null si no hay concepts pertinentes.
	 * 
	 */
	@Override
	public List<String> getConcepts() {
		// TODO

		return lConcepts;
	}

	public String getLabel() {
		return sNombreCategoria;
	}

	/**
	 * <code><b>getDatasets</b></code>
	 * 
	 * @return Mapa con información de los <code>dataset</code> resultantes de la
	 *         búsqueda.
	 *         <br>
	 *         Si no se ha realizado ninguna búsqueda o no hay dataset pertinentes
	 *         devolverá el valor <code>null</code>
	 *         <br>
	 *         Estructura de cada elemento del map:
	 *         <br>
	 *         . <b>key</b>: valor del atributo ID del elemento
	 *         <code>dataset</code>con la cadena de la <code><em>URI</em></code>
	 *         <br>
	 *         . <b>value</b>: Mapa con la información a extraer del
	 *         <code>dataset</code>. Cada <code>key</code> tomará los valores
	 *         <em>title</em>, <em>description</em> o <em>theme</em>, y
	 *         <code>value</code> sus correspondientes valores.
	 * 
	 * @return
	 *         - Map con información de los <code>dataset</code> resultantes de la
	 *         búsqueda.
	 *         <br>
	 *         - null si no hay datasets pertinentes.
	 */
	@Override
	public Map<String, Map<String, String>> getDatasets() {

		return mDatasets;
	}

	// ===========================================================
	// Métodos a implementar de SAX DocumentHandler
	// ===========================================================

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();

		System.out.println("Empieza el documento catalogo.xml ");

		isCode = false;
		isLabel = false;
		isConcepts = false;
		isConcept = false;
		isDatasets = false;
		isDatasetAGuardarMapa = false;
		isConceptAGuardarLista = false;
		guardarNombreCategoria = false;
		isDataset = false;
		isTitle = false;
		isDescription = false;
		isTheme = false;

		nivel = 0;

	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("Termina el documento ");

	}

	// empieza un elemento
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		switch (qName) { // depende del nombre del elemento

			case "concepts":
				isConcepts = true;
				break; // nivel no suma porque concepts va as er el nivel 0

			case "concept":
				idConcept = null;
				isConcept = true;
				nivel++;

				// caso en el que el concept no este dentro de un datashet
				if (!isDataset) {
					idConcept = attributes.getValue("id");

				}

				// caso en el que el concept este dentro de un dataset
				else {
					idConcept = attributes.getValue("id");
					if (lConcepts.contains(idConcept)) { // si el concept se encuentra en la lista de concepts se
															// marca para guardarlo mas tarde.
						isDatasetAGuardarMapa = true;
					}
				}
				break;

			case "label":
				isLabel = true;
				break;

			case "code":
				isCode = true;
				break;

			case "datasets":
				isDatasets = true;
				mDatasets = new HashMap<String, Map<String, String>>(); // se inicializa un nuevo mapa de datasets
				break;

			case "dataset":
				isDataset = true;
				idDataset = null;

				for (int i = 0; i < attributes.getLength(); i++) {
					idDataset = attributes.getValue(i); // TODO: mirar como funcona esto
				}
				break;

			case "title":
				isTitle = true;
				break;

			case "description":
				isDescription = true;
				break;

			case "theme":
				isTheme = true;
				break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);

		if (isCode) { // estamos dentro del concept
			if (sb.toString().trim().equals(sCodigoConcepto)) { // compara si el codigo de la busqueda y del concept en
																// el que se encuetnra son iguales
				isConceptAGuardarLista = true;
				guardarNombreCategoria = true;
				nivelRetorno = nivel;
				lConcepts.add(idConcept);
			}
		} else if (isLabel && isConceptAGuardarLista && guardarNombreCategoria) {
			sNombreCategoria = sb.toString().trim();
			guardarNombreCategoria = false;
		}

		if (isDataset && !isConcept) {
			if (isTitle) {
				title = sb.toString().trim();
			} else if (isDescription) {
				description = sb.toString().trim();
			} else if (isTheme) {
				theme = sb.toString().trim();
			}
		}

		switch (qName) {
			case "concepts":
				isConcepts = false;
				break;

			case "concept":
				isConcept = false;

				if (isConceptAGuardarLista) {
					if (!lConcepts.contains(idConcept) && !isDataset) {
						lConcepts.add(idConcept);
					}
					if (nivel <= nivelRetorno) {
						isConceptAGuardarLista = false;
					}
				}
				nivel--;
				break;

			case "code":
				isCode = false;
				break;

			case "label":
				isLabel = false;
				break;

			case "datasets":
				isDatasets = false;
				break;

			case "dataset":
				isDataset = false;
				if (isDatasetAGuardarMapa) {
					mDatasets.put(idDataset, new HashMap<String, String>());
					isDatasetAGuardarMapa = false;

					if (description != null) {
						mDatasets.get(idDataset).put("description", description);
					}

					if (title != null) {
						mDatasets.get(idDataset).put("title", title);
					}
					mDatasets.get(idDataset).put("title", title);
				}
				break;

			case "title":
				isTitle = false;
				break;

			case "description":
				isDescription = false;
				break;

			case "theme":
				isTheme = false;
				break;

		}
		sb.setLength(0); // se vacia el buffer
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);

		System.out.println("SAX Event: CHARACTERS[");
		sb.append(ch, start, length);

		System.out.println(sb.toString());
		System.out.println("]");
	}

}
