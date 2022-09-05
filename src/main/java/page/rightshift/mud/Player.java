package page.rightshift.mud;

import page.rightshift.mud.world.Location;
import page.rightshift.mud.world.World;

import java.io.DataOutputStream;
import java.io.IOException;

public class Player extends Agent {
    Location currentLocation = null;
    DataOutputStream outputStream;
    int level;
    int xp;

    public Player(String n, int h, int s, DataOutputStream out) { super(n, h, s); outputStream = out; }

    public void lookAround(DataOutputStream out) {
        try {
            out.writeChars(currentLocation.desc);
            out.writeUTF("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void enterLocation(String id, World world) {
        if(currentLocation != null) { currentLocation.leave(this); }
        currentLocation = world.getLocation(id);
        currentLocation.enter(this);
    }

    public void enterLocation(int exitNumber, World world) {
        if(currentLocation != null) { currentLocation.leave(this); }
        assert currentLocation != null;
        currentLocation = world.getLocation(currentLocation.portals.get(exitNumber - 1).locationId);
        currentLocation.enter(this);
    }

    public Location getLocation() {
        return currentLocation;
    }

    public void setLocation(Location location) {
        currentLocation = location;
    }

    public DataOutputStream getOutputStream() { return outputStream; }
}
