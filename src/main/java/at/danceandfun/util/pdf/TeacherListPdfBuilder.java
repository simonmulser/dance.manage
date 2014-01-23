package at.danceandfun.util.pdf;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Teacher;
import at.danceandfun.util.AbstractITextPdfView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TeacherListPdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Teacher> teacherList = (List<Teacher>) model.get("teacherList");

        // prepare font
        Font helvetica = PdfTemplateUtils.getDefaultSansFont();
        Font helveticaHeader = PdfTemplateUtils.getDefaultSansFont();
        helveticaHeader.setColor(BaseColor.WHITE);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        PdfPTable table = new PdfPTable(7);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 1.0f, 1.0f, 0.5f, 0.5f, 0.5f, 0.5f, 1.0f });
        table.setSpacingBefore(10);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Name", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Telefon", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Eintritt", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SVNR", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gehalt", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Stile", helveticaHeader));
        table.addCell(cell);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Teacher teacher : teacherList) {

            cell.setPhrase(new Phrase(teacher.getFirstname() + " "
                    + teacher.getLastname(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(teacher.getEmail(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(teacher.getTelephone(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(fmt.print(teacher.getEngagementDate()),
                    helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(teacher.getSvnr(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(formatter.format(teacher.getSalary()),
                    helvetica));
            table.addCell(cell);

            String courseList = "";
            boolean first = true;
            for (Course c : teacher.getCourses()) {
                if (!first) {
                    courseList += ", ";
                }
                courseList += c.getName();
                first = false;
            }
            cell.setPhrase(new Phrase(courseList, helvetica));
            table.addCell(cell);
        }
        doc.add(table);
    }
}
