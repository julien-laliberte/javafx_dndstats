package dursahn.dndstats.managers;

import dursahn.dndstats.dto.CharacterDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NpcList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String FILE_PATH = "src/main/data/npcs.bin";

    private List<CharacterDTO> npcs = new ArrayList<>();

    public NpcList(){
        loadNpcs();
    }

    public List<CharacterDTO> getNpc() {
        return npcs;
    }

    public void addNpc(CharacterDTO character){
        npcs.add(character);
        saveNPCS();
        printNpcs();
    }

    public void removeNpc(CharacterDTO character){
        npcs.remove(character);
    }

    private void saveNPCS(){
        try(ObjectOutputStream oos =
                new ObjectOutputStream(
                        new FileOutputStream(FILE_PATH)
                )){
            System.out.println("Saving to: " + FILE_PATH);
            oos.writeObject(npcs);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void printNpcs() {
        System.out.println("Current players in memory: " + npcs.size());
        for (CharacterDTO player : npcs) {
            System.out.println("Player: " + player); // Dépend de toString() dans CharacterDTO
        }
    }

    private void loadNpcs(){
        File file = new File(FILE_PATH);
        if(file.exists()){
            try(ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file)
            )) {
                npcs = (List<CharacterDTO>) ois.readObject();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
