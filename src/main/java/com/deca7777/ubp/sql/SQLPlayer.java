package com.deca7777.ubp.sql;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.deca7777.ubp.main.c;

public class SQLPlayer {
    public void NewPlayer(Player p){
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO player VALUES(\""+p.getName()+"\",\""+p.getUniqueId()+"\",1)");
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public boolean CheckEDStatus(Player p) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IsUBPEnabled FROM PLAYER WHERE UUID =\""+p.getUniqueId()+"\"");
            if(rs.isClosed()){
                stmt.executeUpdate("INSERT INTO player VALUES(\""+p.getName()+"\",\""+p.getUniqueId()+"\",1)");
                p.sendMessage("aaaa");
                return true;
            }
            return rs.getInt("IsUBPEnabled") == 1;
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }
    public boolean ChangeEDStatus(Player p){
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE player SET IsUBPEnabled = "+(CheckEDStatus(p)? 0 : 1)+" WHERE UUID = \""+p.getUniqueId()+"\"");
            p.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"UBP"+ChatColor.RESET+"] UBP Status changed to "+CheckEDStatus(p));
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
