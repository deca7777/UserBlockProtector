package com.deca7777.ubp.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.deca7777.ubp.main.SQL;
import static com.deca7777.ubp.main.c;

public class blockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(SQL.CheckEDStatus(player)){
            try{
                Statement stmt1 = c.createStatement();
                ResultSet rs = stmt1.executeQuery("SELECT UUID FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ()+" AND WORLD = \""+player.getWorld().getName()+"\"");
                if(rs.isClosed()){

                }
                else if(!rs.getString("UUID").equals(player.getUniqueId().toString()) && !player.isOp()){
                    player.sendMessage("You can not break this block.");
                    event.setCancelled(true);
                }
                else if(!rs.getString("UUID").equals(player.getUniqueId().toString()) && player.isOp()){
                    player.sendMessage("You bypassed the protection.");
                    c.createStatement().executeUpdate("DELETE FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ());
                }
                else{
                    c.createStatement().executeUpdate("DELETE FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ());
                }
                stmt1.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
