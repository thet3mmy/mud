package page.rightshift.mud;

import page.rightshift.mud.stuff.ItemBase;
import page.rightshift.mud.world.Location;

import java.io.*;

public class Agent implements IAgent{
    String name;
    private int hp = 5;
    private int sp = 5;

    Agent(String n, int h, int s) {
        hp = h;
        sp = s;
        name = n;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getSp() {
        return sp;
    }

    @Override
    public void setHp(int h) {
        hp = h;
    }

    @Override
    public void setSp(int s) {
        sp = s;
    }
}
