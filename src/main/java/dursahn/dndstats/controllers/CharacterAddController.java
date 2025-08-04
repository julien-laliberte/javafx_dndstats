package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.CharacterDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.html.parser.Parser;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterAddController {
    //region Variables
    public TextField characterFirstName;
    public TextField characterLastName;
    public TextField characterClass;
    public TextField characterSubclass;
    public TextField characterClassLevel;
    public TextField characterClass2;
    public TextField characterSubclass2;
    public TextField characterClass2Level;
    public TextField characterColor;

    public TextField dmNom;
    public TextField dmSurname;
    public TextField dmColor;

    private MainController mainController;
    //endregion

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void handleAddPlayer(ActionEvent actionEvent) {
        String playerFirstName = characterFirstName.getText();
        String playerLastName = characterLastName.getText();
        String playerClass = characterClass.getText();
        String playerSubclass = characterSubclass.getText();
        Integer playerClassLevel = Integer.parseInt(characterClassLevel.getText());
        String playerClass2 = characterClass2.getText();
        String playerSubclass2;
        Integer playerClassLevel2;
        if(!playerClass2.isEmpty()){
            playerSubclass2 = characterSubclass2.getText();
            playerClassLevel2 = Integer.parseInt(characterClass2Level.getText());
        } else {
            playerClass2 = null;
            playerSubclass2 = null;
            playerClassLevel2 = null;
        }
        String playerColor = characterColor.getText();

        CharacterDTO characterDTO = new CharacterDTO(
                playerFirstName,
                playerLastName,
                playerClass,
                playerSubclass,
                playerClassLevel,
                playerClass2,
                playerSubclass2,
                playerClassLevel2,
                playerColor
        );

        mainController.addCharacterFromDialog(
                playerFirstName,
                playerLastName,
                playerClass,
                playerSubclass,
                playerClassLevel,
                playerClass2,
                playerSubclass2,
                playerClassLevel2,
                playerColor
        );
        closeView();
    }

    public void handleAddNpc(ActionEvent actionEvent) {
        String NpcFirstName = characterFirstName.getText();
        String NpcLastName = characterLastName.getText();
        String NpcClass = characterClass.getText();
        String NpcSubclass = characterSubclass.getText();
        Integer NpcClassLevel = Integer.parseInt(characterClassLevel.getText());
        String NpcClass2 = characterClass2.getText();
        String NpcSubclass2;
        Integer NpcClassLevel2;

        if(!NpcClass2.isEmpty()){
            NpcSubclass2 = characterSubclass2.getText();
            NpcClassLevel2 = Integer.parseInt(characterClass2Level.getText());
        } else {
            NpcClass2 = null;
            NpcSubclass2 = null;
            NpcClassLevel2 = null;
        }
        String NpcColor = characterColor.getText();
        CharacterDTO characterDTO = new CharacterDTO(
                NpcFirstName,
                NpcLastName,
                NpcClass,
                NpcSubclass,
                NpcClassLevel,
                NpcClass2,
                NpcSubclass2,
                NpcClassLevel2,
                NpcColor
        );
        mainController.addNpcFromDialog(
                NpcFirstName,
                NpcLastName,
                NpcClass,
                NpcSubclass,
                NpcClassLevel,
                NpcClass2,
                NpcSubclass2,
                NpcClassLevel2,
                NpcColor
        );
        closeView();
    }

    public void handleAddDm(ActionEvent actionEvent) {
        //mainController.addDm();
        closeView();
    }

    private void closeView(){
        Stage stage = (Stage) characterFirstName.getScene().getWindow();
        stage.close();
    }
}
