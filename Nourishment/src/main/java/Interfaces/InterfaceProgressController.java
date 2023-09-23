/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author komputer1
 */
public interface InterfaceProgressController {
    
    public void run();
    public void end();
    public void setMax(int max);
    public void setProgress(int i);
    public void setText(String text);
    public boolean getStop();
}
