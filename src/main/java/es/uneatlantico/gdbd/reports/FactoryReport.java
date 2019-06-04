package es.uneatlantico.es.gdbd.reports;

public class FactoryReport {
	
	/**
	 * Obtains an implementation of the IReport interface given a report type defined on the <code>ReportType</code> enum
	 * @param reportType  the type of report defined on <code>ReportType</code>
	 * @return  the implementation of IReport
	 */
	public static IReport getReport(ReportFormat reportType)
	{
		if (reportType == ReportFormat.PDF)
			return new PDFReport();
		else if (reportType == ReportFormat.RTF)
			return new RTFReport();
		else if (reportType == ReportFormat.HTML)
			return new HTMLReport();
//		else if (reportType == ReportFormat.DOCX)
//			return new DOCXReport();
		return null;
	}

}
