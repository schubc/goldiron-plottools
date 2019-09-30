package com.enteente.plottools.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.enteente.plottools.Plottools;
import com.enteente.plottools.utils.Configs;
import com.enteente.plottools.utils.MenuEntry;
import com.enteente.plottools.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import com.github.intellectualsites.plotsquared.plot.database.DBFunc;
import com.github.intellectualsites.plotsquared.plot.object.Location;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotInventory;
import com.github.intellectualsites.plotsquared.plot.object.PlotItemStack;
import com.github.intellectualsites.plotsquared.plot.object.PlotManager;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.object.RunnableVal;
import com.github.intellectualsites.plotsquared.plot.util.ByteArrayUtilities;
import com.github.intellectualsites.plotsquared.plot.util.UUIDHandler;

import net.milkbowl.vault.economy.EconomyResponse;

public class FlagsCommand extends Command {

	private FlagsCommand instance;
	private Plot plot=null;
	private List<MenuEntry> mainMenu=null;
	
	
	public FlagsCommand() {
		super();
		instance=this;
	}
	
	public FlagsCommand(Player player) {
		super(player);
		instance=this;
	}
	
	private boolean setupMainMenu() {
		if(plot==null) {
			return false;
		}
		if(mainMenu==null) {
			mainMenu=new ArrayList<MenuEntry>();
		} else {
			mainMenu.clear();
		}
		
		FileConfiguration flagsconfig=Plottools.getInstance().getFlagsConfigs().get();
		List<?> items=flagsconfig.getList("flags");
		for(Object item:items) {
			MenuEntry e=MenuEntry.deserialize((Map<String,String>)item);
			
			if(player.hasPermission(e.permission)) {
				mainMenu.add(e);
			}
			
		}
		
		return true;
	}
	
	
	public boolean mainMenuClicked(int i) {
		System.out.print("Foomenu: " + i);
		//PlotPlayer pp=PlotPlayer.wrap(player);
		player.sendMessage("Main Menu Clicked: "+i);
		player.sendMessage(plot.toString());
		PlotPlayer pp=PlotPlayer.wrap(player);
		
		return true;
	}
	
	public boolean openMainMenu() {
        String title = "foo";
        PlotInventory inventory = new PlotInventory(PlotPlayer.wrap(player), 1, title) {
            @Override public boolean onClick(int i) {
            	setTitle("Foobar?");
            	instance.mainMenuClicked(i);
            	close();
            	return true;
            }
        };
        
        int c=0;
        for(MenuEntry item : mainMenu) {
        	inventory.setItem(c, new PlotItemStack(item.material, 1, item.name));
        	c++;
        }
        
        
        //inventory.setItem(1, new PlotItemStack("spawner", 42, "Bar"));
        inventory.openInventory();
		return true;
		
	}
	
	@Override
	public boolean execute(Player player, String[] args) {
		PlotPlayer pp=PlotPlayer.wrap(player);
        Location location = Utils.getLocationFull(player);
        plot = location.getPlotAbs();
        UUID uuid = pp.getUUID();
        if (plot == null) {
        	player.sendMessage("Du bist nicht auf einem Plot!");
        	return false;
        }
        if (!player.hasPermission("plots.admin")  && (!plot.hasOwner() || !plot.isOwner(uuid))) {
            player.sendMessage("Das ist nicht dein Plot!");
            return false;
        }
		setupMainMenu();
		return openMainMenu();
        // open foo menu
	}
	
 
}
