package es.uneatlantico.es.gdbd.reports;

public class FactoryReport {
	
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
