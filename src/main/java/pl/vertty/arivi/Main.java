package pl.vertty.arivi;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockID;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.ItemAxeWood;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.TextFormat;
import lombok.SneakyThrows;
import pl.vertty.arivi.entity.*;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.mysql.modes.StoreMySQL;
import pl.vertty.arivi.objects.Combat;
import pl.vertty.arivi.managers.RoleManager;
import pl.vertty.arivi.managers.*;
import pl.vertty.arivi.mysql.Store;
import pl.vertty.arivi.managers.CombatManager;
import cn.nukkit.Player;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.objects.guild.Guild;
import cn.nukkit.inventory.Inventory;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.utils.SeralizerUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import cn.nukkit.level.Level;
import pl.vertty.arivi.task.SprawdzMessageTimer;
import cn.nukkit.Server;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;

import pl.vertty.arivi.wings.WingsManager;
import pl.vertty.arivi.tnt.EntityManager;
import pl.vertty.arivi.loader.MotdLoader;
import pl.vertty.arivi.loader.CraftingLoader;
import pl.vertty.arivi.loader.LevelLoad;
import pl.vertty.arivi.loader.TaskLoader;
import pl.vertty.arivi.loader.EventsLoader;
import pl.vertty.arivi.loader.CommandsLoader;
import pl.vertty.arivi.loader.DropsLoader;
import pl.vertty.arivi.wings.mysql.UserWings;
import pl.vertty.arivi.managers.guild.GuildManager;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.loader.DatabaseLoader;
import pl.vertty.arivi.loader.TimeLoader;
import pl.vertty.arivi.loader.ConfigLoader;
import pl.vertty.arivi.inventory.trade.TradeMenuHandler;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import cn.nukkit.plugin.PluginBase;
import pl.vertty.arivi.worldedit.*;

import javax.security.auth.login.LoginException;

public class Main extends PluginBase
{
    public static Main plugin;
    public static long startUpTime;
    public static HashMap<String, PlayerData> data = new HashMap<>();
    public static WEManager commandManager = new WEManager();


