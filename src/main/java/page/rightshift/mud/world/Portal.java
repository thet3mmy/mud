package page.rightshift.mud.world;

import page.rightshift.mud.Agent;
import page.rightshift.mud.Player;

public class Portal {
    public String id;
    public String desc;
    public String connectedId;
    public String locationId;

    public Portal(String id, String desc, String connectedId, String locationId) {
        this.id = id;
        this.desc = desc;
        this.connectedId = connectedId;
        this.locationId = locationId;
    }

    public void enter(Player player, World world) {
        player.getLocation().leave(player);
        Location newLocation = world.getLocation(this.locationId);
        player.setLocation(newLocation);
        newLocation.enter(player);
    }
}
