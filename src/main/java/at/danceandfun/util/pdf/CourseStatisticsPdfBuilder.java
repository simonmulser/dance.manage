package at.danceandfun.util.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.danceandfun.entity.Course;
import at.danceandfun.util.AbstractITextPdfView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CourseStatisticsPdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Course> statistics = (List<Course>) model.get("statistics");

        // prepare font
        Font helvetica = PdfTemplateUtils.getDefaultSansFont();
        Font helveticaHeader = PdfTemplateUtils.getDefaultSansFont();
        helveticaHeader.setColor(BaseColor.WHITE);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 2.0f, 1.5f, 1.0f, 0.5f });
        table.setSpacingBefore(10);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Kurs", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Lehrer", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Teilnehmer", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Jahr", helveticaHeader));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Course course : statistics) {

            cell.setPhrase(new Phrase(course.getName(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(course.getTeacher().getFirstname() + " "
                    + course.getTeacher().getLastname(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(Integer.toString(course
                    .getCourseParticipants().size()), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(Integer.toString(course.getYear()),
                    helvetica));
            table.addCell(cell);
        }
        doc.add(table);
    }
}
