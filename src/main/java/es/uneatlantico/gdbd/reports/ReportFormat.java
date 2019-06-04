package es.uneatlantico.es.gdbd.reports;

/**
 * Contains the values of the supported export document types of the application
 * @author Alberto Gutiérrez Arroyo
 */
public enum ReportFormat {
	/**
	 * Portable Document Format (PDF)
	 */
	PDF,
	/**
	 * HyperText Markup Language (HTML)
	 */
	HTML,
	// Compatibility problems with Word files (Won't show some fields)
//	/**
//	 * Office Open XML document (DOCX)
//	 */
//	DOCX,
	/**
	 * Rich text document (RTF)
	 */
	RTF
}
