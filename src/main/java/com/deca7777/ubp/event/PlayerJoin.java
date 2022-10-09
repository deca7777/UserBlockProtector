package com.deca7777.ubp.event;
import com.deca7777.ubp.UBPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.deca7777.ubp.main.*;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UBPlayer ub = new UBPlayer();
        ub.player = p;
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IsUBPEnabled FROM PLAYER WHERE UUID =\"" + p.getUniqueId() + "\"");
            if (rs.isClosed()) {
                stmt.executeUpdate("INSERT INTO player VALUES(\"" + p.getName() + "\",\"" + p.getUniqueId() + "\",1)");
            }
            if(rs.getInt("IsUBPEnabled") == 0){
                ub.UBPEnabled = false;
            }
            else if(rs.getInt("IsUBPEnabled") == 1){
                ub.UBPEnabled = true;
            }
            playerList.add(ub);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // Changing the owner's name of the block when the user renamed him/her name.
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM PLAYER WHERE UUID =\"" + p.getUniqueId() + "\"");
            if(!rs.getString("username").equals(p.getName())){
                stmt.executeUpdate("UPDATE BLOCK SET USERNAME = \""+p.getName()+"\" WHERE UUID = \""+p.getUniqueId()+"\"");
                stmt.executeUpdate("UPDATE PLAYER SET USERNAME = \""+p.getName()+"\" WHERE UUID = \""+p.getUniqueId()+"\"");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(config.getBoolean("sendMessageOnJoin"))
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString(ub.UBPEnabled ? "enabled" : "disabled")));
    }
}