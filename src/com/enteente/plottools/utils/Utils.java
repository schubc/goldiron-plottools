package com.enteente.plottools.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.enteente.plottools.Plottools;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;

public final class Utils {
	public static int countPlots(Player player) {
        PlotPlayer pp=PlotPlayer.wrap(player);
        String world=Plottools.getInstance().getConfig().getString("world");
        Set<Plot> plots=pp.getPlots();
        List<Plot> plist = new ArrayList<Plot>(plots);
        int plotCount=0;
        for(Plot p:plist) {
        	player.sendMessage(p.toString());
        	player.sendMessage(p.getWorldName());
        	player.sendMessage(world);
        	if(p.getWorldName().equalsIgnoreCase(world)) {
        		plotCount++;
        	}
        }
		
		return plotCount;
	}
	public static double getPrice(int n) {

		double b=Plottools.getInstance().getConfig().getDouble("baseprice");
		double e=Plottools.getInstance().getConfig().getDouble("exponent");
		int r=Plottools.getInstance().getConfig().getInt("round");
		
		double p=b*Math.pow(e, n);
		if(r!=0) {
			p=r*Math.round(p/r);
		}
		
		return p;
	}
}
