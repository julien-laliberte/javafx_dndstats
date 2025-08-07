package dursahn.dndstats.managers;

import dursahn.dndstats.dto.DmDTO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DmList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String FILE_PATH = "src/main/data/dm.bin";

    private List<DmDTO> dms = new ArrayList<>();

    public DmList(){
        loadDms();
    }

    public List<DmDTO> getDms() {
        return dms;
    }

    public void addDM(DmDTO dm){
        dms.add(dm);
        saveDms();
        printDms();
    }

    private void printDms() {
        System.out.println("Current players in memory: " + dms.size());
        for (DmDTO dm : dms) {
            System.out.println("DM: " + dm); // Dépend de toString() dans CharacterDTO
        }
    }

    public DmDTO getDmById(String id){
        for(DmDTO dm : dms){
            if(dm.getId().equals(id)){
                return dm;
            }
        }
        return null;
    }

    public void updateDM(DmDTO update){
        for(int i=0; i < dms.size();i++){
            DmDTO current = dms.get(i);
            if(current.getId().equals(update.getId())){
                current.setName(update.getName());
                current.setSurname(update.getSurname());
                current.setColor(update.getColor());
            }
        }
        saveDms();
    }

    private void saveDms() {
        try(ObjectOutputStream oos =
                new ObjectOutputStream(
                        new FileOutputStream(FILE_PATH)
                )){
            System.out.println("Saving to: " + FILE_PATH);
            oos.writeObject(dms);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadDms() {
        File file = new File(FILE_PATH);
        if(file.exists()){
            try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(file)
                    )){
                dms = (List<DmDTO>) ois.readObject();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDm(DmDTO dm){
        dms.remove(dm);
        saveDms();
    }
}
