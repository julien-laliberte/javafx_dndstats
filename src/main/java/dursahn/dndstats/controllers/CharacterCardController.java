package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class CharacterCardController {

    public Label characterName;
    public Label characterClass;
    public Label characterSubClass;
    public Label characterLevel;
    public Label characterClass2;
    public Label characterSubClass2;
    public Label characterLevel2;

    public void handleCharacterEdit(ActionEvent actionEvent) {
        System.out.println("Editing character: " + characterName.getText());
        // TODO: À faire
    }

    public void setCharacterDetails(String name, String _class, String subclass, Integer level,
                                    String class2, String subclass2, Integer classLevel2){
        characterName.setText(name);
        characterClass.setText(_class);
        characterSubClass.setText(subclass);
        characterLevel.setText(level.toString());
        characterClass2.setText(class2);
        characterSubClass2.setText(subclass2);
        if(classLevel2 == null) {
            characterLevel2.setText(null);
        } else {
            characterLevel2.setText(classLevel2.toString());
        }

    }
}