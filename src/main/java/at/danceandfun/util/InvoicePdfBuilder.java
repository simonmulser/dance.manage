package at.danceandfun.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.danceandfun.entity.Invoice;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
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

        PdfContentByte cb = writer.getDirectContent();
        cb.setColorFill(new CMYKColor(0f, 0.376f, 1f, 0f));
        cb.rectangle(
                doc.leftMargin(),
                doc.getPageSize().getHeight() - doc.topMargin(),
                doc.getPageSize().getWidth() - doc.leftMargin()
                        - doc.rightMargin(), 5f);
        cb.fill();

        Paragraph studioName = new Paragraph("Dance & Fun");
        studioName.setAlignment(Element.ALIGN_RIGHT);
        doc.add(studioName);

        // PdfPTable table = new PdfPTable(5);
        // table.setWidthPercentage(100.0f);
        // table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f, 1.0f });
        // table.setSpacingBefore(10);

        doc.add(new Chunk(invoice.getParent().getFirstname() + " "
                + invoice.getParent().getLastname()));
        doc.add(Chunk.NEWLINE);
        doc.add(new Chunk(invoice.getParent().getAddress().getStreet()));

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);

        // // define table header cell
        // PdfPCell cell = new PdfPCell();
        // cell.setBackgroundColor(BaseColor.BLUE);
        // cell.setPadding(5);
        //
        // // write table header
        // cell.setPhrase(new Phrase("Book Title", font));
        // table.addCell(cell);
        //
        // cell.setPhrase(new Phrase("Author", font));
        // table.addCell(cell);
        //
        // cell.setPhrase(new Phrase("ISBN", font));
        // table.addCell(cell);
        //
        // cell.setPhrase(new Phrase("Published Date", font));
        // table.addCell(cell);
        //
        // cell.setPhrase(new Phrase("Price", font));
        // table.addCell(cell);

        // write table row data
        // for (Book aBook : listBooks) {
        // table.addCell(aBook.getTitle());
        // table.addCell(aBook.getAuthor());
        // table.addCell(aBook.getIsbn());
        // table.addCell(aBook.getPublishedDate());
        // table.addCell(String.valueOf(aBook.getPrice()));
        // }
        //
        // doc.add(table);
    }
}
