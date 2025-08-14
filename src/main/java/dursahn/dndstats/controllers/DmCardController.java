package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.DmDTO;
import dursahn.dndstats.managers.DmList;
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

public class DmCardController {
    @FXML public Label dmName;
    @FXML public Label dmSurname;
    @FXML public ImageView imageView;

    @FXML public Label damageDone;
    @FXML public Label damageReceived;
    @FXML public Label personalHeal;
    @FXML public Label critical;
    @FXML public Label failure;
    @FXML public Label minionControlled;
    @FXML public Label minionLost;
    @FXML public Label bossLost;
    @FXML public Label playersKOs;
    @FXML public Label playersControlled;

    public String dmId;
    private DmList dmList = new DmList();
    private MainController mainController;
    private VBox dmNode;
    private FlowPane dmFlowPane;

    //region Constructors
    public void SetDmList(DmList dmList) {
        this.dmList = dmList;
    }

    public void SetDmCard(VBox dmNode, FlowPane dmFlowPane, MainController mainController) {
        this.dmNode = dmNode;
        this.dmFlowPane = dmFlowPane;
        this.mainController = mainController;
    }
    //endregion
    //region Edit Button
    public void handleDmEdit(ActionEvent actionEvent) {
        System.out.println("Editing DM: " + dmName.getText());
        DmDTO dm = dmList.getDmById(dmId);
        showEditDM(dm);
    }

    private void showEditDM(DmDTO dmDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dursahn/dndstats/views/view_dm.fxml"));
            VBox dialogPane = loader.load();

            ViewDmController dialogController = loader.getController();
            dialogController.setRootNode(dialogPane);
            dialogController.setDmDetail(dmDTO, this, mainController);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Éditer DM");

            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
    //region Put and delete
    public void updateDm(DmDTO dm) {
        dmList.updateDM(dm);
        setDmDetails(dm.getName(), dm.getSurname(), dm.getId());
        applyCardStyle();
        updateStatsDisplay(dm); // Mettre à jour les stats après modification
    }

    public void deleteDM(DmDTO dm) {
        dmFlowPane.getChildren().remove(dmNode);
        dmList.deleteDm(dm);
    }
    //endregion
    //region Other Utilities
    public void setDmDetails(String name, String surname, String id) {
        this.dmId = id;
        dmName.setText(name.toUpperCase());
        dmSurname.setText(surname != null ? surname.toUpperCase() : "");
        Image image = null;
        try {
            String imagePath = "/dursahn/dndstats/images/" + name.toLowerCase() + ".png";
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (NullPointerException e) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dursahn/dndstats/images/default.png")));
            System.out.println("Image not found for " + name + ", using default.png");
        }
        imageView.setImage(image);
    }

    void applyCardStyle() {
        DmDTO dm = dmList.getDmById(dmId);
        if (dmNode != null && dm != null) {
            dmNode.setStyle("-fx-background-color: #" + dm.getColor() + "1A;" +
                    "-fx-border-color: #" + dm.getColor());
        }
    }

    public void updateStatsDisplay(DmDTO dm) {
        if (dm != null) {
            damageDone.setText(dm.getDamageDone().toString());
            damageReceived.setText(dm.getDamageReceived().toString());
            personalHeal.setText(dm.getPersonalHeal().toString());
            critical.setText(dm.getCritical().toString());
            failure.setText(dm.getFailure().toString());
            minionControlled.setText(dm.getCrowdControlled().toString());
            minionLost.setText(dm.getMinionLost().toString());
            bossLost.setText(dm.getBossLost().toString());
            playersKOs.setText(dm.getPlayerKOs().toString());
            playersControlled.setText(dm.getPlayerControlled().toString());
        }
    }
    //endregion
}