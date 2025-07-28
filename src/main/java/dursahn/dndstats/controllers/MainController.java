package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.CharacterDTO;
import dursahn.dndstats.dto.DmDTO;
import dursahn.dndstats.managers.DmList;
import dursahn.dndstats.managers.NpcList;
import dursahn.dndstats.managers.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
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

    public FlowPane playerListFlowPane;
    public FlowPane npcListFlowPane;
    public FlowPane dmListFlowPane;

    private PlayerList playerList;
    private NpcList npcList;
    private DmList dmList;

    private final ToggleGroup operationToggleGroup = new ToggleGroup();
    //endregion

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

        playerList = new PlayerList();
        npcList = new NpcList();
        dmList = new DmList();

        addCharacter("Fenrick Damascus", "Bloodhunter", "Order of Lycan", 6,
                null, null, null, null);
        addNpc("Alyssa Vae'Lorynn", "Warlock", "Hexblade",5,
                null, null, null, null);
        addDm("Jessica", "Déesse de merde :D");
    }

    //region Buttons
    public void handleAddCharacter(ActionEvent actionEvent) {
        showAddDialog();
    }

    private void showAddDialog(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/character_add_dialog.fxml"));
            VBox dialogPane = loader.load();
            CharacterAddController dialogController = loader.getController();
            dialogController.setMainController(this);

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
        addCharacter("Fenrick Damascus", "Bloodhunter", "Order of Lycan", 6
        , null, null, null, null);
    }

    public void handleEditCharacters(ActionEvent actionEvent) {
    }

    //endregion
    //region Characters interface
    public void addCharacterFromDialog(String name, String _class, String subclass, Integer level,
                                       String class2, String subclass2, Integer classLevel2, String color){
        addCharacter(name, _class, subclass, level,
                class2, subclass2, classLevel2, color);
    }

    private void addCharacter(String name, String _class, String subclass, Integer level,
                              String class2, String subclass2, Integer classLevel2, String color){
        CharacterDTO newCharacter = new CharacterDTO(
                name, _class, subclass, level,
                class2, subclass2, classLevel2, color);
        playerList.addPlayer(newCharacter);
        displayCharacter(newCharacter, playerListFlowPane);
    }

    public void addNpcFromDialog(String name, String _class, String subclass, Integer level,
                                 String class2, String subclass2, Integer classLevel2, String color){
        addNpc(name, _class, subclass, level,
                class2, subclass2, classLevel2, color);
    }

    private void addNpc(String name, String _class, String subclass, Integer level,
                        String class2, String subclass2, Integer classLevel2, String color){
        CharacterDTO newCharacter = new CharacterDTO(
                name, _class, subclass, level,
                class2, subclass2, classLevel2, color);
        npcList.addNpc(newCharacter);
        displayCharacter(newCharacter, npcListFlowPane);
    }

    private void displayCharacter(CharacterDTO character, FlowPane targetPane){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/character-card.fxml"));
            VBox playerCard = loader.load();
            CharacterCardController controller = loader.getController();
            controller.setCharacterDetails(
                    character.getName(),
                    character.get_class(),
                    character.getSubclass(),
                    character.getClassLevel(),
                    character.getClass2(),
                    character.getSubclass2(),
                    character.getClassLevel2()
            );
            targetPane.getChildren().add(playerCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDm(String name, String surname){
        DmDTO newDm = new DmDTO(name, surname);
        dmList.addDM(newDm);
        displayDm(newDm);
    }

    private void displayDm(DmDTO dmDTO){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/dm-card.fxml"));
            VBox dmCard = loader.load();
            DmCardController controller = loader.getController();
            controller.setDmDetails(
                    dmDTO.getName(),
                    dmDTO.getSurname()
            );

            dmListFlowPane.getChildren().add(dmCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}