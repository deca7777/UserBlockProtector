package com.deca7777.ubp.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.sql.ResultSet;
import java.sql.Statement;
import static com.deca7777.ubp.main.c;
import static com.deca7777.ubp.main.getUBPlayer;

public class blockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        assert getUBPlayer(player) != null;
        if(getUBPlayer(player).UBPEnabled && player.hasPermission("UserBlockProtector.Protection")){
            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM BLOCK WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ()+" AND WORLD = \""+block.getWorld().getName()+"\"");
                if(rs.isClosed()){
                    stmt.executeUpdate("INSERT INTO BLOCK VALUES("+block.getX()+","+block.getY()+","+block.getZ()+",\""+player.getUniqueId()+"\",\""+player.getName()+"\",\""+block.getWorld().getName()+"\")");
                }
                else {
                    stmt.executeUpdate("UPDATE BLOCK SET (X, Y, Z, UUID, USERNAME, WORLD)=(" + block.getX() + ", " + block.getY() + ", " + block.getZ()+", \""+player.getUniqueId()+"\", \""+player.getName()+"\", \""+block.getWorld().getName()+"\") WHERE X="+block.getX()+" AND Y="+block.getY()+" AND Z="+block.getZ()+" AND WORLD = \""+block.getWorld().getName()+"\"");
                }
                stmt.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
