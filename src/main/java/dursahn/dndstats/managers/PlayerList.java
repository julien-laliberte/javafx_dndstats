package dursahn.dndstats.managers;

import dursahn.dndstats.dto.CharacterDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlayerList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "src/main/data/players.bin";

    private final List<CharacterDTO> players = new ArrayList<>();

    public PlayerList() {
        loadPlayers(); // Charger les joueurs existants au démarrage
    }

    public List<CharacterDTO> getPlayers() {
        return players;
    }

    public void addPlayer(CharacterDTO character) {
        players.add(character);
        savePlayer();
        printPlayers(); // Afficher les joueurs pour débogage
    }

    public void removePlayer(CharacterDTO character) {
        players.remove(character);
        savePlayer();
    }

    private void savePlayer() {
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

    private void loadPlayers() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    players.clear(); // Vider la liste actuelle
                    for (Object item : (List<?>) obj) {
                        if (item instanceof CharacterDTO) {
                            players.add((CharacterDTO) item);
                        } else {
                            System.err.println("Unexpected object type in file: " + item.getClass().getName());
                        }
                    }
                    System.out.println("Loaded " + players.size() + " players from file.");
                } else {
                    System.err.println("File content is not a List. Type: " + obj.getClass().getName());
                }
            } catch (IOException e) {
                System.err.println("Error reading players: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found during deserialization: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No existing player file found at: " + file.getAbsolutePath());
        }
    }

    private void printPlayers() {
        System.out.println("Current players in memory: " + players.size());
        for (CharacterDTO player : players) {
            System.out.println("Player: " + player); // Dépend de toString() dans CharacterDTO
        }
    }
}