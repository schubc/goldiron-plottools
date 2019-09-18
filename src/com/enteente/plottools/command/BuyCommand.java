package com.enteente.plottools.command;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.enteente.plottools.Plottools;
import com.enteente.plottools.utils.Utils;
import com.github.intellectualsites.plotsquared.plot.config.Configuration;
import com.github.intellectualsites.plotsquared.plot.database.DBFunc;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.object.RunnableVal;
import com.github.intellectualsites.plotsquared.plot.util.ByteArrayUtilities;
import com.github.intellectualsites.plotsquared.plot.util.UUIDHandler;

import net.milkbowl.vault.economy.EconomyResponse;

public class BuyCommand extends Command {

	public BuyCommand() {
		super();
	}
	public BuyCommand(Player player) {
		super(player);
	}
	
	@Override
	public boolean execute(Player player, String[] args) {
		PlotPlayer pp=PlotPlayer.wrap(player);
		
		//player.sendMessage("INFO");
		int plotCount=Utils.countPlots(player);
		//player.sendMessage(Integer.toString(plotCount));
		
		int maxPlots=pp.getAllowedPlots();
		//player.sendMessage(Integer.toString(maxPlots));
		final UUID uuid = pp.getUUID();
		Utils.getPersistentMeta(uuid, "grantedPlots", new RunnableVal<byte[]>() {
            @Override public void run(byte[] array) {
                int granted;
                if (array == null) {
                    granted = 0;
                } else {
                    granted = ByteArrayUtilities.bytesToInteger(array);
                }
                
                int plots=0; // the number of extra plots the player owns
                if(plotCount>maxPlots) {
                	plots=plotCount-maxPlots;
                }
                plots+=granted;
                final double p=Utils.getPrice(plots);
                
                //final double balance=Plottools.getEconomy().getBalance(player);
                

                EconomyResponse response=Plottools.getEconomy().withdrawPlayer(player, p);

                
                if(response.type==EconomyResponse.ResponseType.SUCCESS) {

                    int amount;
                    if (array == null) {
                        amount = 1;
                    } else {
                        amount = 1 + ByteArrayUtilities.bytesToInteger(array);
                    }
                    boolean replace = array != null;
                    String key = "grantedPlots";
                    byte[] rawData = ByteArrayUtilities.integerToBytes(amount);
                    PlotPlayer online = UUIDHandler.getPlayer(uuid);
                    if (online != null) {
                        online.setPersistentMeta(key, rawData);
                    } else {
                        DBFunc.addPersistentMeta(uuid, key, rawData, replace);
                    }
                    player.sendMessage("Du hast ein Plotticket gekauft!");
                	
                } else {
                	player.sendMessage("Du hast nicht genug Geld!");
                }
                
            }
		});
		return true;
	}
	
 
}
