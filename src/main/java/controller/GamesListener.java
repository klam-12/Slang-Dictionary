package controller;

import view.dictionaryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Khanh Lam
 */
public class GamesListener implements ItemListener, ActionListener {
    dictionaryView dictView;

    public GamesListener(dictionaryView dictView) {
        this.dictView = dictView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gameModeSelected = (String) this.dictView.getComboBoxGame().getSelectedItem();
        this.dictView.setGameScreen(gameModeSelected);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
