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
    private VBox dmNode;
    private FlowPane dmFlowPane;
    //region Constructors
    public void SetDmList(DmList dmList){ this.dmList = dmList;}
    public void SetDmCard(VBox dmNode, FlowPane dmFlowPane) {
        this.dmNode = dmNode;
        this.dmFlowPane = dmFlowPane;
    }
    //endregion
    //region Edit Button

    public void handleDmEdit(ActionEvent actionEvent) {
        System.out.println("Editing DM: " + dmName.getText());
        DmDTO dm = dmList.getDmById(dmId);
        showEditDM(dm);
    }

    private void showEditDM(DmDTO dmDTO){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/dursahn/dndstats/views/view_dm.fxml"));
            VBox dialogPane = loader.load();

            ViewDmController dialogController = loader.getController();
            dialogController.setRootNode(dialogPane);
            dialogController.setDmDetail(dmDTO, this);

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
    public void updateDm(DmDTO dm){
        dmList.updateDM(dm);
        setDmDetails(
                dm.getName(),
                dm.getSurname(),
                dm.getId()
        );
        applyCardStyle();
    }

    public void deleteDM(DmDTO dm){
        dmFlowPane.getChildren().remove(dmNode);
        dmList.deleteDm(dm);
    }

    //endregion
    //region Other utilities
    public void setDmDetails(String name, String surname, String id){
        this.dmId = id;
        dmName.setText(name.toUpperCase());
        dmSurname.setText(surname);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/dursahn/dndstats/images/" + name.toLowerCase() + ".png")));
        imageView.setImage(image);
    }
    private void applyCardStyle() {
        DmDTO dm = dmList.getDmById(dmId);
        dmNode.setStyle("-fx-background-color: #" + dm.getColor() + "1A;" +
                "-fx-border-color: #" + dm.getColor());
    }
    //endregion
}
