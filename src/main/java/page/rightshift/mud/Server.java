package page.rightshift.mud;

import page.rightshift.mud.world.JsonWorldBuilder;
import page.rightshift.mud.world.World;
import page.rightshift.mud.world.WorldBuilder;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static World world;
    private static ExecutorService threadPool;

    Server() {
        JsonWorldBuilder builder = new JsonWorldBuilder("/home/tommy/mud/locations/world.json");
        this.world = builder.build();
    }

    public static void main(String args[]) throws IOException {
        new Server();
        world = WorldBuilder.newJsonWorldBuilder("/home/tommy/mud/locations/world.json").build();

        threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("SERVER: Started port 5000");

        while(true) {
            Socket s = null;

            try {
                s = serverSocket.accept();
                System.out.println("SERVER: New client: " + s);

                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                System.out.println("SERVER: Assigning new thread for client");

                threadPool.execute(new ClientHandler(s, in, out, world));
            } catch(IOException ie) {
                s.close();
                System.out.println(ie);
            } catch (Exception e) {
                s.close();
                System.out.println(e);
            }
        }
    }
}
