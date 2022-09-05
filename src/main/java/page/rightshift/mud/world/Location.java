package page.rightshift.mud.world;

import page.rightshift.mud.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Location {
    public String id;
    public String desc;
    public List<Portal> portals;
    public List<Player> players;

    public Location(String id, String desc, List<Portal> portals) {
        this.id = id;
        this.desc = desc;
        this.portals = portals;
        this.players = new ArrayList<Player>();
    }

    public void enter(Player player) {
        Iterator<Player> iterator = players.iterator();
        while(iterator.hasNext()) {
            try {
                iterator.next().getOutputStream().writeChars("\nPlayer arrives\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        players.add(player);
    }

    public void leave(Player player) {
        Iterator<Player> iterator = players.iterator();
        while(iterator.hasNext()) {
            try {
                iterator.next().getOutputStream().writeChars("\nPlayer leaves\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        players.remove(player);
    }
}
