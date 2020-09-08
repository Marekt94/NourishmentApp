/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author Marek
 */
public interface MyPDFGeneratorInterface {
    public void openDocument(String path);
    public void addTitle(String text);
    public void addSubtitle(String text);
    public void addList(String[] text);
    public void addParagraph(String text);
    public void closeDocument();
    public void newPage();
}
