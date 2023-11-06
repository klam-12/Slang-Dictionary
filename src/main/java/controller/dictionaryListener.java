package controller;

import view.dictionaryView;

import javax.accessibility.Accessible;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Khanh Lam
 */
public class dictionaryListener implements ActionListener {
    dictionaryView dictView = new dictionaryView();

    public dictionaryListener(dictionaryView dictView) {
        this.dictView = dictView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        System.out.println("Pressed " + src);


    }
}


