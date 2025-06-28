package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class DmCardController {
    public Label dmName;
    public Label dmSurname;

    public void handleDmEdit(ActionEvent actionEvent) {
        System.out.println("Editing npc: " + dmName.getText());
        // TODO: À faire
    }

    public void setDmDetails(String name, String _class){
        dmName.setText(name);
        dmSurname.setText(_class);
    }
}
