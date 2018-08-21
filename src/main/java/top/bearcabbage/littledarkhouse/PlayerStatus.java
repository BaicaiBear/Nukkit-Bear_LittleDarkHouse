package top.bearcabbage.littledarkhouse;


import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PlayerStatus extends PluginBase {


    public Player[] inxhw;
    public int size=0;

    public boolean addPlayer(Player c){
        if(inxhw[size]!=null)
            return false;
        inxhw[size] = c;
        size++;
        return true;
    }

    public boolean delPlayer(Player c){
        int pos = Arrays.binarySearch(inxhw,c);
        if(pos<0)
            return false;
        if(inxhw[pos]==null)
            return false;
        if(inxhw[size]==null)
            return false;
        inxhw[pos] = inxhw[size];
        inxhw[size] = null;
        if(inxhw[size]!=null)
            return false;
        size--;
        return true;
    }

    public boolean findPlayer(Player c){
        for(Player i:inxhw)
            if(i==c)
                return true;
        return false;
    }

    public void savePlayer(){
        String[] tmp = new String[inxhw.length];
        for(int i=0;i<inxhw.length;i++)
            tmp[i] = inxhw[i].getName();
        List<String> save = Arrays.asList(tmp);
        Config a = new Config(getDataFolder().getPath()+"player.yml");
        a.set("在小黑屋里的玩家",save);
        a.save();
        return;
    }

    public void loadPlayer(int sze){
        inxhw = new Player[sze];
        Config a = new Config(new File(getDataFolder().getPath()+"player.yml"));
        List<String> tmp = a.getStringList("在小黑屋里的玩家");
        if(tmp==null)
            return;
        String[] b = new String[inxhw.length];
        tmp.toArray(b);
        for(int i=0;i<inxhw.length;i++)
            inxhw[i] = this.getServer().getPlayerExact(b[i]);
        return;
    }
}
