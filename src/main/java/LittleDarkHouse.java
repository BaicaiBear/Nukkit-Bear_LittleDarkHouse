package top.bearcabbage.littledarkhouse;

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
    public double x,y,z;
    public String levelname;
    public Location xhw;

    public boolean ynsave;

    public void onLoad()
    {
        saveResource("player.yml",false);
        saveResource("item.yml",false);
        saveDefaultConfig();
        this.getLogger().info("§6小黑屋插件正在加载...");
    }

    public void onEnable()
    {
        saveResource("config.yml");
        this.getServer().getPluginManager().registerEvents(this,this);
        levelname = this.getServer().getConfig().getString("监狱所在的世界","world");
        x = this.getServer().getConfig().getDouble("监狱X坐标",0);
        y = this.getServer().getConfig().getDouble("监狱Y坐标",0);
        z = this.getServer().getConfig().getDouble("监狱Z坐标",0);
        xhw = new Location(x, y, z, this.getServer().getLevelByName(levelname));
        ynsave = this.getConfig().getBoolean("是否在进入监狱前清空玩家物品",true);
        this.getServer().getConfig().save();
        this.getLogger().info("§6小黑屋插件已加载成功，作者:小熊白菜，此插件完全免费。§3作者邮箱:xiaoxiongbaicai@outlook.com");
    }

    public void onDisable()
    {
        this.getServer().getConfig().save();
    }
    /*
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player c;
        c = event.getPlayer();
        new top.bearcabbage.littledarkhouse.SaveData().finds(c);
        return;
    }
    */
    public boolean onCommand(CommandSender sender, Command command, String zhiling, String[] liebiao)
    {
        if(zhiling.equals("setxhw"))
        {
            if(!sender.isPlayer()){
                this.getServer().getLogger().warning("[小黑屋]请在游戏内执行此指令");
                return false;
            }
            try{
                Location locall;
                locall = this.getServer().getPlayerExact(sender.getName()).getLocation();
                this.getServer().getConfig().set("监狱X坐标", locall.getX());
                this.getServer().getConfig().set("监狱Y坐标", locall.getY());
                this.getServer().getConfig().set("监狱Z坐标", locall.getZ());
                this.getServer().getConfig().set("监狱所在的地图", locall.getLevel().getName());
                this.getServer().getConfig().save();
                //sender.sendMessage("小黑屋位置"+locall.getX()+" "+locall.getY()+" "+locall.getZ()+" "+locall.getLevel().getName());
                this.onEnable();
            }
            catch (Exception e){
                sender.sendMessage("§[小黑屋]未知错误！");
                return false;
            }
            sender.sendMessage("[小黑屋]§c小黑屋位置设定成功为 地图"+levelname+" "+x+ " "+y+" "+z);
            return true;
        }
        else if(zhiling.equals("xhw")) {
            /*if(!sender.isPlayer()){
                this.getServer().getLogger().warning("[小黑屋]请在游戏内执行此指令");
                return false;
            }*/
            if (liebiao.length == 1) {
                Player c;
                c = this.getServer().getPlayerExact(liebiao[0]);
                try {
                    /*
                    if (ynsave == true) {
                        int ss = c.getInventory().getSize();
                        Item[] playeritem = new Item[ss];
                        for (int i = 0; i < ss; i++) {
                            playeritem[i] = c.getInventory().getItem(i);
                            new top.bearcabbage.littledarkhouse.SaveData().saveitem(c.getName(), playeritem);
                        }
                    }
                    */
                    if(ynsave==true)
                        c.getInventory().clearAll();
                    c.setGamemode(2);
                    c.teleport(xhw);
                    c.setSpawn(new Position(x, y, z, this.getServer().getLevelByName(levelname)));
                    //new SaveData().edits(c, true);
                    c.sendMessage("[小黑屋]您已被关进小黑屋，请不要试图逃离，否则后果自负。");
                    sender.sendMessage("[小黑屋]" + c.getName() + "以被您关进小黑屋，记得放他出来哦。");
                    this.getServer().broadcastMessage("§5[小黑屋]" + c.getName() + "已被管理员关进小黑屋，请其他玩家检点自己的行为。");
                }
                catch(Exception e) {
                    sender.sendMessage("[小黑屋]§4发生错误！");
                    return false;
                }
                finally {
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
            /*if(!sender.isPlayer()){
                this.getServer().getLogger().warning("[小黑屋]请在游戏内执行此指令");
                return false;
            }*/
            if (liebiao.length == 1) {
                Player c;
                c = this.getServer().getPlayerExact(liebiao[0]);
                c.teleport(this.getServer().getDefaultLevel().getSafeSpawn().getLocation());
                //new SaveData().backitem(c);
                c.setSpawn(this.getServer().getDefaultLevel().getSafeSpawn());
                c.setGamemode(0);
                //new SaveData().edits(c, false);
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
