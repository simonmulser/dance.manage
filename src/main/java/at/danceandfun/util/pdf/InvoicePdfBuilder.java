package at.danceandfun.util.pdf;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import at.danceandfun.entity.Invoice;
import at.danceandfun.entity.Position;
import at.danceandfun.util.AbstractITextPdfView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Idea from:
 * http://www.codejava.net/frameworks/spring/spring-web-mvc-with-pdf-view
 * -example-using-itext-5x
 * 
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 */
public class InvoicePdfBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // get data model which is passed by the Spring container
        Invoice invoice = (Invoice) model.get("invoice");

        // prepare font
        Font times = PdfTemplateUtils.getDefaultSerifFont();
        Font timesHeader = PdfTemplateUtils.getDefaultSerifFont();
        timesHeader.setColor(BaseColor.WHITE);

        PdfTemplateUtils.drawTemplateBars(doc, writer);

        Paragraph studioName = new Paragraph("dance.manage");
        studioName.setAlignment(Element.ALIGN_RIGHT);
        doc.add(studioName);

        PdfPTable table = new PdfPTable(5);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] { 0.5f, 3f, 2.0f, 2.0f, 1.0f });
        table.setSpacingBefore(10);

        // space before address
        PdfTemplateUtils.printNewLine(doc, 3);

        Paragraph p = new Paragraph("Dance & Fun", times);
        p.setAlignment(Element.ALIGN_RIGHT);
        doc.add(p);
        p = new Paragraph("Währingerstraße 125", times);
        p.setAlignment(Element.ALIGN_RIGHT);
        doc.add(p);
        p = new Paragraph("1180 Wien", times);
        p.setAlignment(Element.ALIGN_RIGHT);
        doc.add(p);

        PdfTemplateUtils.printNewLine(doc, 1);

        p = new Paragraph("Belegnummer: " + invoice.getIid(), times);
        p.setAlignment(Element.ALIGN_LEFT);
        doc.add(p);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
        p = new Paragraph(fmt.print(invoice.getDate()), times);
        p.setAlignment(Element.ALIGN_LEFT);
        doc.add(p);

        PdfTemplateUtils.printNewLine(doc, 1);

        // addressee
        if (invoice.getParent() != null) {
            PdfTemplateUtils.printAddress(doc, times, invoice.getParent());
        } else {
            PdfTemplateUtils.printAddress(doc, times, invoice.getParticipant());
        }

        PdfTemplateUtils.printNewLine(doc, 2);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Pos", timesHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Kurs", timesHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Einheit", timesHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Semester", timesHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Betrag", timesHeader));
        table.addCell(cell);

        // write table row data
        List<Position> positions = invoice.getPositions();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        int i = 1;
        for (Position pos : positions) {
            cell.setPhrase(new Phrase(Integer.toString(i), times));
            table.addCell(cell);
            cell.setPhrase(new Phrase(pos.getKey().getCourse().getName(), times));
            table.addCell(cell);
            cell.setPhrase(new Phrase(pos.getKey().getCourse().getDuration()
                    .getLabel()
                    + " Minuten", times));
            table.addCell(cell);
            cell.setPhrase(new Phrase(pos.getDuration().getLabel(), times));
            table.addCell(cell);
            cell.setPhrase(new Phrase(formatter.format(pos.getAmount()), times));
            table.addCell(cell);
            i++;
        }

        if (invoice.getReduction() != null) {
            cell.setPhrase(new Phrase(invoice.getReduction().toString()
                    + "% Rabatt", times));
            cell.setColspan(4);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            cell.setPhrase(new Phrase(formatter.format(invoice
                    .getReductionAmount()), times));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        }

        cell.setPhrase(new Phrase("20% MwSt", times));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell.setPhrase(new Phrase(formatter.format(invoice.getVatAmount()),
                times));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gesamt", times));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell.setPhrase(new Phrase(formatter.format(invoice.getTotalAmount()),
                times));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        doc.add(table);
    }
}
