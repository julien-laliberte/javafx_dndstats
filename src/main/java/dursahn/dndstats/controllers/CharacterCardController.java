package dursahn.dndstats.controllers;

import dursahn.dndstats.managers.NpcList;
import dursahn.dndstats.managers.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class CharacterCardController {

    public Label characterName;
    public Label characterClass;
    public Label characterSubClass;
    public Label characterLevel;
    public Label characterClass2;
    public Label characterSubClass2;
    public Label characterLevel2;
    public ImageView imageView;

    public String characterId;
    private PlayerList playerList = new PlayerList();
    private NpcList npcList = new NpcList();

    public void handleCharacterEdit(ActionEvent actionEvent) {
        System.out.println("Editing character: " + characterName.getText());
        // TODO: À faire
    }

    public void setCharacterDetails(String firstName, String lastName, String _class, String subclass, Integer level,
                                    String class2, String subclass2, Integer classLevel2, String id){
        this.characterId = id;
        if ((lastName != null)) {
            characterName.setText(firstName.toUpperCase() + ' ' + lastName.toUpperCase());
        } else {
            characterName.setText(firstName.toUpperCase());
        }
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
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/dursahn/dndstats/images/" + firstName.toLowerCase() + ".png")));
        imageView.setImage(image);

    }

    public void setPlayerList(PlayerList playerList){
        this.playerList = playerList;
    }

    public void setNpcList(NpcList npcList){
        this.npcList = npcList;
    }
}