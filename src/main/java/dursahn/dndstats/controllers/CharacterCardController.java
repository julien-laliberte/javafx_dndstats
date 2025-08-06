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

import java.io.FileNotFoundException;
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
    private VBox rootNode;
    private FlowPane parentFlowPane;

    public void setPlayerList(PlayerList playerList){
        this.playerList = playerList;
    }

    public void setNpcList(NpcList npcList){
        this.npcList = npcList;
    }

    public void setRootNode(VBox rootNode) {
        this.rootNode = rootNode;
    }

    public void setParentFlowPane(FlowPane parentFlowPane){
        this.parentFlowPane = parentFlowPane;
    }

    public void handleCharacterEdit(ActionEvent actionEvent) {
        System.out.println("Editing character: " + characterName.getText());
        CharacterDTO characterDTO = playerList.getPlayerById(characterId);
        if(characterDTO == null){
            characterDTO = npcList.getNpcById(characterId);
        }
        showEditCharacter(characterDTO);
    }

    private void showEditCharacter(CharacterDTO characterDTO){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/dursahn/dndstats/views/view_character.fxml"));
            VBox dialogPane = loader.load();

            ViewCharacterController dialogController = loader.getController();
            dialogController.setCharacterDetail(characterDTO, this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Éditer personnage");

            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Image image = null;
        try {
            String imagePath = "/dursahn/dndstats/images/" + firstName.toLowerCase() + ".png";
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (NullPointerException e) {
            // Si l'image spécifique n'est pas trouvée, charger l'image par défaut
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dursahn/dndstats/images/default.png")));
            System.out.println("Image not found for " + firstName + ", using default.png");
        }
        imageView.setImage(image);
    }

    public void updateCharacter(CharacterDTO character) throws FileNotFoundException {
        if (playerList.getPlayerById(characterId) != null){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
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
    }

    private void applyCardStyle() {
        if (rootNode != null) {
            CharacterDTO character = playerList.getPlayerById(characterId);
            if (character == null) {
                character = npcList.getNpcById(characterId);
            }
            if (character != null) {
                rootNode.setStyle("-fx-background-color: #" + character.getColor() + "1A;" +
                        "-fx-border-color: #" + character.getColor());
            }
        } else {
            System.err.println("Root node is null in CharacterCardController");
        }
    }

    public void deleteCharacter(CharacterDTO character){
        parentFlowPane.getChildren().remove(rootNode);
        if (playerList.getPlayerById(characterId) != null){
            playerList.deletePlayer(character);
        } else {
            npcList.deleteNpc(character);
        }

    }
}