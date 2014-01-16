package at.danceandfun.util;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.danceandfun.entity.Invoice;
import at.danceandfun.entity.Person;
import at.danceandfun.entity.Position;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
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
		Font times = FontFactory.getFont(FontFactory.TIMES);
		times.setSize(11);
		Font timesHeader = FontFactory.getFont(FontFactory.TIMES);
		timesHeader.setSize(11);
		timesHeader.setColor(BaseColor.WHITE);

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

		Paragraph studioName = new Paragraph("Dance & Fun");
		studioName.setAlignment(Element.ALIGN_RIGHT);
		doc.add(studioName);

		PdfPTable table = new PdfPTable(5);
		table.getDefaultCell().setBorder(0);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 0.5f, 3f, 2.0f, 2.0f, 1.0f });
		table.setSpacingBefore(10);

		// space before address
		printNewLine(doc, 4);

		// addressee
		if (invoice.getParent() != null) {
			printAddress(doc, times, invoice.getParent());
		} else {
			printAddress(doc, times, invoice.getParticipant());
		}

		printNewLine(doc, 2);

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

	public static void printNewLine(Document doc, int count)
			throws DocumentException {
		for (int i = 0; i < count; i++) {
			doc.add(Chunk.NEWLINE);
		}
	}
}
