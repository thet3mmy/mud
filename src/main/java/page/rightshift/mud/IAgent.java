package page.rightshift.mud;

import page.rightshift.mud.world.Location;

public interface IAgent {
    public int getHp();
    public int getSp();
    public void setHp(int h);
    public void setSp(int s);
}
