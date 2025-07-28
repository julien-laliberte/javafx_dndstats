package dursahn.dndstats.managers;

import dursahn.dndstats.dto.CharacterDTO;
import dursahn.dndstats.dto.NpcDTO;

import java.util.ArrayList;
import java.util.List;

public class NpcList {
    private List<CharacterDTO> npcs;

    public NpcList(){
        npcs = new ArrayList<>();
    }

    public List<CharacterDTO> getNpc() {
        return npcs;
    }

    public void addNpc(CharacterDTO character){
        npcs.add(character);
    }

    public void removeNpc(CharacterDTO character){
        npcs.remove(character);
    }
}
