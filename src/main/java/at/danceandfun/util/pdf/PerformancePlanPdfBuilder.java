package at.danceandfun.util.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import at.danceandfun.entity.Course;
import at.danceandfun.util.AbstractITextPdfView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PerformancePlanPdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Course> performanceList1 = (List<Course>) model
                .get("performanceList1");
        @SuppressWarnings("unchecked")
        List<Course> performanceList2 = (List<Course>) model
                .get("performanceList2");
        @SuppressWarnings("unchecked")
        List<Course> performanceList3 = (List<Course>) model
                .get("performanceList3");

        LocalDate dateTime = (LocalDate) model.get("dateTime");

        // prepare font
        Font helvetica = PdfTemplateUtils.getDefaultSansFont();
        Font helveticaHeader = PdfTemplateUtils.getDefaultSansFont();
        helveticaHeader.setColor(BaseColor.WHITE);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
        String date = fmt.print(dateTime);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        printPerformanceTable(doc, helvetica, helveticaHeader, "Saal 1 - "
                + date, performanceList1);
        doc.newPage();

        PdfTemplateUtils.drawTemplateBars(doc, writer);
        printPerformanceTable(doc, helvetica, helveticaHeader, "Saal 2 - "
                + date, performanceList2);
        doc.newPage();

        PdfTemplateUtils.drawTemplateBars(doc, writer);
        printPerformanceTable(doc, helvetica, helveticaHeader, "Saal 3 - "
                + date, performanceList3);
    }

    private void printPerformanceTable(Document doc, Font font,
            Font headerFont, String header, List<Course> performanceList)
            throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase(header, headerFont));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Course course : performanceList) {

            cell.setPhrase(new Phrase(course.getName(), font));
            table.addCell(cell);
        }
        doc.add(table);
    }
}
