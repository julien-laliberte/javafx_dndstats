package dursahn.dndstats.managers;

import dursahn.dndstats.dto.CharacterDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        sortNpcsByFirstName();
        saveNPCS();
        printNpcs();
    }

    public void updateNpcs(CharacterDTO udpate) throws FileNotFoundException {
        for(int i=0 ; i < npcs.size(); i++){
            CharacterDTO current = npcs.get(i);
            if(current.getId().equals(udpate.getId())){
                current.setFirstName(udpate.getFirstName());
                current.setLastName(udpate.getLastName());
                current.set_class(udpate.get_class());
                current.setSubclass(udpate.getSubclass());
                current.setClassLevel(udpate.getClassLevel());
                current.setColor(udpate.getColor());
                if(udpate.getClass2() != null) {
                    current.setClass2(udpate.getClass2());
                    current.setSubclass2(udpate.getSubclass2());
                    current.setClassLevel2(udpate.getClassLevel2());
                }
            }
        }
        sortNpcsByFirstName();
        saveNPCS();
    }

    private void sortNpcsByFirstName() {
        Collections.sort(
                npcs, new Comparator<CharacterDTO>() {
                    @Override
                    public int compare(CharacterDTO o1, CharacterDTO o2) {
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    }
                }
        );
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
        for (CharacterDTO npc : npcs) {
            System.out.println("Npcs: " + npc); // Dépend de toString() dans CharacterDTO
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

    public CharacterDTO getNpcById(String id){
        for(CharacterDTO npc: npcs){
            if(npc.getId().equals(id)){
                return npc;
            }
        }
        return null;
    }

    public void deleteNpc(CharacterDTO character) {
        npcs.remove(character);
        saveNPCS();
    }
}
