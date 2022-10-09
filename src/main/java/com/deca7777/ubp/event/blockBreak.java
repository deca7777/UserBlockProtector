package com.deca7777.ubp.event;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.deca7777.ubp.main.*;

public class blockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        try{
            Statement stmt = c.createStatement();
            //mysql ResultSet rs = stmt.executeQuery("SELECT UUID FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ()+" AND WORLD = CONVERT(CHAR(16),\"" + block.getWorld().getName() + "\")");
            ResultSet rs = stmt.executeQuery("SELECT UUID FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ()+" AND WORLD = \"" + block.getWorld().getName() + "\"");
            if(rs.isClosed()){

            }
            else if(!rs.getString("UUID").equals(player.getUniqueId().toString())){
                if(getUBPlayer(player).ShouldSendMessage(block)) {
                    //mysql rs = stmt.executeQuery("SELECT USERNAME FROM BLOCK WHERE X=" + block.getX() + " AND Y=" + block.getY() + " AND Z=" + block.getZ() + " AND WORLD = CONVERT(CHAR(16),\"" + block.getWorld().getName() + "\")");
                    ResultSet rs1 = stmt.executeQuery("SELECT USERNAME FROM BLOCK WHERE X=" + block.getX() + " AND Y=" + block.getY() + " AND Z=" + block.getZ() + " AND WORLD = \"" + block.getWorld().getName() + "\"");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("cannot-break").replaceAll("\\[BLOCKOWNER\\]",rs1.getString("username"))));
                }
                event.setCancelled(true);
            }
            else if(rs.getString("UUID").equals(player.getUniqueId().toString())){
                c.createStatement().executeUpdate("DELETE FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ());
            }
            else if(player.hasPermission("UserBlockProtector.bypassProtection")){
                if(getUBPlayer(player).ShouldSendMessage(block)) {
                    //mysql rs = stmt.executeQuery("SELECT USERNAME FROM BLOCK WHERE X=" + block.getX() + " AND Y=" + block.getY() + " AND Z=" + block.getZ() + " AND WORLD = CONVERT(CHAR(16),\"" + block.getWorld().getName() + "\")");
                    ResultSet rs1 = stmt.executeQuery("SELECT USERNAME FROM BLOCK WHERE X=" + block.getX() + " AND Y=" + block.getY() + " AND Z=" + block.getZ() + " AND WORLD = \"" + block.getWorld().getName() + "\"");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("bypass-message").replaceAll("\\[BLOCKOWNER\\]",rs1.getString("username"))));
                }
                c.createStatement().executeUpdate("DELETE FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ());
            }
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
