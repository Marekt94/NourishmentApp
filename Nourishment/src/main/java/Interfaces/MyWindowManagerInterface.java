/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.List;

/**
 *
 * @author Marek
 */
public interface MyWindowManagerInterface {
    public <E> void unpackWindow(List<E> objectList);
    public <E> void unpackWindow(E object);
}
