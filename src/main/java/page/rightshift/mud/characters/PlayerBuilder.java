package page.rightshift.mud.characters;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import page.rightshift.mud.Player;

import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class PlayerBuilder {
    private String filename;
    private DataOutputStream out;

    public PlayerBuilder(String filename, DataOutputStream out) {
        this.filename = filename;
        this.out = out;
    }

    public HashMap<String, Player> build() {
        HashMap<String, Player> playersMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONArray charactersArray;
        JSONObject object;

        try {
            object = (JSONObject) parser.parse(new FileReader(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        charactersArray = (JSONArray) object.get("characters");
        Iterator it = charactersArray.iterator();
        while(it.hasNext()) {
            JSONObject obj = (JSONObject)it.next();
            String name = (String)obj.get("name");
            Player newPlayer = new Player(name, Integer.parseInt((String) obj.get("hp")), Integer.parseInt((String) obj.get("sp")), out);

            playersMap.put(name, newPlayer);
            System.out.println(newPlayer);
        }

        return playersMap;
    }
}
