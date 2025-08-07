package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.CharacterDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ViewCharacterController {
    public TextField characterFirstName;
    public TextField characterLastName;
    public TextField characterClass;
    public TextField characterSubclass;
    public TextField characterClassLevel;
    public TextField characterClass2;
    public TextField characterSubclass2;
    public TextField characterClass2Level;
    public TextField characterColor;
    public ImageView imageView;

    private CharacterDTO character;
    private CharacterCardController mainController;
    private VBox rootNode;

    public void setRootNode(VBox rootNode){
        this.rootNode = rootNode;
    }

    public void setCharacterDetail(CharacterDTO character, CharacterCardController mainController){
        this.character = character;
        this.mainController = mainController;

        characterFirstName.setText(character.getFirstName());
        characterLastName.setText(character.getLastName());
        characterClass.setText(character.get_class());
        characterSubclass.setText(character.getSubclass());
        characterClassLevel.setText(character.getClassLevel().toString());
        characterColor.setText(character.getColor());
        if(character.getClass2() != null){
            characterClass2.setText(character.getClass2());
            characterSubclass2.setText(character.getSubclass2());
            characterClass2Level.setText(character.getClassLevel2().toString());
        }

        Image image = null;
        try {
            String imagePath = "/dursahn/dndstats/images/" + character.getFirstName().toLowerCase() + ".png";
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (NullPointerException e) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dursahn/dndstats/images/default.png")));
            System.out.println("Image not found for " + character.getFirstName() + ", using default.png");
        }
        imageView.setImage(image);
        rootNode.setStyle("-fx-background-color: #" + character.getColor() + "1A;" +
                "-fx-border-color: #" + character.getColor());
    }



    public void handleDelete(ActionEvent actionEvent) {
        mainController.deleteCharacter(character);
        closeDialog();
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeDialog();
    }

    public void handleUpdate(ActionEvent actionEvent) throws FileNotFoundException {
        character.setFirstName(characterFirstName.getText());
        character.setLastName(characterLastName.getText());
        character.set_class(characterClass.getText());
        character.setSubclass(characterSubclass.getText());
        character.setClassLevel(Integer.parseInt(characterClassLevel.getText()));
        character.setColor(characterColor.getText());

        if(!characterClass2.getText().isEmpty()){
            character.setClass2(characterClass2.getText());
            character.setSubclass2(characterSubclass2.getText());
            character.setClassLevel2(Integer.parseInt(characterClass2Level.getText()));
        }
        mainController.updateCharacter(character);

        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) characterFirstName.getScene().getWindow();
        stage.close();
    }
}
