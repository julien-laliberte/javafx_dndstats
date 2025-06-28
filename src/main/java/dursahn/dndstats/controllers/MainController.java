package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    //region Variables
    @FXML private RadioButton degatFaitRB;
    @FXML private RadioButton degatRecuRB;
    @FXML private RadioButton soinsFaitRB;
    @FXML private RadioButton soinsRecuRB;
    @FXML private RadioButton soinsPersoRB;
    @FXML private RadioButton degatAllieRB;
    @FXML private RadioButton critiqueRB;
    @FXML private RadioButton echecRB;
    @FXML private RadioButton koRB;

    public FlowPane playerList;
    public FlowPane npcList;
    public FlowPane dmList;
    //endregion

    private final ToggleGroup operationToggleGroup = new ToggleGroup();

    @FXML
    public void initialize() {
        degatFaitRB.setToggleGroup(operationToggleGroup);
        degatRecuRB.setToggleGroup(operationToggleGroup);
        soinsFaitRB.setToggleGroup(operationToggleGroup);
        soinsRecuRB.setToggleGroup(operationToggleGroup);
        soinsPersoRB.setToggleGroup(operationToggleGroup);
        degatAllieRB.setToggleGroup(operationToggleGroup);
        critiqueRB.setToggleGroup(operationToggleGroup);
        echecRB.setToggleGroup(operationToggleGroup);
        koRB.setToggleGroup(operationToggleGroup);

        addCharacter("Fenrick Damascus", "Bloodhunter", "Order of Lycan", 6);
        addNpc("Alyssa Vae'Lorynn", "Warlock", "Hexblade",5);
        addDm("Jessica", "Déesse de merde :D");
    }

    public void handleAddCharacter(ActionEvent actionEvent) {
        showAddDialog();
    }

    private void showAddDialog(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/character_add_dialog.fxml"));
            VBox dialogPane = loader.load();
            CharacterAddController dialogController = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter un personnage/NPC/DM");
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleEditGame(ActionEvent actionEvent) {
        addCharacter("Fenrick Damascus", "Bloodhunter", "Order of Lycan", 6);
    }

    public void handleEditCharacters(ActionEvent actionEvent) {
    }

    private void addCharacter(String name, String _class, String subclass, Integer level){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/player-card.fxml"));
            VBox playerCard = loader.load();
            PlayerCardController controller = loader.getController();
            controller.setCharacterDetails(name, _class, subclass, level);

            playerList.getChildren().add(playerCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNpc(String name, String _class, String subclass, Integer level){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/npc-card.fxml"));
            VBox npcCard = loader.load();
            NpcCardController controller = loader.getController();
            controller.setNpcDetails(name, _class, subclass, level);

            npcList.getChildren().add(npcCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDm(String name, String surname){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/dm-card.fxml"));
            VBox dmCard = loader.load();
            DmCardController controller = loader.getController();
            controller.setDmDetails(name, surname);

            dmList.getChildren().add(dmCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}