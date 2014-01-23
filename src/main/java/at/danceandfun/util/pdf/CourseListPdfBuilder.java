package at.danceandfun.util.pdf;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

public class CourseListPdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Course> courseList = (List<Course>) model.get("courseList");

        // prepare font
        Font helvetica = PdfTemplateUtils.getDefaultSansFont();
        Font helveticaHeader = PdfTemplateUtils.getDefaultSansFont();
        helveticaHeader.setColor(BaseColor.WHITE);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        PdfPTable table = new PdfPTable(8);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 1.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f,
                0.5f });
        table.setSpacingBefore(10);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Kurs", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Preis / Semester", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Preis / Jahr", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Termin", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Dauer", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Zuschauer", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Aufführungen", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Lehrer", helveticaHeader));
        table.addCell(cell);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Course course : courseList) {

            cell.setPhrase(new Phrase(course.getName(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(formatter.format(course
                    .getSemesterPrice()), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(formatter.format(course.getYearPrice()),
                    helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(course.getWeekday().getLabel() + ", "
                    + fmt.print(course.getTime()), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(course.getDuration().getLabel(),
                    helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(course.getEstimatedSpectators()
                    .getLabel(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(Integer.toString(course
                    .getAmountPerformances()), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(course.getTeacher().getFirstname(),
                    helvetica));
            table.addCell(cell);
        }
        doc.add(table);
    }
}
