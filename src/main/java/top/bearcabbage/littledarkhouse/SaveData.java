package top.bearcabbage.littledarkhouse;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.google.common.primitives.Ints;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class SaveData extends PluginBase implements Listener{

    public void saveitem(String name,Item[] items){
        Config a = new Config("item.yml");
        List<Item> ii = Arrays.asList(items);
        a.set(name,ii);
        a.save();
        return;
    }

    public void backitem(Player player){
        Item[] n;
        Config a = new Config("item.yml");
        List<Item> s = a.getList(player.getName());
        n = s.toArray(new Item[s.size()]);
        for(int i=0;i<s.size();i++){
            player.getInventory().setItem(i,n[i]);
        }
        a.remove(player.getName());
        a.save();
        return;
    }
/*

        //true为在小黑屋内，false为不在或其他

    public void edits(Player player,boolean s){
        Config a = new Config("player.yml");
        a.getBoolean(player.getName(),s);
        a.save();
        return;
    }

    public boolean finds(Player player){
        boolean re;
        Config a = new Config();
        try{
            a = new Config(this.getDataFolder().getPath()+"player.yml");
        }
        catch(Exception e)
        {
            this.getLogger().warning("eeeee||"+e.getMessage());
        }
        re = a.getBoolean(player.getName(),false);
        a.save();
        return re;
    }
    */
}
