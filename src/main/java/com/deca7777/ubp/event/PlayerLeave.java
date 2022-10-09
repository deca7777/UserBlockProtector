package com.deca7777.ubp.event;

import com.deca7777.ubp.UBPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import java.sql.Statement;

import static com.deca7777.ubp.main.*;

public class PlayerLeave implements Listener {
    @EventHandler
    public void PlayerLeaveEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();
        UBPlayer ub = getUBPlayer(p);
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE player SET IsUBPEnabled = "+(ub.UBPEnabled ? 1 : 0)+" WHERE UUID = \""+p.getUniqueId()+"\"");
            playerList.remove(ub);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
