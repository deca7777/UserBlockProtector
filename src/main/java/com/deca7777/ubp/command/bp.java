package com.deca7777.ubp.command;

import com.deca7777.ubp.UBPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.deca7777.ubp.main.*;

public class bp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        UBPlayer ub = getUBPlayer(player);
        UBPlayer ubo = ub;
        assert ub != null;
        ub.UBPEnabled = !ub.UBPEnabled;
        playerList.set(playerList.indexOf(ubo), ub);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("prefix")+" "+messages.getString(ub.UBPEnabled ? "motd.enabled" : "motd.disabled")));
        return true;
    }
}
