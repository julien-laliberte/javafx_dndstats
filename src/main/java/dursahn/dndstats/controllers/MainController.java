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
import javafx.scene.control.TextField;
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

    @FXML private TextField number;
    @FXML private TextField targets;

    @FXML private RadioButton degatFaitRB;
    @FXML private RadioButton degatRecuRB;
    @FXML private RadioButton soinsFaitRB;
    @FXML private RadioButton soinsRecuRB;
    @FXML private RadioButton soinsPersoRB;
    @FXML private RadioButton degatAllieRB;
    @FXML private RadioButton critiqueRB;
    @FXML private RadioButton echecRB;
    @FXML private RadioButton enemyControlledRB;
    @FXML private RadioButton enemyKilledRB;
    @FXML private RadioButton bossKilledRB;
    @FXML private RadioButton controlledRB;
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
    private final ToggleGroup operationToggleGroup2 = new ToggleGroup();
    //endregion

    @FXML
    public void initialize() {
        degatFaitRB.setToggleGroup(operationToggleGroup);
        degatRecuRB.setToggleGroup(operationToggleGroup);
        soinsFaitRB.setToggleGroup(operationToggleGroup);
        soinsRecuRB.setToggleGroup(operationToggleGroup);
        soinsPersoRB.setToggleGroup(operationToggleGroup);
        degatAllieRB.setToggleGroup(operationToggleGroup);
        critiqueRB.setToggleGroup(operationToggleGroup2);
        echecRB.setToggleGroup(operationToggleGroup2);
        enemyControlledRB.setToggleGroup(operationToggleGroup2);
        enemyKilledRB.setToggleGroup(operationToggleGroup2);
        bossKilledRB.setToggleGroup(operationToggleGroup2);
        controlledRB.setToggleGroup(operationToggleGroup2);
        koRB.setToggleGroup(operationToggleGroup2);

        playerList = new PlayerList();
        npcList = new NpcList();
        dmList = new DmList();

        redrawAllCards();
    }

    //region Buttons
    public void handleAddCharacter(ActionEvent actionEvent) {
        showAddDialog();
    }

    private void showAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/views/add_character.fxml"));
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/views/edit_game.fxml"));
            VBox dialogPane = loader.load();
            EditGameController dialogController = loader.getController();
            dialogController.setAllLists(playerList, npcList, dmList);
            dialogController.setMainController(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier la partie");
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

    public void handleResetGame(ActionEvent actionEvent) {
        for(CharacterDTO player: activePlayer){
            player.resetCharacter();
            playerList.updatePlayers(player);
        }
        for(CharacterDTO npc: activeNpc){
            npc.resetCharacter();
            npcList.updateNpcs(npc);
        }
        for(DmDTO dm: activeDm){
            dm.resetDm();
            dmList.updateDM(dm);
        }
        redrawAllCards();
    }

    public void handleCalculate(ActionEvent actionEvent) {
        int num = 0;
        int enemy = 1;
        DmDTO dm = null;

        try {
            num = Integer.parseInt(number.getText());
            enemy = Integer.parseInt(targets.getText());
        } catch (Exception e) {
            System.out.println("Au moins un des champs n'est pas un nombre");
            return;
        }

        for (DmDTO currentDm: activeDm){
            dm = currentDm;
        }

        RadioButton selectedOperation = (RadioButton) operationToggleGroup.getSelectedToggle();
        String operation = selectedOperation.getText();

        for (CheckBox checkBox : playerCheckBoxMap.keySet()) {
            if (checkBox.isSelected()) {
                CharacterDTO player = playerCheckBoxMap.get(checkBox);
                player.setDamageDone(player.getDamageDone() + num * enemy);
                playerList.updatePlayers(player);
                updateCharacterCard(player);

                dm.setDamageReceived(dm.getDamageReceived() + num * enemy);
                dmList.updateDM(dm);
                updateDmCard(dm);
            }
        }

        if (!activeNpc.isEmpty()) {
            for (CheckBox checkBox : npcCheckBoxMap.keySet()) {
                if (checkBox.isSelected()) {
                    CharacterDTO npc = npcCheckBoxMap.get(checkBox);
                    npc.setDamageDone(npc.getDamageDone() + num * enemy);
                    npcList.updateNpcs(npc);
                    updateCharacterCard(npc);

                    dm.setDamageReceived(dm.getDamageReceived() + num * enemy);
                    dmList.updateDM(dm);
                    updateDmCard(dm);
                }
            }
        }
    }

    public void handleAddOpp2(ActionEvent actionEvent) {
    }

    public void handleReduceOpp2(ActionEvent actionEvent) {
    }
    //endregion
    // region Operations interface
    public void redrawInterface(List<CharacterDTO> activePlayersList,
                                List<CharacterDTO> activeNpcsList,
                                List<DmDTO> activeDmList) {
        playerCheckboxList.getChildren().clear();
        npcCheckboxList.getChildren().clear();
        dmCheckboxList.getChildren().clear();

        for (int i = 0; i < activePlayersList.size(); i++) {
            CharacterDTO player = activePlayersList.get(i);
            CheckBox checkBox = new CheckBox(player.getFirstName());
            getPlayerCheckBoxMap().put(checkBox, player);
            playerCheckboxList.getChildren().add(checkBox);
        }
        for (int i = 0; i < activeNpcsList.size(); i++) {
            CharacterDTO npc = activeNpcsList.get(i);
            CheckBox checkBox = new CheckBox(npc.getFirstName());
            getNpcCheckBoxMap().put(checkBox, npc);
            npcCheckboxList.getChildren().add(checkBox);
        }
        for (int i = 0; i < activeDmList.size(); i++) {
            DmDTO dm = activeDmList.get(i);
            CheckBox checkBox = new CheckBox(dm.getName());
            getDmCheckBoxMap().put(checkBox, dm);
            dmCheckboxList.getChildren().add(checkBox);
        }
    }
    // endregion
    //region Characters interface
    public void addCharacterFromDialog(String firstName, String lastName, String _class, String subclass, Integer level,
                                       String class2, String subclass2, Integer classLevel2, String color) {
        addCharacter(firstName, lastName, _class, subclass, level, class2, subclass2, classLevel2, color);
        redrawAllCards();
    }

    private void addCharacter(String firstName, String lastName, String _class, String subclass, Integer level,
                              String class2, String subclass2, Integer classLevel2, String color) {
        CharacterDTO newCharacter = new CharacterDTO(firstName, lastName, _class, subclass, level, class2, subclass2, classLevel2, color);
        playerList.addPlayer(newCharacter);
        displayCharacter(newCharacter, playerListFlowPane);
    }

    public void addNpcFromDialog(String firstName, String lastName, String _class, String subclass, Integer level,
                                 String class2, String subclass2, Integer classLevel2, String color) {
        addNpc(firstName, lastName, _class, subclass, level, class2, subclass2, classLevel2, color);
        redrawAllCards();
    }

    private void addNpc(String firstName, String lastName, String _class, String subclass, Integer level,
                        String class2, String subclass2, Integer classLevel2, String color) {
        CharacterDTO newCharacter = new CharacterDTO(firstName, lastName, _class, subclass, level, class2, subclass2, classLevel2, color);
        npcList.addNpc(newCharacter);
        displayCharacter(newCharacter, npcListFlowPane);
    }

    private void displayCharacter(CharacterDTO character, FlowPane targetPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/views/character_card.fxml"));
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
            controller.updateStatsDisplay(character);
            characterCard.setStyle("-fx-background-color: #" + character.getColor() + "1A;" +
                    "-fx-border-color: #" + character.getColor());
            characterCard.setUserData(controller); // Stocker le contrôleur
            targetPane.getChildren().add(characterCard);
            controller.updateStatsDisplay(character); // Initialiser les stats
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDm(String name, String surname, String color) {
        DmDTO newDm = new DmDTO(name, surname, color);
        dmList.addDM(newDm);
        redrawAllCards();
    }

    private void displayDm(DmDTO dmDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/views/dm_card.fxml"));
            VBox dmCard = loader.load();
            DmCardController controller = loader.getController();
            controller.setDmDetails(dmDTO.getName(), dmDTO.getSurname(), dmDTO.getId());
            controller.SetDmCard(dmCard, dmListFlowPane, this);
            controller.updateStatsDisplay(dmDTO);
            dmCard.setStyle("-fx-background-color: #" + dmDTO.getColor() + "1A;" +
                    "-fx-border-color: #" + dmDTO.getColor());
            dmCard.setUserData(controller); // Stocker le contrôleur
            dmListFlowPane.getChildren().add(dmCard);
            controller.updateStatsDisplay(dmDTO); // Initialiser les stats
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCharacters() {
        List<CharacterDTO> players = playerList.getPlayers();
        List<CharacterDTO> npcs = npcList.getNpcs();

        for (CharacterDTO player : players) {
            if (player.getVisible()) {
                displayCharacter(player, playerListFlowPane);
                activePlayer.add(player);
            }
        }

        for (CharacterDTO npc : npcs) {
            if (npc.getVisible()) {
                displayCharacter(npc, npcListFlowPane);
                activeNpc.add(npc);
            }
        }
    }

    private void loadDM() {
        List<DmDTO> dms = dmList.getDms();
        for (DmDTO dm : dms) {
            if (dm.getVisible()) {
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

    // Méthode pour mettre à jour une carte de personnage
    private void updateCharacterCard(CharacterDTO character) {
        for (javafx.scene.Node node : playerListFlowPane.getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                CharacterCardController controller = (CharacterCardController) card.getUserData();
                if (controller != null && controller.characterId.equals(character.getId())) {
                    controller.updateStatsDisplay(character);
                    controller.applyCardStyle();
                    break;
                }
            }
        }
        for (javafx.scene.Node node : npcListFlowPane.getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                CharacterCardController controller = (CharacterCardController) card.getUserData();
                if (controller != null && controller.characterId.equals(character.getId())) {
                    controller.updateStatsDisplay(character);
                    controller.applyCardStyle();
                    break;
                }
            }
        }
    }

    // Méthode pour mettre à jour une carte de DM
    private void updateDmCard(DmDTO dm) {
        for (javafx.scene.Node node : dmListFlowPane.getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                DmCardController controller = (DmCardController) card.getUserData();
                if (controller != null && controller.dmId.equals(dm.getId())) {
                    controller.updateStatsDisplay(dm);
                    controller.applyCardStyle(); // Appliquer le style après mise à jour
                    break;
                }
            }
        }
    }
    //endregion
}