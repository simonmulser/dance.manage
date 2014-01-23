package at.danceandfun.util.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.danceandfun.entity.Parent;
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

public class ParentListPdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Parent> parentList = (List<Parent>) model.get("parentList");

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

        cell.setPhrase(new Phrase("Addresse", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PLZ", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Stadt", helveticaHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Kinder", helveticaHeader));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        for (Parent parent : parentList) {

            cell.setPhrase(new Phrase(parent.getFirstname() + " "
                    + parent.getLastname(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(parent.getEmail(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(parent.getTelephone(), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(parent.getAddress().getStreet(),
                    helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(Integer.toString(parent.getAddress()
                    .getZip()), helvetica));
            table.addCell(cell);

            cell.setPhrase(new Phrase(parent.getAddress().getCity(), helvetica));
            table.addCell(cell);

            String participantList = "";
            boolean first = true;
            for (Participant p : parent.getChildren()) {
                if (!first) {
                    participantList += ", ";
                }
                participantList += p.getFirstname();
                first = false;
            }
            cell.setPhrase(new Phrase(participantList, helvetica));
            table.addCell(cell);
        }
        doc.add(table);
    }
}
