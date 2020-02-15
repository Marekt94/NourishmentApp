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
    Font titleFont = FontFactory.getFont(FontFactory.COURIER, BaseFont.CP1250, BaseFont.CACHED, 16, Font.BOLD, BaseColor.BLACK); 
    Font subtitleFont = FontFactory.getFont(FontFactory.COURIER,BaseFont.CP1250, BaseFont.CACHED, 14, Font.BOLD);
    
    public PDFGenerator(){
        
    }
    
    @Override
    public void closeDocument() {
        document.close();    
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
            document.add(new Paragraph(text,titleFont));
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addSubtitle(String text) {
        try {
            document.add(new Paragraph(text, subtitleFont));
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addList(String[] text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
