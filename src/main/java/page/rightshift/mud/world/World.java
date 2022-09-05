package page.rightshift.mud.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {
    private List<Location> locations = new ArrayList<Location>();
    private HashMap<String, Location> locationsMap = new HashMap<>();

    public void addLocation(Location location) {
        locationsMap.put(location.id, location);
        locations.add(location);
    }

    public Location getLocation(String id) {
        return this.locationsMap.get(id);
    }
}
