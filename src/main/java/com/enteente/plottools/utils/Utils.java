package com.enteente.plottools.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.enteente.plottools.Plottools;
import com.github.intellectualsites.plotsquared.plot.object.RunnableVal;
import com.github.intellectualsites.plotsquared.plot.database.DBFunc;
import com.github.intellectualsites.plotsquared.plot.object.Location;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.util.MathMan;
import com.github.intellectualsites.plotsquared.plot.util.PlotGameMode;
import com.github.intellectualsites.plotsquared.plot.util.UUIDHandler;

public final class Utils {
	public static int countPlots(Player player) {
        PlotPlayer pp=PlotPlayer.wrap(player);
        String world=Plottools.getInstance().getConfig().getString("world");
        Set<Plot> plots=pp.getPlots();
        List<Plot> plist = new ArrayList<Plot>(plots);
        int plotCount=0;
        for(Plot p:plist) {
        	if(p.getWorldName().equalsIgnoreCase(world)) {
        		plotCount++;
        	}
        }
		
		return plotCount;
	}
	public static double getPrice(int n) {

		double b=Plottools.getInstance().getConfigs().get().getDouble("baseprice");
		double e=Plottools.getInstance().getConfigs().get().getDouble("exponent");
		int r=Plottools.getInstance().getConfigs().get().getInt("round");
		
		double p=b*Math.pow(e, n);
		if(r!=0) {
			p=r*(double)Math.round(p/r);
		}
		
		return p;
	}
    public static void getPersistentMeta(UUID uuid, final String key,
            final RunnableVal<byte[]> result) {
            PlotPlayer player = UUIDHandler.getPlayer(uuid);
            if (player != null) {
                result.run(player.getPersistentMeta(key));
            } else {
                DBFunc.getPersistentMeta(uuid, new RunnableVal<Map<String, byte[]>>() {
                    @Override public void run(Map<String, byte[]> value) {
                        result.run(value.get(key));
                    }
            	});
            }
        }
    
    public static Location getLocationFull(final Entity entity) {
        final org.bukkit.Location location = entity.getLocation();
        return new Location(location.getWorld().getName(), MathMan.roundInt(location.getX()),
            MathMan.roundInt(location.getY()), MathMan.roundInt(location.getZ()), location.getYaw(),
            location.getPitch());
    }
    
    public static void fixFlight(Plot origin) {
        for (Plot plot : origin.getConnectedPlots()) {
            for (PlotPlayer pp : plot.getPlayersInPlot()) {
                boolean flight = pp.getFlight();
                PlotGameMode gamemode = pp.getGameMode();
                if(gamemode != PlotGameMode.CREATIVE && gamemode == PlotGameMode.SPECTATOR) {
                	pp.setFlight(false);
                }

            }
        }    	
    }
    
}
