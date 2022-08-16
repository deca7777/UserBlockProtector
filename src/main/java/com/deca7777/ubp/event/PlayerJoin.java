package com.deca7777.ubp.event;
import com.deca7777.ubp.main;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import static com.deca7777.ubp.main.SQL;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(!p.hasPlayedBefore()){
            SQL.NewPlayer(p);
        }
        if(main.configs[0]){
            if(SQL.CheckEDStatus(p))
                event.getPlayer().sendMessage(ChatColor.GREEN +"Your BlockProtector has been enabled.");
            else if(!SQL.CheckEDStatus(p))
                event.getPlayer().sendMessage(ChatColor.RED+"Your BlockProtector has been disabled.");
        }
    }
}