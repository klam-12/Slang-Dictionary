
package controller;

import view.dictionaryView;

import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Khanh Lam
 */
public class MenuListener implements javax.swing.event.MenuListener {
    dictionaryView dictView;

    public MenuListener(dictionaryView dictView) {
        this.dictView = dictView;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        String src = e.getSource().toString();
        System.out.println("Selected" + src);
        this.dictView.setSearchSrc();
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        String src = e.getSource().toString();
        System.out.println("Deselected" + src);
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        String src = e.getSource().toString();
        System.out.println("Canceled" + src);
    }
}