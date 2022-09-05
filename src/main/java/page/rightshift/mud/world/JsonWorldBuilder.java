package page.rightshift.mud.world;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonWorldBuilder implements WorldBuilder{
    private final String filename;

    public JsonWorldBuilder(String fileName) {
        this.filename = fileName;
    }

    @Override
    public World build() {
        JSONParser parser = new JSONParser();
        JSONObject worldJson;
        try {
            worldJson = (JSONObject) parser.parse(new FileReader(filename));
        } catch (IOException e) {
            throw new WorldBuildingException("Unable to read JSON file: " + filename, e);
        } catch (ParseException e) {
            throw new WorldBuildingException("Unable to parse JSON file: " + filename, e);
        }

        World world = new World();
        for (Object o : (JSONArray) worldJson.get("locations")) {
            Location newLocation = newLocation((JSONObject) o);
            world.addLocation(newLocation);
        }

        return world;
    }

    /** Creates a new {@link Location} based on the JSON representation. */
    private Location newLocation(JSONObject locationJson) {
        List<Portal> portals = new ArrayList<>();
        for (Object o : (JSONArray) locationJson.get("portals")) {
            portals.add(newPortal((JSONObject) o));
        }
        return new Location((String) locationJson.get("id"), (String) locationJson.get("desc"), portals);
    }

    /** Creates a new {@link Portal} based on the JSON representation. */
    private Portal newPortal(JSONObject portalJson) {
        return new Portal(
                (String)portalJson.get("id"),
                (String)portalJson.get("desc"),
                (String)portalJson.get("connectedId"),
                (String)portalJson.get("locationId")
        );
    }
}
