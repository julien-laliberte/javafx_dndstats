package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class PlayerCardController {

    public Label characterName;
    public Label characterClass;
    public Label characterSubClass;
    public Label characterLevel;

    public void handleCharacterEdit(ActionEvent actionEvent) {
        System.out.println("Editing character: " + characterName.getText());
        // TODO: À faire
    }

    public void setCharacterDetails(String name, String _class, String subclass, Integer level){
        characterName.setText(name);
        characterClass.setText(_class);
        characterSubClass.setText(subclass);
        characterLevel.setText(level.toString());
    }
}
