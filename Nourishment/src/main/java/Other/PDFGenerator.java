/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Interfaces.MyPDFGeneratorInterface;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marek
 */
public class PDFGenerator implements MyPDFGeneratorInterface{
    Document document = null;
    Integer spaceCount = 0;
    Font titleFont    = FontFactory.getFont(FontFactory.COURIER, BaseFont.CP1250, BaseFont.CACHED, 16, Font.BOLD, BaseColor.BLACK); 
    Font subtitleFont = FontFactory.getFont(FontFactory.COURIER, BaseFont.CP1250, BaseFont.CACHED, 13, Font.BOLD);
    Font listFont     = FontFactory.getFont(FontFactory.COURIER, BaseFont.CP1250, BaseFont.CACHED, 11, Font.NORMAL);
    Font normalFont   = FontFactory.getFont(FontFactory.COURIER, BaseFont.CP1250, BaseFont.CACHED, 11, Font.NORMAL);
    
    public PDFGenerator(){
        
    }
    
    private String addSpacesToLine(String line){
        String spaces = "";
        if (spaceCount == 0){
            return line;
        }
        for (int i = 0; i < spaceCount; i++) {
            spaces = spaces + " ";
        }
        return spaces + line;
    }

    @Override
    public MyPDFGeneratorInterface withSpaces(Integer spaceCount) {
        this.spaceCount = spaceCount;
        return this;
    }
    
    @Override
    public void closeDocument() {
        document.close();    
    }

    @Override
    public void newPage() {
        document.newPage();
    }

    @Override
    public void openDocument(String path) {
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            
            document.open();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addTitle(String text) {
        try {
            document.add(new Paragraph(addSpacesToLine(text),titleFont));
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addSubtitle(String text) {
        try {
            document.add(new Paragraph(addSpacesToLine(text), subtitleFont));
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addList(String[] text) {
        try {
            Paragraph paragraph = new Paragraph();
            paragraph.setFont(listFont);
            for (String line : text) {
                line = line.replace("\n", "\n  ");
                line = line.trim();
                paragraph.add(addSpacesToLine(" - " + line + "\n"));
            }
            document.add(paragraph);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addParagraph(String text) {
        try {
            document.add(new Paragraph(addSpacesToLine(text), normalFont));
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
