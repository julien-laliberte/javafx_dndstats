package dursahn.dndstats.managers;

import dursahn.dndstats.dto.CharacterDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "src/main/data/players.bin";

    private List<CharacterDTO> players = new ArrayList<>();

    public PlayerList() {
        sortPlayerByFirstName();
        loadPlayers(); // Charger les joueurs existants au démarrage
    }

    public List<CharacterDTO> getPlayers() {
        return players;
    }

    public void addPlayer(CharacterDTO character) {
        players.add(character);
        sortPlayerByFirstName();
        savePlayers();
        printPlayers();
    }

    private void printPlayers() {
        System.out.println("Current players in memory: " + players.size());
        for (CharacterDTO player : players) {
            System.out.println("Player: " + player); // Dépend de toString() dans CharacterDTO
        }
    }

    public List<String> getPlayersName(){
        List<String> names = new ArrayList<>();
        for(CharacterDTO player: players){
            names.add(player.getFirstName());
        }
        return names;
    }

    public CharacterDTO getPlayerById(String id){
        for(CharacterDTO player: players){
            if(player.getId().equals(id)){
                return player;
            }
        }
        return null;
    }

    public void updatePlayers(CharacterDTO update) {
        for(int i=0 ; i<players.size(); i++){
            CharacterDTO current = players.get(i);
            if(current.getId().equals(update.getId())){
                current.setFirstName(update.getFirstName());
                current.setLastName(update.getLastName());
                current.set_class(update.get_class());
                current.setSubclass(update.getSubclass());
                current.setClassLevel(update.getClassLevel());
                current.setColor(update.getColor());
                if(update.getClass2() != null) {
                    current.setClass2(update.getClass2());
                    current.setSubclass2(update.getSubclass2());
                    current.setClassLevel2(update.getClassLevel2());
                }
            }
        }
        sortPlayerByFirstName();
        savePlayers();
    }

    private void sortPlayerByFirstName() {
        Collections.sort(
                players, new Comparator<CharacterDTO>() {
                    @Override
                    public int compare(CharacterDTO o1, CharacterDTO o2) {
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    }
                }
        );
        for(CharacterDTO player: players){
            System.out.println(player.getFirstName());
        }
    }

    private void savePlayers() {
        try {
            Path filePath = Paths.get(FILE_PATH);
            Files.createDirectories(filePath.getParent());
            System.out.println("Saving to: " + filePath.toAbsolutePath());
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
                oos.writeObject(players);
                System.out.println("Save successful");
            }
        } catch (IOException e) {
            System.err.println("Error saving players: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadPlayers() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois =
                         new ObjectInputStream(
                                 new FileInputStream(file)
                         )) {
                players =(List<CharacterDTO>) ois.readObject();
            } catch (IOException e) {
                System.err.println("Error reading players: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found during deserialization: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void deletePlayer(CharacterDTO character) {
        players.remove(character);
        savePlayers();
    }


}