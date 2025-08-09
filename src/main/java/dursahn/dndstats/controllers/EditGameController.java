package dursahn.dndstats.controllers;

import dursahn.dndstats.managers.DmList;
import dursahn.dndstats.managers.NpcList;
import dursahn.dndstats.managers.PlayerList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class EditGameController {
    public GridPane playersGrid;
    public GridPane npcsGrid;
    public GridPane dmGrid;

    private PlayerList playerList;
    private NpcList npcList;
    private DmList dmList;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setAllLists(PlayerList playerList, NpcList npcList, DmList dmList) {
        this.playerList = playerList;
        this.npcList = npcList;
        this.dmList = dmList;

        remplirGrid(playersGrid, playerList.getPlayersName());
        remplirGrid(npcsGrid, npcList.getNpcsName());
        remplirGrid(dmGrid, dmList.getDmsName());
    }

    private void remplirGrid(GridPane grid, List<String> noms) {
        int colonnes = 3;

        for (int i = 0; i < noms.size(); i++) {
            int row = i / colonnes;
            int col = i % colonnes;

            CheckBox checkBox = new CheckBox(noms.get(i));
            grid.add(checkBox, col, row);
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeView();
    }

    public void handleEdit(ActionEvent actionEvent) {
        closeView();
    }

    private void closeView(){
        Stage stage = (Stage) playersGrid.getScene().getWindow();
        stage.close();
    }
}
