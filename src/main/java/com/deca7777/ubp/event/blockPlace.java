package com.deca7777.ubp.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.deca7777.ubp.main.SQL;
import static com.deca7777.ubp.main.c;

public class blockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(SQL.CheckEDStatus(player)){
            try {
                Statement stmt = c.createStatement();
                stmt.executeUpdate("INSERT INTO BLOCK VALUES("+block.getX()+","+block.getY()+","+block.getZ()+",\""+player.getUniqueId()+"\",\""+player.getName()+"\",\""+player.getWorld().getName()+"\")");
                stmt.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
