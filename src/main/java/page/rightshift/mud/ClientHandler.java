package page.rightshift.mud;

import page.rightshift.mud.characters.PlayerBuilder;
import page.rightshift.mud.world.Portal;
import page.rightshift.mud.world.World;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

class ClientHandler implements Runnable {
    final private Socket s;
    final private DataInputStream in;
    final private DataOutputStream out;
    private HashMap<String, Player> players;
    private final World world;

    public ClientHandler(Socket s, DataInputStream in, DataOutputStream out, World world) {
        this.s = s;
        this.in = in;
        this.out = out;
        this.world = world;
    }

    @Override
    public void run() {
        String read = "";

        try {
            while (true) {
                out.writeUTF("Character name: ");
                read = in.readLine();

                PlayerBuilder builder = new PlayerBuilder("/home/tommy/mud/characters/characters.json", out);
                players = builder.build();
                Player c = players.get(read);

                assert c != null;
                c.enterLocation("1", world);

                out.writeUTF("\n");

                while(!read.equals("exit")) {
                    out.writeUTF("hp:"+ c.getHp() +" > ");
                    read = in.readLine();

                    switch(read.split("\\s+")[0]) {
                        case "stats":
                            if(c.xp > c.level * 8) {
                                out.writeUTF("YOU LEVELED UP: new level " + c.level);
                                c.xp -= c.level * 8;
                            }

                            out.writeUTF("\rname: " + c.name);
                            out.writeUTF("\rlevel: " + c.level + "\n");
                            out.writeUTF("xp: " + c.xp + "\n\n");
                            break;
                        case "l":
                        case "look":
                            c.lookAround(out);
                            break;
                        case "exits":
                            Iterator<Portal> it = c.currentLocation.portals.iterator();
                            int i = 0;
                            while(it.hasNext()) {
                                i++;
                                Portal portal = (Portal) it.next();
                                out.writeChars(i + ": " + portal.desc + "\n");
                            }
                            break;
                        default:
                            try {
                                int exitNumber = Integer.parseInt(read.split("\\s+")[0]);
                                c.enterLocation(exitNumber, world);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println(e);
                            }
                    }
                }

                out.writeUTF("closing connection...");
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}