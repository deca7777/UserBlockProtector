package com.deca7777.ubp.event;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import static com.deca7777.ubp.main.c;
import static com.deca7777.ubp.main.config;

public class EntityExplode implements Listener {
    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        if(config.getBoolean("protection.explosion")) {
            List<Block> blockList = e.blockList();
            Iterator it = blockList.iterator();
            while (it.hasNext()) {
                Block block = (Block) it.next();
                try {
                    Statement stmt1 = c.createStatement();
                    //mysql ResultSet rs = stmt1.executeQuery("SELECT UUID FROM BLOCK WHERE X=" + block.getX() + " AND Y=" + block.getY() + " AND Z=" + block.getZ() + " AND WORLD = CONVERT(CHAR(16),\"" + block.getWorld().getName() + "\")");
                    ResultSet rs = stmt1.executeQuery("SELECT UUID FROM BLOCK WHERE X=" + block.getX() + " AND Y=" + block.getY() + " AND Z=" + block.getZ() + " AND WORLD = \"" + block.getWorld().getName() + "\"");
                    if (!rs.isClosed()) {
                        it.remove();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
