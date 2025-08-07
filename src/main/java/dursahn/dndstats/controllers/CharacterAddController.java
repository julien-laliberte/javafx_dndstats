package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public TextField dmName;
    public TextField dmSurname;
    public TextField dmColor;

    private MainController mainController;
    //endregion

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void handleAddPlayer(ActionEvent actionEvent) {
        createCharacter(true);
    }

    public void handleAddNpc(ActionEvent actionEvent) {
       createCharacter(false);
    }

    private void createCharacter(boolean isPlayer){
        String firstName = characterFirstName.getText();
        String lastName = characterLastName.getText();
        String class1 = characterClass.getText();
        String subclass1 = characterSubclass.getText();
        Integer level1 = Integer.parseInt(characterClassLevel.getText());
        String class2 = characterClass2.getText();
        String subclass2;
        Integer level2;

        if(!class2.isEmpty()){
            subclass2 = characterSubclass2.getText();
            level2 = Integer.parseInt(characterClass2Level.getText());
        } else {
            class2 = null;
            subclass2 = null;
            level2 = null;
        }
        String color = characterColor.getText();
        if (isPlayer) {
            mainController.addCharacterFromDialog(
                    firstName, lastName, class1, subclass1, level1,
                    class2, subclass2, level2, color
            );
        } else {
            mainController.addNpcFromDialog(
                    firstName, lastName, class1, subclass1, level1,
                    class2, subclass2, level2, color
            );
        }
        closeView();
    }

    public void handleAddDm(ActionEvent actionEvent) {
        String name = dmName.getText();
        String surname = dmSurname.getText();
        String color = dmColor.getText();
        mainController.addDm(name, surname, color);
        closeView();
    }

    private void closeView(){
        Stage stage = (Stage) characterFirstName.getScene().getWindow();
        stage.close();
    }
}
