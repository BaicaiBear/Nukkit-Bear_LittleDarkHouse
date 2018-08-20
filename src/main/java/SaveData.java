package top.bearcabbage.littledarkhouse;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;

public class SaveData extends PluginBase implements Listener{

    public void saveitem(String name,int[] items){
        saveResource("item.yml");
        this.getConfig().set(name,items);
        this.getConfig().save();
        return;
    }

    public void backitem(Player player){
        saveResource("item.yml");
        Item[] n = new Item[player.getInventory().getSize()];
        int[] tmp = (int[])this.getConfig().get(player.getName());
        for(int i:tmp){
            n[i] = new Item(tmp[i]);
            player.getInventory().setItem(i,n[i]);
        }
        return;
    }

    /*
        true为在小黑屋内，false为不在或其他
     */
    public void edits(Player player,boolean s){
        saveResource("player.yml");
        this.getConfig().set(player.getName(),s);
        this.getConfig().save();
        return;
    }

    public boolean finds(Player player){
        saveResource("player.yml");
        boolean re;
        re = this.getConfig().getBoolean(player.getName(),false);
        this.getConfig().save();
        return re;
    }
}
