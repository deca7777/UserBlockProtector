package com.deca7777.ubp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.deca7777.ubp.main.SQL;

public class bp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        return SQL.ChangeEDStatus(player);
    }
}
