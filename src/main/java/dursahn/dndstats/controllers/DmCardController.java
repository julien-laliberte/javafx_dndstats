package dursahn.dndstats.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class DmCardController {
    public Label dmName;
    public Label dmSurname;
    public ImageView imageView;

    public void handleDmEdit(ActionEvent actionEvent) {
        System.out.println("Editing npc: " + dmName.getText());
        // TODO: À faire
    }

    public void setDmDetails(String name, String _class){
        dmName.setText(name.toUpperCase());
        dmSurname.setText(_class);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/dursahn/dndstats/images/" + name.toLowerCase() + ".png")));
        imageView.setImage(image);
    }
}
