package at.danceandfun.util.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

public class ParticipantListPdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Participant> participantList = (List<Participant>) model
                .get("participantList");

        // prepare font
        Font helvetica = PdfTemplateUtils.getDefaultSansFont();
        Font helveticaHeader = PdfTemplateUtils.getDefaultSansFont();
        helveticaHeader.setColor(BaseColor.WHITE);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        PdfPTable table = new PdfPTable(7);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 1.0f, 1.0f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f });
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

        cell.setPhrase(new Phrase("Geburtsdatum", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Addresse", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PLZ", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Stadt", helveticaHeader));
        table.addCell(cell);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Participant participant : participantList) {

            cell.setPhrase(new Phrase(participant.getFirstname() + " "
                    + participant.getLastname(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(participant.getEmail(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(participant.getTelephone(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(fmt.print(participant.getBirthday()),
                    helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(participant.getAddress().getStreet(),
                    helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(Integer.toString(participant.getAddress()
                    .getZip()), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(participant.getAddress().getCity(),
                    helvetica));
            table.addCell(cell);
        }
        doc.add(table);
    }
}
