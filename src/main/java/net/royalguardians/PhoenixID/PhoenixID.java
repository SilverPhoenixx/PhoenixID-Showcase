package net.royalguardians.PhoenixID;

import net.royalguardians.PhoenixID.command.IdCommand;
import net.royalguardians.PhoenixID.command.LanguageCommand;
import net.royalguardians.PhoenixID.database.Database;
import net.royalguardians.PhoenixID.listener.JoinQuitListener;
import net.royalguardians.PhoenixID.user.UserID;
import org.bukkit.Bukkit;
import org.bukkit.block.Structure;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PhoenixID extends JavaPlugin {

    public static PhoenixID instance;
    public static Database db;

    public static List<UUID> lang = new ArrayList<>();
    public static Map<UUID, UserID> ids = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        db = new Database("localhost", 3306, "Database", "User", "Password");
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        getCommand("Language").setExecutor(new LanguageCommand());
        getCommand("Language").setTabCompleter(new LanguageCommand());
        getCommand("Id").setExecutor(new IdCommand());
        
    }

    public static PhoenixID getInstance() {
        return instance;
    }
}
