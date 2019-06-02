package es.uneatlantico.es.gdbd.reports;

public class FactoryReport {
	
	public static IReport getReport(ReportFormat reportType)
	{
		if (reportType == ReportFormat.PDF)
			return new PDFReport();
		if (reportType == ReportFormat.RTF)
			return new RTFReport();
		return null;
	}

}
