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
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {


    //region Variables
    @FXML private VBox playerCheckboxList;
    @FXML private VBox npcCheckboxList;
    @FXML private VBox dmCheckboxList;

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

    private List<CharacterDTO> activePlayer = new ArrayList<>();
    private List<CharacterDTO> activeNpc = new ArrayList<>();
    private List<DmDTO> activeDm = new ArrayList<>();

    private Map<CheckBox, CharacterDTO> playerCheckBoxMap = new HashMap<>();
    private Map<CheckBox, CharacterDTO> npcCheckBoxMap = new HashMap<>();
    private Map<CheckBox, DmDTO> dmCheckBoxMap = new HashMap<>();

    public Map<CheckBox, CharacterDTO> getPlayerCheckBoxMap() {
        return playerCheckBoxMap;
    }
    public Map<CheckBox, CharacterDTO> getNpcCheckBoxMap() {
        return npcCheckBoxMap;
    }
    public Map<CheckBox, DmDTO> getDmCheckBoxMap() {
        return dmCheckBoxMap;
    }

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

        redrawAllCards();
    }

    //region Buttons
    public void handleAddCharacter(ActionEvent actionEvent) {
        showAddDialog();
    }

    private void showAddDialog(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/add_character.fxml"));
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
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/edit_game.fxml"));
            VBox dialogPane = loader.load();
            EditGameController dialogController = loader.getController();
            dialogController.setAllLists(playerList, npcList, dmList);
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

    public void handleEditCharacters(ActionEvent actionEvent) {
    }

    //endregion
    // region Operations interface
    public void redrawInterface(List<CharacterDTO> activePlayersList,
                                List<CharacterDTO> activeNpcsList,
                                List<DmDTO> activeDmList){
        playerCheckboxList.getChildren().clear();
        npcCheckboxList.getChildren().clear();
        dmCheckboxList.getChildren().clear();

        for(int i = 0 ; i < activePlayersList.size() ; i++){
            CharacterDTO player = activePlayersList.get(i);
            CheckBox checkBox = new CheckBox(player.getFirstName());
            getPlayerCheckBoxMap().put(checkBox, player);
            playerCheckboxList.getChildren().add(checkBox);
        }
        for(int i = 0 ; i < activeNpcsList.size() ; i++){
            CharacterDTO npc = activeNpcsList.get(i);
            CheckBox checkBox = new CheckBox(npc.getFirstName());
            getNpcCheckBoxMap().put(checkBox, npc);
            npcCheckboxList.getChildren().add(checkBox);
        }
        for(int i = 0 ; i < activeDmList.size() ; i++){
            DmDTO dm = activeDmList.get(i);
            CheckBox checkBox = new CheckBox(dm.getName());
            getDmCheckBoxMap().put(checkBox, dm);
            dmCheckboxList.getChildren().add(checkBox);
        }

    }
    // endregion
    //region Characters interface
    public void addCharacterFromDialog(String firstMame, String lastName, String _class, String subclass, Integer level,
                                       String class2, String subclass2, Integer classLevel2, String color){
        addCharacter(firstMame, lastName, _class, subclass, level,
                class2, subclass2, classLevel2, color);
        redrawAllCards();
    }

    private void addCharacter(String firstMame, String lastName, String _class, String subclass, Integer level,
                              String class2, String subclass2, Integer classLevel2, String color){
        CharacterDTO newCharacter = new CharacterDTO(
                firstMame, lastName, _class, subclass, level,
                class2, subclass2, classLevel2, color);
        playerList.addPlayer(newCharacter);
        displayCharacter(newCharacter, playerListFlowPane);
    }

    public void addNpcFromDialog(String firstMame, String lastName, String _class, String subclass, Integer level,
                                 String class2, String subclass2, Integer classLevel2, String color){
        addNpc(firstMame, lastName, _class, subclass, level,
                class2, subclass2, classLevel2, color);
        redrawAllCards();
    }

    private void addNpc(String firstMame, String lastName, String _class, String subclass, Integer level,
                        String class2, String subclass2, Integer classLevel2, String color){
        CharacterDTO newCharacter = new CharacterDTO(
                firstMame, lastName, _class, subclass, level,
                class2, subclass2, classLevel2, color);
        npcList.addNpc(newCharacter);
        displayCharacter(newCharacter, npcListFlowPane);
    }

    private void displayCharacter(CharacterDTO character, FlowPane targetPane){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/character_card.fxml"));
            VBox characterCard = loader.load();
            CharacterCardController controller = loader.getController();
            controller.setCharacterDetails(
                    character.getFirstName(),
                    character.getLastName(),
                    character.get_class(),
                    character.getSubclass(),
                    character.getClassLevel(),
                    character.getClass2(),
                    character.getSubclass2(),
                    character.getClassLevel2(),
                    character.getId()
            );
            controller.setCharacterCard(characterCard, targetPane, this);
            characterCard.setStyle("-fx-background-color: #" + character.getColor() +"1A;" +
                    "-fx-border-color: #" + character.getColor());
            targetPane.getChildren().add(characterCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDm(String name, String surname, String color){
        DmDTO newDm = new DmDTO(name, surname, color);
        dmList.addDM(newDm);
        redrawAllCards();
    }

    private void displayDm(DmDTO dmDTO){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dursahn/dndstats/views/dm_card.fxml"));
            VBox dmCard = loader.load();
            DmCardController controller = loader.getController();
            controller.setDmDetails(
                    dmDTO.getName(),
                    dmDTO.getSurname(),
                    dmDTO.getId()
            );
            controller.SetDmCard(dmCard, dmListFlowPane, this);
            dmCard.setStyle("-fx-background-color: #" + dmDTO.getColor() +"1A;" +
                    "-fx-border-color: #" + dmDTO.getColor());
            dmListFlowPane.getChildren().add(dmCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCharacters(){
        List<CharacterDTO> players = playerList.getPlayers();
        List<CharacterDTO> npcs = npcList.getNpcs();

        for(CharacterDTO player: players){
            if(player.getVisible()) {
                displayCharacter(player, playerListFlowPane);
                activePlayer.add(player);
            }
        }

        for(CharacterDTO npc: npcs){
            if(npc.getVisible()) {
                displayCharacter(npc, npcListFlowPane);
                activeNpc.add(npc);
            }
        }
    }

    private void loadDM(){
        List<DmDTO> dms = dmList.getDms();
        for(DmDTO dm: dms){
            if(dm.getVisible()) {
                displayDm(dm);
                activeDm.add(dm);
            }
        }
    }

    public void redrawAllCards() {
        playerListFlowPane.getChildren().clear();
        npcListFlowPane.getChildren().clear();
        dmListFlowPane.getChildren().clear();

        activePlayer.clear();
        activeNpc.clear();
        activeDm.clear();

        playerList.loadPlayers();
        npcList.loadNpcs();
        dmList.loadDms();

        loadCharacters();
        loadDM();

        redrawInterface(activePlayer, activeNpc, activeDm);
    }
    //endregion
}