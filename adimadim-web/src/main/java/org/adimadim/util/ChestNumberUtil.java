/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 * @author Adem
 */
public class ChestNumberUtil {
    
    public static ByteArrayOutputStream createChestNumberDocument(Integer chestNumber, String name) throws DocumentException, BadElementException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(/*PageSize.LETTER.rotate()*/);
        document.setPageSize(PageSize.A4);
        document.setMargins(0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        document.add(createTable(writer, chestNumber, name));
        document.add(createSeparatorTable());
        document.add(createTable(writer, chestNumber, name));

        document.close();

        return baos;
    }

    private static PdfPTable createSeparatorTable() throws BadElementException, MalformedURLException, IOException, DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{30, 220});

        table.addCell(createMakas());
        table.addCell(createDottedLine());

        return table;
    }

    private static PdfPCell createMakas() throws BadElementException, MalformedURLException, IOException {
        Image makas = Image.getInstance(ChestNumberUtil.class.getResource("/org/adimadim/image/makas.png"));
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(15);
        cell.setPaddingLeft(40);
        cell.setPaddingTop(25);
        cell.setPaddingRight(0);
        cell.setFixedHeight(60);
        cell.addElement(makas);

        return cell;
    }

    private static PdfPCell createDottedLine() throws BadElementException, MalformedURLException, IOException {
        String line = "";
        for (int i = 0; i < 120; i++) {
            line += "-";
        }

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(10);
        cell.setPaddingLeft(0);
        cell.setPaddingTop(20);
        cell.setPaddingRight(20);
        cell.setFixedHeight(60);
        cell.addElement(new Phrase(line));

        return cell;
    }

    private static PdfPTable createTable(PdfWriter writer,Integer chestNumber, String name) throws BadElementException, MalformedURLException, IOException, DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{50, 100, 100});

        table.addCell(createAdimAdimKosuLogo());
        table.addCell(createAdimAdimLogo());
        table.addCell(createAsicsLogo());
        table.addCell(createNumber(chestNumber));
        table.addCell(createBarcode(writer, chestNumber));
        table.addCell(createRunnerName(name));
        table.addCell(createBarcode(writer, chestNumber));

        return table;
    }

    private static PdfPCell createAdimAdimKosuLogo() throws BadElementException, MalformedURLException, IOException {
        Image adimAdimKosuLogo = Image.getInstance(ChestNumberUtil.class.getResource("/org/adimadim/image/adimadimkosu-logo.png"));

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setRowspan(2);
        cell.setPaddingTop(25);
        cell.setPaddingLeft(25);
        cell.setPaddingRight(25);
        cell.setFixedHeight(275);
        cell.addElement(adimAdimKosuLogo);

        return cell;
    }

    private static PdfPCell createAdimAdimLogo() throws BadElementException, MalformedURLException, IOException {
        Image adimAdimLogo = Image.getInstance(ChestNumberUtil.class.getResource("/org/adimadim/image/adimadim-logo.png"));

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(25);
        cell.setPaddingLeft(75);
        cell.setPaddingRight(25);
        cell.setFixedHeight(100);
        cell.addElement(adimAdimLogo);

        return cell;
    }

    private static PdfPCell createAsicsLogo() throws BadElementException, MalformedURLException, IOException {
        Image nbLogo = Image.getInstance(ChestNumberUtil.class.getResource("/org/adimadim/image/nb-logo.png"));

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(25);
        cell.setPaddingLeft(50);
        cell.setPaddingRight(50);
        cell.setFixedHeight(100);
        cell.addElement(nbLogo);

        return cell;
    }

    private static PdfPCell createNumber(Integer chestNumber) throws BadElementException, MalformedURLException, IOException {
        PdfPCell cell = new PdfPCell(new Phrase(chestNumber.toString(), FontFactory.getFont(FontFactory.HELVETICA, 170)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        cell.setFixedHeight(175);

        return cell;
    }

    private static PdfPCell createRunnerName(String name) throws BadElementException, MalformedURLException, IOException {
        PdfPCell cell = new PdfPCell(new Phrase(name, FontFactory.getFont(FontFactory.HELVETICA, "cp1254", 25)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(100);
        cell.setPaddingTop(25);

        return cell;
    }

    private static PdfPCell createBarcode(PdfWriter writer, Integer chestNumber) throws BadElementException, MalformedURLException, IOException {
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCodeType(Barcode128.EAN8);
        String strChestBumber = chestNumber.toString();
        while (strChestBumber.length() < 3) {
            strChestBumber = "0" + strChestBumber;
        }
        barcode128.setCode(strChestBumber);

        Image barcode = barcode128.createImageWithBarcode(writer.getDirectContent(), null, null);
        barcode.setAbsolutePosition(100f, 100);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(100);
        cell.setPaddingRight(0);
        cell.setPaddingLeft(50);
        cell.setPaddingTop(25);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.addElement(barcode);

        return cell;
    }
}
