package at.danceandfun.util.pdf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.util.AbstractITextPdfView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ParticipantsByNumberOfSilblingsPdfBuilder extends
        AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Participant> participants = (List<Participant>) model
                .get("participantsByNumberOfSiblings");

        // prepare font
        Font helvetica = PdfTemplateUtils.getDefaultSansFont();
        Font helveticaHeader = PdfTemplateUtils.getDefaultSansFont();
        helveticaHeader.setColor(BaseColor.WHITE);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 1.5f, 1.5f, 3.0f, 4.0f });
        table.setSpacingBefore(10);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Vorname", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nachname", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Geschwister", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Kurse", helveticaHeader));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Participant participant : participants) {
            Set<Participant> silblings = participant.getSiblings();

            if (!silblings.isEmpty()) {
                cell.setPhrase(new Phrase(participant.getFirstname(), helvetica));
                table.addCell(cell);

                cell.setPhrase(new Phrase(participant.getLastname(), helvetica));
                table.addCell(cell);

                String silblingList = Integer.toString(silblings.size())
                        + " - (";
                boolean first = true;
                for (Participant p : silblings) {
                    if (!first) {
                        silblingList += ", ";
                    }
                    silblingList += p.getFirstname();
                    first = false;
                }
                silblingList += ")";
                cell.setPhrase(new Phrase(silblingList, helvetica));
                table.addCell(cell);

                String courseList = "";
                first = true;
                for (CourseParticipant cp : participant.getCourseParticipants()) {
                    if (!first) {
                        courseList += ", ";
                    }
                    courseList += cp.getCourse().getName();
                    first = false;
                }
                cell.setPhrase(new Phrase(courseList, helvetica));
                table.addCell(cell);
            }
        }
        doc.add(table);
    }
}
