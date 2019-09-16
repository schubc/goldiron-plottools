package com.enteente.plottools.command;

import org.bukkit.entity.Player;

import com.enteente.plottools.utils.Utils;
import com.github.intellectualsites.plotsquared.plot.config.Configuration;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;


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
		
		player.sendMessage("INFO");
		int plotCount=Utils.countPlots(player);
		player.sendMessage(Integer.toString(plotCount));
		
		int maxPlots=pp.getAllowedPlots();
		player.sendMessage(Integer.toString(maxPlots));
		
		
		return true;
	}

}
