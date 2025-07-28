package dursahn.dndstats.managers;

import dursahn.dndstats.dto.DmDTO;
import dursahn.dndstats.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

public class DmList {
    private List<DmDTO> dms;

    public DmList(){
        dms = new ArrayList<>();
    }

    public List<DmDTO> getDms() {
        return dms;
    }

    public void addDM(DmDTO dm){
        dms.add(dm);
    }

    public void removeDM(DmDTO dm){
        dms.remove(dm);
    }
}
