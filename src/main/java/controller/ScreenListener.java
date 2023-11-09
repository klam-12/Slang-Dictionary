package controller;

import view.dictionaryView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Khanh Lam
 */
public class ScreenListener implements ChangeListener, WindowListener {
    dictionaryView dictView;

    public ScreenListener(dictionaryView dictView) {
        this.dictView = dictView;
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dictView.saveHistory();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        this.dictView.dispose();
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JTabbedPane pane) {
            System.out.println("Selected paneNo : " + pane.getSelectedIndex());
            int index = pane.getSelectedIndex();
            // History tab
            if(index == 1) {
                this.dictView.reloadHistoryTextArea();
            }

        }
    }
}
