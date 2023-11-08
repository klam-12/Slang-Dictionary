package controller;

import view.dictionaryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

/**
 * @author Khanh Lam
 */
public class GamesListener implements ActionListener {
    dictionaryView dictView;

    public GamesListener(dictionaryView dictView) {
        this.dictView = dictView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Choose game mode
        String gameModeSelected = "";
        gameModeSelected = (String) this.dictView.getComboBoxGame().getSelectedItem();
        if(!gameModeSelected.equals(this.dictView.getGameMode()))
            this.dictView.setGameScreen(gameModeSelected);

        String click = e.getActionCommand();
        if(click.equals("Submit")){
            String userSelected = "";

            switch (gameModeSelected){
                case "Find definition":
                    // Choose answer for game Find Word
                    Enumeration<AbstractButton> buttonsWord = this.dictView.getButtonGroupWord().getElements();
                    while(buttonsWord.hasMoreElements()){
                        AbstractButton nextBtn = buttonsWord.nextElement();
                        if(nextBtn.isSelected()){
                            userSelected = nextBtn.getText();
                            System.out.println(userSelected);
                            this.dictView.setGameChoiceOfUser(userSelected);
                            break;
                        }
                    }
                    break;
                case "Find word":
                    Enumeration<AbstractButton> buttonsDef = this.dictView.getButtonGroupDef().getElements();
                    while(buttonsDef.hasMoreElements()){
                        AbstractButton nextBtn = buttonsDef.nextElement();
                        if(nextBtn.isSelected()){
                            userSelected = nextBtn.getText();
                            System.out.println(userSelected);
                            this.dictView.setGameChoiceOfUser(userSelected);
                            break;
                        }
                    }
                    break;

                case null:
                default:
                    break;
            }

            this.dictView.checkGame(gameModeSelected);
        }

    }

}
