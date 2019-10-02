package com.enteente.plottools.command;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.enteente.plottools.Plottools;
import com.enteente.plottools.utils.Utils;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.object.RunnableVal;
import com.github.intellectualsites.plotsquared.plot.util.ByteArrayUtilities;


public class InfoCommand extends Command {

	public InfoCommand() {
		super();
	}
	public InfoCommand(Player player) {
		super(player);
	}

	@Override
	public boolean execute(Player player, String[] args) {
		PlotPlayer pp=PlotPlayer.wrap(player);
		
		int plotCount=Utils.countPlots(player);
		
		int maxPlots=pp.getAllowedPlots();
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
                
                final double balance=Plottools.getInstance().getEconomy().getBalance(player);
                double b=balance;
                int n=0;
                while(Utils.getPrice(plots+n)<b) {
                	b-=Utils.getPrice(plots+n);
                	n++;
                }
                player.sendMessage(String.format("Du hast %d Grundst�cke und %d Grundst�ckstickets", plotCount, granted));
                player.sendMessage(String.format("Das n�chste Grundst�ck kostet %.02f Goldies", p));
                player.sendMessage(String.format("Du hast genug Geld f�r %d weitere Grundst�cke", n));
            }
        });
		
		return true;
	}

}
