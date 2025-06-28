package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class NpcCardController {
    public Label npcName;
    public Label npcClass;
    public Label npcSubClass;
    public Label npcLevel;

    public void handleNpcEdit(ActionEvent actionEvent) {
        System.out.println("Editing npc: " + npcName.getText());
        // TODO: À faire
    }

    public void setNpcDetails(String name, String _class, String subclass, Integer level){
        npcName.setText(name);
        npcClass.setText(_class);
        npcSubClass.setText(subclass);
        npcLevel.setText(level.toString());
    }
}
