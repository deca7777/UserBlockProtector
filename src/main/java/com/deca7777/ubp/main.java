package com.deca7777.ubp;

import com.deca7777.ubp.event.PlayerJoin;
import com.deca7777.ubp.event.blockPlace;
import com.deca7777.ubp.event.blockBreak;
import com.deca7777.ubp.sql.SQLPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

public class main extends JavaPlugin {
    public static boolean[] configs = new boolean[1];
    public static SQLPlayer SQL = new SQLPlayer();
    public static Connection c;
    @Override
    public void onEnable(){
        saveResource("config.yml",false);
        saveResource("ubp.db",false);
        FileConfiguration config = this.getConfig();
        configs[0] = config.getBoolean("sendMessageOnJoin");
        this.getCommand("bp").setExecutor(new com.deca7777.ubp.command.bp());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new blockBreak(), this);
        getServer().getPluginManager().registerEvents(new blockPlace(), this);
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:plugins/UserBlockProtector/ubp.db");
        }catch(Exception e){
            e.printStackTrace();
        }
        getLogger().log(Level.INFO, "UBP Enabled!");
    }
    @Override
    public void onDisable() {
        try {
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        getLogger().log(Level.INFO, "Disabling UBP...");
        getLogger().log(Level.INFO, "UBP Disabled!");
    }
}
