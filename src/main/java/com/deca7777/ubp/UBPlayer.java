package com.deca7777.ubp;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.time.LocalTime;

public class UBPlayer {
    public Player player;
    private Block BrokenBlock;
    private int BrokenTime=0;
    public boolean UBPEnabled=true;
    public boolean ShouldSendMessage(Block b){
        if(BrokenBlock == null || BrokenTime == 0){
            this.BrokenBlock = b;
            this.BrokenTime = LocalTime.now().getSecond();
            return true;
        }
        if(main.config.getDouble("cannot-break-message-interval") <= LocalTime.now().getSecond() - BrokenTime && !BrokenBlock.equals(b)) {
            this.BrokenBlock = b;
            this.BrokenTime = LocalTime.now().getSecond();
            return true;
        }
        else{
            return false;
        }
    }
}
