package top.bearcabbage.littledarkhouse;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Location;package top.bearcabbage.littledarkhouse;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;

public class LittleDarkHouse extends PluginBase implements Listener
{
    public int x,y,z;
    public String levelname;
    public Location xhw;

    public boolean ynsave;

    public void onEnable()
    {
        saveResource("config.yml");
        this.getServer().getPluginManager().registerEvents(this,this);
        levelname = this.getServer().getConfig().getString("监狱所在的世界","world");
        x = this.getServer().getConfig().getInt("监狱X坐标",0);
        y = this.getServer().getConfig().getInt("监狱Y坐标",0);
        z = this.getServer().getConfig().getInt("监狱Z坐标",0);
        xhw = new Location(x, y, z, this.getServer().getLevelByName(levelname));
        ynsave = this.getConfig().getBoolean("是否为进入监狱的玩家在清空物品栏之前临时保存物品",true);
        this.getLogger().info("§6小黑屋插件已加载成功，作者:小熊白菜，此插件完全免费。§3作者邮箱:xiaoxiongbaicai@outlook.com");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player c;
        c = event.getPlayer();
        new top.bearcabbage.littledarkhouse.SaveData().finds(c);
        return;
    }

    public boolean onCommand(CommandSender sender, Command command, String zhiling, String[] liebiao)
    {
        if(zhiling.equals("setxhw"))
        {
            Location locall;
            Player c;
            c = this.getServer().getPlayer(sender.getName());
            locall = c.getLocation();
            this.getServer().getConfig().set("监狱X坐标", locall.x);
            this.getServer().getConfig().set("监狱Y坐标", locall.y);
            this.getServer().getConfig().set("监狱Z坐标", locall.z);
            this.getServer().getConfig().set("监狱所在的地图", locall.level.getName());
            this.getServer().getConfig().save();
        }
        else if(zhiling.equals("xhw")) {
            if (liebiao.length == 1) {
                Player c;
                c = this.getServer().getPlayerExact(liebiao[0]);
                boolean s;
                s = new SaveData().finds(c);
                if (s == true) {
                    sender.sendMessage("[小黑屋]§4对方已在小黑屋里了哦。");
                    return false;
                }
                try {
                    if (ynsave == true) {
                        int ss = c.getInventory().getSize();
                        int[] playeritem = new int[ss];
                        for (int i = 0; i < ss; i++) {
                            playeritem[i] = c.getInventory().getItem(i).getId();
                            new top.bearcabbage.littledarkhouse.SaveData().saveitem(c.getName(), playeritem);
                        }
                    }
                    c.getInventory().clearAll();
                    c.setGamemode(2);
                    c.teleport(xhw);
                    c.setSpawn(new Position(x, y, z, this.getServer().getLevelByName(levelname)));
                    new SaveData().edits(c, true);
                    c.sendMessage("[小黑屋]您已被关进小黑屋，请不要试图逃离，否则后果自负。");
                    sender.sendMessage("[小黑屋]" + c.getName() + "以被您关进小黑屋，记得放他出来哦。");
                    this.getServer().broadcastMessage("§5[小黑屋]" + c.getName() + "已被管理员关进小黑屋，请其他玩家检点自己的行为。");
                } finally {
                    sender.sendMessage("[小黑屋]§4发生错误！");
                    return true;
                }
            }
            else
            {
                sender.sendMessage("[小黑屋]请使用/xhw 玩家名 来使用小黑屋功能");
                return false;
            }
        }
        else if(zhiling.equals("out")) {
            if (liebiao.length == 1) {
                Player c;
                c = this.getServer().getPlayerExact(liebiao[0]);
                if (new SaveData().finds(c) == false) {
                    sender.sendMessage("[小黑屋]§4对方不在小黑屋内，请检查拼写。");
                    return false;
                }
                c.teleport(this.getServer().getDefaultLevel().getSafeSpawn());
                new SaveData().backitem(c);
                c.setSpawn(this.getServer().getDefaultLevel().getSafeSpawn());
                c.setGamemode(0);
                new SaveData().edits(c, false);
                c.sendMessage("§b[小黑屋]你已经被放出了小黑屋，记得一定要认真做人哦。");
                this.getServer().broadcastMessage("§a[小黑屋]玩家§c" + c.getName() + "§a已被放出小黑屋。");
                return true;
            }
            else
            {
                sender.sendMessage("[小黑屋]您的输入不正确，请使用 /out 玩家名 来将玩家放出小黑屋。");
                return false;
            }
        }

        return false;
    }
}

import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;

public class LittleDarkHouse extends PluginBase implements Listener
{
    public int x,y,z;
    public String levelname;
    public Location xhw;
    public void onEnable()
    {
        saveResource("config.yml");
        this.getServer().getPluginManager().registerEvents(this,this);
        levelname = this.getServer().getConfig().getString("监狱所在的世界","world");
        x = this.getServer().getConfig().getInt("监狱X坐标",0);
        y = this.getServer().getConfig().getInt("监狱Y坐标",0);
        z = this.getServer().getConfig().getInt("监狱Z坐标",0);
        xhw = new Location(x, y, z, this.getServer().getLevelByName(levelname));
        this.getLogger().info("§6小黑屋插件已加载成功，作者:小熊白菜，此插件完全免费。§3作者邮箱:xiaoxiongbaicai@outlook.com");
    }

    public boolean onCommand(CommandSender sender, Command command, String zhiling, String[] liebiao)
    {
        if(zhiling.equals("setxhw"))
        {
            Location locall;
            Player c;
            c = this.getServer().getPlayer(sender.getName());
            locall = c.getLocation();
            this.getServer().getConfig().set("监狱X坐标", locall.x);
            this.getServer().getConfig().set("监狱Y坐标", locall.y);
            this.getServer().getConfig().set("监狱Z坐标", locall.z);
            this.getServer().getConfig().set("监狱所在的地图", locall.level.getName());
            this.getServer().getConfig().save();
        }
        else if(zhiling.equals("xhw"))
        {
            if(liebiao.length==1)
            {
                Player c;
                c = this.getServer().getPlayerExact(liebiao[0]);
                try{
                    c.getInventory().clearAll();
                    c.setGamemode(2);
                    c.teleport(xhw);
                    c.setSpawn(new Vector3(x,y, z));
                    c.sendMessage("[小黑屋]您已被关进小黑屋，请不要试图逃离，否则后果自负。");
                    sender.sendMessage("[小黑屋]"+c.getName()+"以被您关进小黑屋，记得放他出来哦。");
                    this.getServer().getLogger().info("§5[小黑屋]"+c.getName()+"已被管理员关进小黑屋，请其他玩家检点自己的行为。");
                }finally {
                    sender.sendMessage("[小黑屋]§4发生错误！");
                    return true;
                }
            }
            else
            {
                sender.sendMessage("[小黑屋]请使用/xhw 玩家名 来使用小黑屋功能");
                return false;
            }
        }

        return false;
    }
}
