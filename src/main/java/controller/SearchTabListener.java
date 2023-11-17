package controller;

import view.dictionaryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Khanh Lam
 */
public class SearchTabListener implements ActionListener {
    dictionaryView dictView;

    public SearchTabListener(dictionaryView dictView) {
        this.dictView = dictView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String click = e.getActionCommand();
        if(!(click.equals("Search word") || click.equals("Search definition"))){
            this.dictView.reloadSearchArea();
        }
        switch (click){
            case "Search word":
                dictView.searchWord();
                break;
            case "Search definition":
                dictView.searchDef();
                break;
            case "Add":
                dictView.addAWord();
                break;
            case "Delete":
                dictView.deleteAWord();
                break;
            case "Edit":
                dictView.editAWord();
                break;
            case "Reset":
                dictView.reset();
                break;
            case "Random":
                dictView.randomAWord();
                break;
            default:
                System.out.println("Nothing");

        }
    }
}
