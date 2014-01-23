package at.danceandfun.util.pdf;

import at.danceandfun.entity.Person;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfTemplateUtils {

    public static void drawTemplateBars(Document doc, PdfWriter writer) {
        // draw upper bar
        PdfContentByte cb = writer.getDirectContent();
        cb.setColorFill(new CMYKColor(0f, 0.376f, 1f, 0f));
        cb.rectangle(
                doc.leftMargin(),
                doc.getPageSize().getHeight() - doc.topMargin(),
                doc.getPageSize().getWidth() - doc.leftMargin()
                        - doc.rightMargin(), 5f);
        cb.fill();

        // draw lower bar
        cb = writer.getDirectContent();
        cb.setColorFill(new CMYKColor(0f, 0.376f, 1f, 0f));
        cb.rectangle(doc.leftMargin(), doc.bottomMargin(), doc.getPageSize()
                .getWidth() - doc.leftMargin() - doc.rightMargin(), 5f);
        cb.fill();
    }

    public static void printNewLine(Document doc, int count)
            throws DocumentException {
        for (int i = 0; i < count; i++) {
            doc.add(Chunk.NEWLINE);
        }
    }

    public static void printAddress(Document doc, Font font, Person person)
            throws DocumentException {
        doc.add(new Chunk(person.getFirstname() + " " + person.getLastname(),
                font));
        doc.add(Chunk.NEWLINE);
        if (person.getAddress().getStreet() != null
                && person.getAddress().getNumber() != null
                && person.getAddress().getStair() != null
                && person.getAddress().getDoor() != null) {
            doc.add(new Chunk(person.getAddress().getStreet() + " "
                    + person.getAddress().getNumber() + "/"
                    + person.getAddress().getStair() + "/"
                    + person.getAddress().getDoor(), font));
        } else if (person.getAddress().getStreet() != null
                && person.getAddress().getNumber() != null
                && person.getAddress().getDoor() != null) {
            doc.add(new Chunk(person.getAddress().getStreet() + " "
                    + person.getAddress().getNumber() + "/"
                    + person.getAddress().getDoor(), font));
        } else if (person.getAddress().getStreet() != null
                && person.getAddress().getNumber() != null) {
            doc.add(new Chunk(person.getAddress().getStreet() + " "
                    + person.getAddress().getNumber(), font));
        }
        doc.add(Chunk.NEWLINE);
        doc.add(new Chunk(person.getAddress().getZip() + " "
                + person.getAddress().getCity(), font));
    }

    public static void printAddress(Document doc, Person person)
            throws DocumentException {
        doc.add(new Chunk(person.getFirstname() + " " + person.getLastname()));
        doc.add(Chunk.NEWLINE);
        if (person.getAddress().getStreet() != null
                && person.getAddress().getNumber() != null
                && person.getAddress().getStair() != null
                && person.getAddress().getDoor() != null) {
            doc.add(new Chunk(person.getAddress().getStreet() + " "
                    + person.getAddress().getNumber() + "/"
                    + person.getAddress().getStair() + "/"
                    + person.getAddress().getDoor()));
        } else if (person.getAddress().getStreet() != null
                && person.getAddress().getNumber() != null
                && person.getAddress().getDoor() != null) {
            doc.add(new Chunk(person.getAddress().getStreet() + " "
                    + person.getAddress().getNumber() + "/"
                    + person.getAddress().getDoor()));
        } else if (person.getAddress().getStreet() != null
                && person.getAddress().getNumber() != null) {
            doc.add(new Chunk(person.getAddress().getStreet() + " "
                    + person.getAddress().getNumber()));
        }
        doc.add(Chunk.NEWLINE);
        doc.add(new Chunk(person.getAddress().getZip() + " "
                + person.getAddress().getCity()));
    }

    public static Font getDefaultSansFont() {
        Font times = FontFactory.getFont(FontFactory.HELVETICA);
        times.setSize(11);
        return times;
    }

    public static Font getDefaultSerifFont() {
        Font times = FontFactory.getFont(FontFactory.TIMES);
        times.setSize(11);
        return times;
    }
}
