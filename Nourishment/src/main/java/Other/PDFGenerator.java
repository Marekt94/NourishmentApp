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
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marek
 */
public class PDFGenerator implements MyPDFGeneratorInterface{
    Document document = null;
    Integer spaceCount = 0;
    String path = "";
    ByteArrayOutputStream byteOutputStream = null;
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
        closeDocument(true);
    }
    
    @Override
    public void closeDocument(boolean saveFile) {
        document.close();
        if (saveFile) {
          saveToFile();            
        }
    }    

    @Override
    public void newPage() {
        document.newPage();
    }

    @Override
    public void openDocument(String path) {
        try {
            document = new Document();
            this.path = path;
            byteOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteOutputStream);
            
            document.open();
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

    @Override
    public void saveToFile() {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(byteOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }

    @Override
    public void fromString(String text) {
        try {
            byteOutputStream.reset();
            
            document = new Document();
            PdfWriter.getInstance(document, byteOutputStream);
            document.open();
            document.add(new Paragraph(text));
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString(){
        String text = "";
        try {
            PdfReader pdfReader = new PdfReader(byteOutputStream.toByteArray());
            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                text = text + PdfTextExtractor.getTextFromPage(pdfReader, i);
            }
        } catch (IOException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }
    
}
