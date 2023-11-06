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
        String input;
        switch (click){
            case "Search word":
                System.out.println("Search word");
                dictView.searchWord();
                break;
            case "Search definition":
                System.out.println("Search definition");
                //long start, end;
                //start = System.currentTimeMillis();
                dictView.searchDef();
                //end = System.currentTimeMillis();   // start lấy thời gian theo millisecond
                //System.out.println("Time Millis: " + (end - start));
                break;
            case "Add":
                System.out.println("Add");

                break;
            case "Delete":
                System.out.println("Delete");
                break;
            case "Edit":
                System.out.println("Edit");
                break;
            case "Reset":
                System.out.println("Reset");
                break;
            default:
                System.out.println("Nothing");

        }
    }
}
