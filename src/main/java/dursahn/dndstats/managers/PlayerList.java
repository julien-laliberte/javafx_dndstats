package dursahn.dndstats.managers;

import dursahn.dndstats.dto.CharacterDTO;
import dursahn.dndstats.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {

    private List<CharacterDTO> players;

    public PlayerList(){
        players = new ArrayList<>();
    }

    public List<CharacterDTO> getPlayers() {
        return players;
    }

    public void addPlayer(CharacterDTO character){
        players.add(character);
    }

    public void removePlayer(CharacterDTO character){
        players.remove(character);
    }
}
