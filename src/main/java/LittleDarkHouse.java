package top.bearcabbage.littledarkhouse;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Location;
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
