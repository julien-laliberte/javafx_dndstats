package dursahn.dndstats.service;

import dursahn.dndstats.controllers.MainController;
import dursahn.dndstats.dto.CharacterDTO;
import dursahn.dndstats.dto.DmDTO;
import dursahn.dndstats.managers.DmList;
import dursahn.dndstats.managers.NpcList;
import dursahn.dndstats.managers.PlayerList;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Calcul {
    private PlayerList playerList;
    private NpcList npcList;
    private DmList dmList;
    private Map<CheckBox, CharacterDTO> playerCheckBoxMap;
    private Map<CheckBox, CharacterDTO> npcCheckBoxMap;
    private Map<CheckBox, DmDTO> dmCheckBoxMap;
    private MainController mainController;

    public Calcul(PlayerList playerList, NpcList npcList, DmList dmList,
                  Map<CheckBox, CharacterDTO> playerCheckBoxMap,
                  Map<CheckBox, CharacterDTO> npcCheckBoxMap,
                  Map<CheckBox, DmDTO> dmCheckBoxMap,
                  MainController mainController) {
        this.playerList = playerList;
        this.npcList = npcList;
        this.dmList = dmList;
        this.playerCheckBoxMap = playerCheckBoxMap;
        this.npcCheckBoxMap = npcCheckBoxMap;
        this.dmCheckBoxMap = dmCheckBoxMap;
        this.mainController = mainController;
    }

    public void update(PlayerList playerList, NpcList npcList, DmList dmList,
                       Map<CheckBox, CharacterDTO> playerCheckBoxMap,
                       Map<CheckBox, CharacterDTO> npcCheckBoxMap,
                       Map<CheckBox, DmDTO> dmCheckBoxMap,
                       MainController mainController) {
        this.playerList = playerList;
        this.npcList = npcList;
        this.dmList = dmList;
        this.playerCheckBoxMap = playerCheckBoxMap;
        this.npcCheckBoxMap = npcCheckBoxMap;
        this.dmCheckBoxMap = dmCheckBoxMap;
        this.mainController = mainController;
    }

    public void calculate(int number, String operation) {
        DmDTO dm = mainController.getActiveDm().isEmpty() ? null : mainController.getActiveDm().get(0);

        Map<String, BiConsumer<CharacterDTO, Integer>> characterOps = new HashMap<>();
        characterOps.put("Dégâts faits", (c, n) -> addDamageDone(c, dm, n, true));
        characterOps.put("Dégâts reçus", (c, n) -> addDamageReceived(c, dm, n, true));
        characterOps.put("Dégâts alliés", (c, n) -> addAlliedDamage(c, n, true));
        characterOps.put("Soins faits", (c, n) -> addHealDone(c, n, true));
        characterOps.put("Soins reçus", (c, n) -> addHealReceived(c, n, true));
        characterOps.put("Soins personnels", (c, n) -> addPersonalHeal(c, n, true));
        characterOps.put("Critique", (c, n) -> addCritical(c, n, true));
        characterOps.put("Échec", (c, n) -> addFailure(c, n, true));
        characterOps.put("Ennemis contrôlés", (c, n) -> addMinionControlled(c, dm, n, true));
        characterOps.put("Ennemis tués", (c, n) -> addMinionKilled(c, dm, n, true));
        characterOps.put("Boss tués", (c, n) -> addBossKilled(c, dm, n, true));
        characterOps.put("Contrôlé", (c, n) -> addControlled(c, dm, n, true));
        characterOps.put("KO", (c, n) -> addKnockOut(c, dm, n, true));

        for (CheckBox checkBox : playerCheckBoxMap.keySet()) {
            if (checkBox.isSelected()) {
                CharacterDTO player = playerCheckBoxMap.get(checkBox);
                characterOps.getOrDefault(operation, (c, n) -> System.out.println("Mauvaise opération")).accept(player, number);
            }
        }

        if (!mainController.getActiveNpc().isEmpty()) {
            Map<String, BiConsumer<CharacterDTO, Integer>> npcOps = new HashMap<>(characterOps);
            for (CheckBox checkBox : npcCheckBoxMap.keySet()) {
                if (checkBox.isSelected()) {
                    CharacterDTO npc = npcCheckBoxMap.get(checkBox);
                    npcOps.getOrDefault(operation, (c, n) -> System.out.println("Mauvaise opération!")).accept(npc, number);
                }
            }
        }

        Map<String, BiConsumer<DmDTO, Integer>> dmOps = new HashMap<>();
        dmOps.put("Soins personnels", this::addPersonalHeal);
        dmOps.put("Critique", this::addCritical);
        dmOps.put("Failure", this::addFailure); // Correction de "Failure" en "Échec" si nécessaire
        for (CheckBox checkBox : dmCheckBoxMap.keySet()) {
            if (checkBox.isSelected()) {
                DmDTO dmEntity = dmCheckBoxMap.get(checkBox);
                dmOps.getOrDefault(operation, (d, n) -> System.out.println("Mauvaise opération!")).accept(dmEntity, number);
            }
        }
    }

    private void addDamageDone(CharacterDTO character, DmDTO dm,
                               int number, boolean isPlayer){
        character.setDamageDone(character.getDamageDone() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setDamageReceived(dm.getDamageReceived() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addDamageReceived(CharacterDTO character, DmDTO dm,
                                   int number, boolean isPlayer){
        character.setDamageReceived(character.getDamageReceived() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setDamageDone(dm.getDamageDone() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addAlliedDamage(CharacterDTO character, int number, boolean isPlayer){
        character.setAlliedDamage(character.getAlliedDamage() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);
    }

    private void addHealDone(CharacterDTO character, int number, boolean isPlayer){
        character.setHealDone(character.getHealDone() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);
    }

    private void addHealReceived(CharacterDTO character, int number, boolean isPlayer){
        character.setHealReceived(character.getHealReceived() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);
    }

    private void addPersonalHeal(CharacterDTO character, int number, boolean isPlayer){
        character.setPersonalHeal(character.getPersonalHeal() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);
    }

    private void addPersonalHeal(DmDTO dm, int number){
        dm.setPersonalHeal(dm.getPersonalHeal() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addCritical(CharacterDTO character, int number, boolean isPlayer){
        character.setCritical(character.getCritical() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);
    }

    private void addCritical(DmDTO dm, int number){
        dm.setCritical(dm.getCritical() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);

    }

    private void addFailure(CharacterDTO character, int number, boolean isPlayer){
        character.setFailure(character.getFailure() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

    }

    private void addFailure(DmDTO dm, int number){
        dm.setFailure(dm.getFailure() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addMinionControlled(CharacterDTO character, DmDTO dm, int number, boolean isPlayer){
        character.setMinionControlled(character.getMinionControlled() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setCrowdControlled(dm.getCrowdControlled() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addMinionKilled(CharacterDTO character, DmDTO dm, int number, boolean isPlayer){
        character.setMinionKilled(character.getMinionKilled() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setMinionLost(dm.getMinionLost() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addBossKilled(CharacterDTO character, DmDTO dm, int number, boolean isPlayer){
        character.setBossKilled(character.getBossKilled() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setBossLost(dm.getBossLost() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addControlled(CharacterDTO character, DmDTO dm, int number, boolean isPlayer){
        character.setControlled(character.getControlled() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setPlayerControlled(dm.getPlayerControlled() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }

    private void addKnockOut(CharacterDTO character, DmDTO dm, int number, boolean isPlayer){
        character.setKnockOut(character.getKnockOut() + number);
        if(isPlayer){
            playerList.updatePlayers(character);
        } else {
            npcList.updateNpcs(character);
        }
        mainController.updateCharacterCard(character);

        dm.setPlayerKOs(dm.getPlayerKOs() + number);
        dmList.updateDM(dm);
        mainController.updateDmCard(dm);
    }
}
