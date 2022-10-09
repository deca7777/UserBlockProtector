package com.deca7777.ubp;

import com.deca7777.ubp.event.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class main extends JavaPlugin {
    public static Connection c;
    public static FileConfiguration messages;
    public static FileConfiguration config;
    public static ArrayList<UBPlayer> playerList = new ArrayList<UBPlayer>();
    @Override
    public void onEnable(){
        // Saving Files
        if(!(new File("plugins/UserBlockProtector/config.yml")).exists())
            saveResource("config.yml",false);
        if(!(new File("plugins/UserBlockProtector/messages.yml")).exists())
            saveResource("messages.yml",false);
        if(!(new File("plugins/UserBlockProtector/ubp.db")).exists())
            saveResource("ubp.db",false);

        // Accessing Config Files
        config = new YamlConfiguration();
        messages = new YamlConfiguration();
        try{
            config.load(new File(getDataFolder(),"config.yml"));
            messages.load(new File(getDataFolder(),"messages.yml"));
        } catch(Exception e){
            e.printStackTrace();
        }

        // Registering Commands & Events
        this.getCommand("bp").setExecutor(new com.deca7777.ubp.command.bp());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new blockBreak(), this);
        getServer().getPluginManager().registerEvents(new blockPlace(), this);
        getServer().getPluginManager().registerEvents(new BlockIgnite(), this);
        getServer().getPluginManager().registerEvents(new EntityExplode(), this);

        // Making a connection to db file
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:plugins/UserBlockProtector/ubp.db");
        }catch(Exception e){
            e.printStackTrace();
        }

        // Enabled Message
        getLogger().log(Level.INFO, "UBP Enabled!");
    }
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Disabling UBP...");

        // Closing the connection
        try {
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        getLogger().log(Level.INFO, "UBP Disabled!");
    }
    public static UBPlayer getUBPlayer(Player p){
        for(UBPlayer ub : playerList){
            if(ub.player.equals(p)){
                return ub;
            }
        }
        return null;
    }
}
