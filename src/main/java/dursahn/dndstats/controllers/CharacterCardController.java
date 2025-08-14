package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.CharacterDTO;
import dursahn.dndstats.managers.NpcList;
import dursahn.dndstats.managers.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class CharacterCardController {

    //region FXML Variables
    @FXML public Label characterName;
    @FXML public Label characterClass;
    @FXML public Label characterSubClass;
    @FXML public Label characterLevel;
    @FXML public Label characterClass2;
    @FXML public Label characterSubClass2;
    @FXML public Label characterLevel2;
    @FXML public ImageView imageView;

    @FXML public Label damageDone;
    @FXML public Label damageReceived;
    @FXML public Label alliedDamage;
    @FXML public Label healDone;
    @FXML public Label healReceived;
    @FXML public Label personalHeal;
    @FXML public Label critical;
    @FXML public Label failure;
    @FXML public Label minionControlled;
    @FXML public Label minionKilled;
    @FXML public Label bossKilled;
    @FXML public Label controlled;
    @FXML public Label knockOut;
    //endregion
    //region Other Variables
    public String characterId;
    private MainController mainController;
    private PlayerList playerList = new PlayerList();
    private NpcList npcList = new NpcList();
    private VBox characterNode;
    private FlowPane characterFlowPane;
    //endregion
    //region Constructors
    public void setCharacterCard(VBox characterNode, FlowPane characterFlowPane, MainController mainController) {
        this.characterNode = characterNode;
        this.characterFlowPane = characterFlowPane;
        this.mainController = mainController;
    }
    //endregion
    //region Edit Button
    public void handleCharacterEdit(ActionEvent actionEvent) {
        System.out.println("Editing character: " + characterName.getText());
        CharacterDTO characterDTO = playerList.getPlayerById(characterId);
        if (characterDTO == null) {
            characterDTO = npcList.getNpcById(characterId);
        }
        showEditCharacter(characterDTO);
    }

    private void showEditCharacter(CharacterDTO characterDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/views/view_character.fxml"));
            VBox dialogPane = loader.load();

            ViewCharacterController dialogController = loader.getController();
            dialogController.setRootNode(dialogPane);
            dialogController.setCharacterDetail(characterDTO, this, mainController);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Éditer personnage");

            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
    //region Put and delete
    public void updateCharacter(CharacterDTO character) {
        if (playerList.getPlayerById(characterId) != null) {
            playerList.updatePlayers(character);
            playerList.sortPlayerByFirstName();
        } else {
            npcList.updateNpcs(character);
            npcList.sortNpcsByFirstName();
        }

        setCharacterDetails(
                character.getFirstName(),
                character.getLastName(),
                character.get_class(),
                character.getSubclass(),
                character.getClassLevel(),
                character.getClass2(),
                character.getSubclass2(),
                character.getClassLevel2(),
                characterId
        );
        applyCardStyle();
        updateStatsDisplay(character); // Mettre à jour les stats
    }

    public void deleteCharacter(CharacterDTO character) {
        characterFlowPane.getChildren().remove(characterNode);
        if (playerList.getPlayerById(characterId) != null) {
            playerList.deletePlayer(character);
        } else {
            npcList.deleteNpc(character);
        }
    }
    //endregion
    //region Calculs
    public void updateStatsDisplay(CharacterDTO character) {
        damageDone.setText(character.getDamageDone().toString());
        damageReceived.setText(character.getDamageReceived().toString());
        alliedDamage.setText(character.getAlliedDamage().toString()); // À adapter si pertinent
        healDone.setText(character.getHealDone().toString());
        healReceived.setText(character.getHealReceived().toString());
        personalHeal.setText(character.getPersonalHeal().toString());
        critical.setText(character.getCritical().toString());
        failure.setText(character.getFailure().toString());
        minionControlled.setText(character.getMinionControlled().toString());
        minionKilled.setText(character.getMinionKilled().toString());
        bossKilled.setText(character.getBossKilled().toString());
        controlled.setText(character.getControlled().toString());
        knockOut.setText(character.getKnockOut().toString());
    }
    //endregion
    //region Other Utilities
    public void setCharacterDetails(String firstName, String lastName, String _class, String subclass, Integer level,
                                    String class2, String subclass2, Integer classLevel2, String id) {
        this.characterId = id;
        if (lastName != null) {
            characterName.setText(firstName.toUpperCase() + ' ' + lastName.toUpperCase());
        } else {
            characterName.setText(firstName.toUpperCase());
        }
        characterClass.setText(_class);
        characterSubClass.setText(subclass);
        characterLevel.setText(level.toString());
        characterClass2.setText(class2);
        characterSubClass2.setText(subclass2);
        if (classLevel2 == null) {
            characterLevel2.setText(null);
        } else {
            characterLevel2.setText(classLevel2.toString());
        }
        Image image = null;
        try {
            String imagePath = "/dursahn/dndstats/images/" + firstName.toLowerCase() + ".png";
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (NullPointerException e) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dursahn/dndstats/images/default.png")));
            System.out.println("Image not found for " + firstName + ", using default.png");
        }
        imageView.setImage(image);
    }

    void applyCardStyle() {
        if (characterNode != null) {
            CharacterDTO character = playerList.getPlayerById(characterId);
            if (character == null) {
                character = npcList.getNpcById(characterId);
            }
            if (character != null) {
                characterNode.setStyle("-fx-background-color: #" + character.getColor() + "1A;" +
                        "-fx-border-color: #" + character.getColor());
            }
        } else {
            System.err.println("Root node is null in CharacterCardController");
        }
    }
    //endregion
}