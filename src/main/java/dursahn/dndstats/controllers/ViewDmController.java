package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.DmDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewDmController {
    public ImageView imageView;
    public TextField dmName;
    public TextField dmSurname;
    public TextField dmColor;

    private DmDTO dm;
    private DmCardController dmCardController;
    private MainController mainController;
    private VBox rootNode;

    public void setRootNode(VBox rootNode){
        this.rootNode = rootNode;
    }

    public void setDmDetail(DmDTO dm, DmCardController dmCardController,
                            MainController mainController){
        this.dm = dm;
        this.dmCardController = dmCardController;
        this.mainController = mainController;

        dmName.setText(dm.getName());
        dmSurname.setText(dm.getSurname());
        dmColor.setText(dm.getColor());

        Image image = null;
        try {
            String imagePath = "/dursahn/dndstats/images/" + dm.getName().toLowerCase() + ".png";
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (NullPointerException e) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dursahn/dndstats/images/default.png")));
            System.out.println("Image not found for " + dm.getName() + ", using default.png");
        }
        imageView.setImage(image);
        rootNode.setStyle("-fx-background-color: #" + dm.getColor() + "1A;" +
                "-fx-border-color: #" + dm.getColor());
    }

    public void handleDelete(ActionEvent actionEvent) {
        dmCardController.deleteDM(dm);
        mainController.redrawAllCards();
        closeDialog();
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeDialog();
    }

    public void handleUpdate(ActionEvent actionEvent) {
        dm.setName(dmName.getText());
        dm.setSurname(dmSurname.getText());
        dm.setColor(dmColor.getText());
        dmCardController.updateDm(dm);
        mainController.redrawAllCards();
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) dmName.getScene().getWindow();
        stage.close();
    }
}
