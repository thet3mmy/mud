package page.rightshift.mud.stuff;

import page.rightshift.mud.Agent;

import java.io.DataOutputStream;

public interface IItem {
    public void inspect(DataOutputStream out);
    public void equip(Agent a);
    public void unequip(Agent a);
}
