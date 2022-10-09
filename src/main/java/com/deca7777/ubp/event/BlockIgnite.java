package com.deca7777.ubp.event;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.deca7777.ubp.main.c;

public class BlockIgnite implements Listener {
    @EventHandler
    public void onBlockIgnite(BlockBurnEvent e){
        Block block = e.getBlock();
        try{
            Statement stmt1 = c.createStatement();
            ResultSet rs = stmt1.executeQuery("SELECT UUID FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ()+" AND WORLD = \""+block.getWorld().getName()+"\"");
            if(!rs.isClosed()){
                e.setCancelled(true);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