    @SneakyThrows
    public void onEnable() {
        Main.startUpTime = System.currentTimeMillis();
        Main.plugin = this;
        this.getServer().setAutoSave(true);
        this.getServer().setMaxPlayers(110);
        this.getServer().setPropertyBoolean("achievements", false);
        this.getServer().setPropertyBoolean("announce-player-achievements", false);
        final InventoryMenuHandler handler = new InventoryMenuHandler();
        final TradeMenuHandler handler2 = new TradeMenuHandler();
        this.getServer().getPluginManager().registerEvents(handler, this);
        this.getServer().getPluginManager().registerEvents(handler2, this);
        ConfigLoader.onConfigLoad();
        TimeLoader.onTimeLoader();
        DatabaseLoader.onDatabaseLoad();
        WarpManager.loadWarp();
        BanManager.loadBans();
        UserManager.loadUsers();
        GuildManager.loadGuilds();
        UserWings.loadUsers();
        DropsLoader.onDropsLoad();
        CommandsLoader.onCommandsLoad();
        TaskLoader.onTaskLoad();
        LevelLoad.onLevel();
        CraftingLoader.onCraftingLoad();
        MotdLoader.onLoad();
        EntityManager.init();
        ItemShopManager.loadUsers();
        RoleManager.loadUsers();
        registerEntity();
        BlockFactory.registerBlock(12, FixedSand.class);
        try {
            WingsManager.init(this);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        final Level world = Server.getInstance().getDefaultLevel();
        world.setThundering(false);
        world.setTime(2000);
        this.onCombat();
        new SprawdzMessageTimer(this);
        BackupManager.loadBackups();
        Iterator<Player> iterator = Server.getInstance().getOnlinePlayers().values().iterator();
        if (iterator.hasNext()) {
            Player p = iterator.next();
            Combat c = CombatManager.getCombat(p);
            if (c == null) {
                CombatManager.createCombat(p);
            }
            return;
        }
        MainConstants.set();
        EventsLoader.onEventsLoad();
    }
    
    public void onDisable() {
        for (final Guild user2 : GuildManager.getGuilds().values()) {
            user2.setSkrzynka1(SeralizerUtil.serializeInventory(user2.getSkrzynka()));
        }
        DropsLoader.onDataSaved();
        for (Player p : Server.getInstance().getOnlinePlayers().values()){
            CombatManager.removeCombat(p);
        }
        plugin.getServer().doAutoSave();
        for(FakeWater water : WaterManager.getWaters().values()) {
            water.getLevel().setBlock(water, Block.get(BlockID.AIR));
        }
        DatabaseLoader.store.disconnect();
    }
    
    public static Store getStore() {
        return DatabaseLoader.store;
    }
    
    public static Main getPlugin() {
        return Main.plugin;
    }
    
    public void onCombat() {
        final Iterator<Player> iterator = Server.getInstance().getOnlinePlayers().values().iterator();
        if (iterator.hasNext()) {
            final Player p = iterator.next();
            final Combat c = CombatManager.getCombat(p);
            if (c == null) {
                CombatManager.createCombat(p);
            }
        }
    }


    public static void onRestart(){
        for (final Guild user2 : GuildManager.getGuilds().values()) {
            user2.setSkrzynka1(SeralizerUtil.serializeInventory((Inventory) user2.getSkrzynka()));
        }
        DropsLoader.onDataSaved();
        for (Player p : Server.getInstance().getOnlinePlayers().values()){
            CombatManager.removeCombat(p);
        }
        plugin.getServer().doAutoSave();
        for(FakeWater water : WaterManager.getWaters().values()) {
            water.getLevel().setBlock(water, Block.get(BlockID.AIR));
        }
        plugin.getPlugin().getServer().shutdown();
    }

    private void registerEntity() {
        long startTime = System.nanoTime();
        Entity.registerEntity(Snowball.class.getSimpleName(), Snowball.class);
        Entity.registerEntity(EnderPearl.class.getSimpleName(), EnderPearl.class);
        Entity.registerEntity(Arrow.class.getSimpleName(), Arrow.class);
        long endTime = System.nanoTime();
        getLogger().info("Registered entities in {S}ms".replace("{S}", String.valueOf((endTime - startTime) / 1000000L)));
    }

    public void mysqlSetup() throws SQLException, ClassNotFoundException {
        Main.getStore().update("CREATE TABLE IF NOT EXISTS `q1zz_receivedrewards` (`UniqueID` VARCHAR(96) PRIMARY KEY, `DiscordAccountID` VARCHAR(96));");
        getLogger().log(LogLevel.INFO, "Successful connected to MySQl datebase!");
    }

    public void log(String message) {
        getLogger().log(LogLevel.INFO, message);
    }





















    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Komenda tylko w grze");
            return false;
        }

