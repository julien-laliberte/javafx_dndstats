package dursahn.dndstats.controllers;

import dursahn.dndstats.dto.CharacterDTO;
import dursahn.dndstats.dto.DmDTO;
import dursahn.dndstats.managers.DmList;
import dursahn.dndstats.managers.NpcList;
import dursahn.dndstats.managers.PlayerList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.*;

public class EditGameController {
    public GridPane playersGrid;
    public GridPane npcsGrid;
    public GridPane dmGrid;

    private PlayerList playerList;
    private NpcList npcList;
    private DmList dmList;

    private MainController mainController;

    private Map<CheckBox, CharacterDTO> playerCheckBoxMap = new HashMap<>();
    private Map<CheckBox, CharacterDTO> npcCheckBoxMap = new HashMap<>();
    private Map<CheckBox, DmDTO> dmCheckBoxMap = new HashMap<>();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setAllLists(PlayerList playerList,
                            NpcList npcList,
                            DmList dmList) {
        this.playerList = playerList;
        this.npcList = npcList;
        this.dmList = dmList;

        remplirGrid(playersGrid, playerList.getPlayers(), playerCheckBoxMap, true);
        remplirGrid(npcsGrid, npcList.getNpcs(), npcCheckBoxMap, false);
        remplirGridDm(dmGrid, dmList.getDms(), dmCheckBoxMap);
    }

    private void remplirGrid(GridPane grid,
                             List<CharacterDTO> characters,
                             Map<CheckBox, CharacterDTO> checkBoxMap,
                             boolean isPlayer) {
        int colonnes = 5;
        for (int i = 0; i < characters.size(); i++) {
            int row = i / colonnes;
            int col = i % colonnes;

            CharacterDTO character = characters.get(i);
            CheckBox checkBox = new CheckBox(character.getFirstName());
            checkBox.setSelected(isPlayer ? (character.getVisible() != null && character.getVisible()) : character.getVisible());
            checkBoxMap.put(checkBox, character);
            grid.add(checkBox, col, row);
        }
    }

    private void remplirGridDm(GridPane grid,
                               List<DmDTO> dms,
                               Map<CheckBox, DmDTO> checkBoxMap) {
        int colonnes = 5;
        for (int i = 0; i < dms.size(); i++) {
            int row = i / colonnes;
            int col = i % colonnes;

            DmDTO dm = dms.get(i);
            CheckBox checkBox = new CheckBox(dm.getName());
            checkBox.setSelected(dm.getVisible());
            checkBoxMap.put(checkBox, dm);
            grid.add(checkBox, col, row);
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeView();
    }

    public void handleEdit(ActionEvent actionEvent) throws FileNotFoundException {

        for (CheckBox checkBox : playerCheckBoxMap.keySet()) {
            CharacterDTO player = playerCheckBoxMap.get(checkBox);
            player.setVisible(checkBox.isSelected());
            playerList.updatePlayers(player);
        }
        for(CheckBox checkBox: npcCheckBoxMap.keySet()){
            CharacterDTO npc = npcCheckBoxMap.get(checkBox);
            npc.setVisible(checkBox.isSelected());
            npcList.updateNpcs(npc);
        }
        for(CheckBox checkBox: dmCheckBoxMap.keySet()){
            DmDTO dm = dmCheckBoxMap.get(checkBox);
            dm.setVisible(checkBox.isSelected());
            dmList.updateDM(dm);
        }

        mainController.redrawAllCards();
        closeView();
    }

    private void closeView(){
        Stage stage = (Stage) playersGrid.getScene().getWindow();
        stage.close();
    }
}