        Player p = (Player) sender;
        User u = UserManager.getUser(p);
        if (!u.can(GroupType.ADMIN)) {
            p.sendMessage(ChatUtil.fixColor("&cWymagana ranga do uzycia tej komendy to &7ADMIN"));
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("/")) {
            PlayerData data = getPlayerData(p);
            Selection sel = data.getSelection();

            switch (label.toLowerCase()) {
                case "/":
                case "/help":
                    if (args.length == 0) {
                        p.sendMessage(getHelp(0));
                    } else if (args.length == 1) {
                        p.sendMessage(getHelp(Integer.parseInt(args[0])));
                    } else {
                        p.sendMessage(TextFormat.GRAY + "uzyj: " + TextFormat.YELLOW + "//help [1-3]");
                        return false;
                    }
                    return true;
                case "/wand":
                    p.getInventory().setItemInHand(new ItemAxeWood().setCustomName(ChatUtil.fixColor("&r&eWORLDEDIT BETA &r&f&lK U R W A")));
                    p.getInventory().sendHeldItem(p);

                    p.sendMessage(TextFormat.YELLOW + "Zniszcz blok aby zaznaczyc: " + TextFormat.GRAY + "pos "  + "#1" + TextFormat.GRAY + "; " + TextFormat.YELLOW + "Tapnij w blok aby zaznaczyc: " + TextFormat.GRAY + "pos "  + "#2");
                    return true;
                case "/sel":
                case "/;":
                case "/desel":
                case "/deselect":
                    data.getSelection().pos1 = null;
                    data.getSelection().pos2 = null;

                    p.sendMessage(TextFormat.GREEN + "Wyczyszczono zaznaczone pole.");
                    return true;
                case "/pos1":
                case "/1":
                    data.getSelection().pos1 = p.getPosition().clone();
                    p.sendMessage(TextFormat.GREEN + "Pozycja pierwsza zaznaczona na (" +  p.getFloorX() + ".0"  + ", " +  p.getFloorY() + ".0"  + ", " +  p.getFloorZ() + ".0"  + ").");
                    //p.sendMessage(TextFormat.GREEN+"Selected the first position at "+TextFormat.BLUE+p.getFloorX()+TextFormat.GREEN+", "+TextFormat.BLUE+p.getFloorY()+TextFormat.GREEN+", "+TextFormat.BLUE+p.getFloorZ()+TextFormat.GREEN);
                    return true;
                case "/pos2":
                case "/2":
                    data.getSelection().pos1 = p.getPosition().clone();
                    p.sendMessage(TextFormat.GREEN + "Pozycja druga zaznaczona na (" +  p.getFloorX() + ".0"  + ", " +  p.getFloorY() + ".0"  + ", " +  p.getFloorZ() + ".0"  + ").");
                    return true;
                case "/undo":
                    return true;
                case "/redo":
                    return true;
                case "/paste":
                    BlocksCopy copy = data.copiedBlocks;

                    if (copy == null) {
                        p.sendMessage(TextFormat.RED + "Uzyj //copy znim zaczniesz!");
                        return false;
                    }

                    p.sendMessage(commandManager.paste(p, copy) + " blokow zostalo wklejone.");
                    data.copiedBlocks = null;
                    return true;
            }

            Block b;

            switch (label.toLowerCase()) {
                case "/cyl":
                    if (args.length != 2) {
                        p.sendMessage(TextFormat.YELLOW + "Uzyj //cyl <blok> <zasieg>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);
                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }

                    p.sendMessage(commandManager.cyl(p.getPosition(), Integer.parseInt(args[1]), b) + " blokow, zostalo zmienione");
                    return true;
                case "/hcyl":
                    if (args.length != 2) {
                        p.sendMessage(TextFormat.YELLOW + "Use //hcyl <blok> <zasieg>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);
                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }

                    p.sendMessage(commandManager.hcyl(p.getPosition(), Integer.valueOf(args[1]), b)  + " blokow, zostalo zmienione.");
                    return true;
                case "/sphere":
                    if (args.length != 2) {
                        p.sendMessage(TextFormat.YELLOW + "Use //sphere <blok> <zasieg>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);
                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }

                    p.sendMessage( commandManager.sphere(p.getPosition(), Integer.parseInt(args[1]), b) + " blokow, zostalo zmienione.");
                    return true;
                case "/hsphere":
                    if (args.length != 2) {
                        p.sendMessage(TextFormat.YELLOW + "Use //hsphere <blok> <zasieg>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);
                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }

                    p.sendMessage( commandManager.hsphere(p.getPosition(), Integer.parseInt(args[1]), b)  + " blokow, zostalo zmienione.");
                    return true;
            }

            if (!isPosSet(p)) {
                p.sendMessage(TextFormat.RED + "Musisz najpierw wybrac obie pozycje!");
                return false;
            }

            if (data.getSelection().pos1.level.getId() != data.getSelection().pos2.level.getId()) {
                p.sendMessage(TextFormat.RED + "Obie pozycje muszą być na tym samym swiecie!");
                return false;
            }

            switch (label.toLowerCase()) {
                case "/cut":
                    p.sendMessage( commandManager.set(sel.pos1, sel.pos2, new BlockAir())  + " blokow, zostalo zmienione.");
                    return true;
                case "/copy":
                    data.copiedBlocks = new BlocksCopy();

                    p.sendMessage( commandManager.copy(sel.pos1, sel.pos2, p, data.copiedBlocks)  + " blokow, zostalo skopiowane.");
                    return true;
            }

            switch (label.toLowerCase()) {
                case "/set":
                    if (args.length != 1) {
                        sender.sendMessage(TextFormat.YELLOW + "Uzyj //set <blok>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);

                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }
                    p.sendMessage( commandManager.set(sel.pos1, sel.pos2, b)  + " blokow, zostalo zmienione.");
                    return true;
                case "/walls":
                case "/wall":
                    if (args.length != 1) {
                        sender.sendMessage(TextFormat.YELLOW + "Use //walls <blok>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);

                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }
                    p.sendMessage( commandManager.walls(sel.pos1, sel.pos2, b)  + " blokow, zostalo zmienione.");
                    return true;
                case "/replace":
                    if (args.length != 2) {
                        sender.sendMessage(TextFormat.YELLOW + "Use //replace <blok> <zasieg>");
                        return false;
                    }

                    b = Utils.fromString(args[0]);
                    Block b2 = Utils.fromString(args[1]);

                    if (b == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[0] + "' nie istnieje");
                        return false;
                    }

                    if (b2 == null) {
                        sender.sendMessage(TextFormat.RED + "Blok '" + args[1] + "' nie istnieje");
                        return false;
                    }

                    p.sendMessage( commandManager.replace(sel.pos1, sel.pos2, b, b2)  + " blokow, zostalo zmienione.");
                    return true;
            }
        }

        return true;
    }

    public boolean isPosSet(Player p) {
        PlayerData data = getPlayerData(p);

        return data.getSelection().pos1 != null && data.getSelection().pos2 != null;
    }

    public static boolean isSelector(Player p) {
        User u = UserManager.getUser(p);
        return u.can(GroupType.ADMIN);
    }

    public static PlayerData getPlayerData(Player p) {
        PlayerData data = Main.data.get(p.getName().toLowerCase());

        if (data == null) {
            data = new PlayerData(p);
            Main.data.put(p.getName().toLowerCase(), data);
        }

        return data;
    }

    private String getHelp(int page) {
        String msg = "";

        switch (page) {
            case 0:
            case 1:
                msg += TextFormat.GRAY + "Pomoc "  + "1/3:";
                msg += "\n" + TextFormat.YELLOW + "   //pos1";
                msg += "\n" + TextFormat.YELLOW + "   //pos2";
                msg += "\n" + TextFormat.YELLOW + "   //set <blok>";
                msg += "\n" + TextFormat.YELLOW + "   //walls <blok>";
                msg += "\n" + TextFormat.YELLOW + "   //replace <blok> <nowy blok>";
                break;
            case 2:
                msg += TextFormat.GRAY + "Pomoc "  + "2/3:";
                msg += "\n" + TextFormat.YELLOW + "   //cyl <blok> <zasieg>";
                msg += "\n" + TextFormat.YELLOW + "   //hcyl <blok> <zasieg>";
                msg += "\n" + TextFormat.YELLOW + "   //sphere <blok> <zasieg>";
                msg += "\n" + TextFormat.YELLOW + "   //hsphere <blok> <zasieg>";
                msg += "\n" + TextFormat.YELLOW + "   //cut";
                break;
            case 3:
                msg += TextFormat.GRAY + "Pomoc "  + "3/3:";
                msg += "\n" + TextFormat.YELLOW + "   //copy";
                msg += "\n" + TextFormat.YELLOW + "   //paste";
                msg += "\n" + TextFormat.YELLOW + "   //wand";
                break;
        }

        return msg;
    }






}
