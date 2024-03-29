package com.enteente.plottools.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.enteente.plottools.Plottools;
import com.enteente.plottools.utils.MenuEntry;
import com.enteente.plottools.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import com.github.intellectualsites.plotsquared.plot.flag.BooleanFlag;
import com.github.intellectualsites.plotsquared.plot.flag.Flag;
import com.github.intellectualsites.plotsquared.plot.flag.FlagManager;
import com.github.intellectualsites.plotsquared.plot.object.Location;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotInventory;
import com.github.intellectualsites.plotsquared.plot.object.PlotItemStack;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;

public class FlagsCommand extends Command {

	private FlagsCommand instance;
	private Plot plot = null;
	private List<MenuEntry> mainMenu = null;

	public FlagsCommand() {
		super();
		instance = this;
	}

	public FlagsCommand(Player player) {
		super(player);
		instance = this;
	}

	private boolean setupMainMenu() {
		if (plot == null) {
			return false;
		}
		if (mainMenu == null) {
			mainMenu = new ArrayList<>();
		} else {
			mainMenu.clear();
		}

		FileConfiguration flagsconfig = Plottools.getInstance().getFlagsConfigs().get();
		List<?> items = flagsconfig.getList("flags");

		for (Object item : items) {
			MenuEntry e = MenuEntry.deserialize((Map<String, String>) item);

			if (player.hasPermission(e.permission)) {
				Flag flag = FlagManager.getFlag(e.id);
				if (flag == null || flag.isReserved()) {
					player.sendMessage("Unbekanntes Flag:" + e.id);
					continue;
				}

				switch (e.type) {
				case "Boolean":
					addBooleanMenuEntry(e, (BooleanFlag) flag, plot);
					break;
				default:
					player.sendMessage("Unsupported: " + e.type);
				}

			}

		}

		return true;
	}

	private void addBooleanMenuEntry(MenuEntry e, BooleanFlag flag, Plot plot) {
		if (flag.isSet(plot) && (flag).isTrue(plot)) {
			e.setValue("AN");
		} else {
			e.setValue("AUS");
		}
		mainMenu.add(e);
	}

	public boolean mainMenuClicked(int i) {
		if (i < 0)
			return false;

		MenuEntry e = this.mainMenu.get(i);
		if (e == null)
			return false;
		Flag<?> flag = FlagManager.getFlag(e.id);
		if (flag == null)
			return false;

		if (e.type.equals("Boolean")) {

			boolean result = false;
			if (flag.isSet(plot) && ((BooleanFlag) flag).isTrue(plot)) {
				result = plot.removeFlag(flag);
				if (e.id.equals("fly")) {
					Utils.fixFlight(plot);
				}

			} else {
				result = plot.setFlag(flag, flag.parseValue("true"));
			}
			if (result) {
				player.sendMessage(e.name + " wurde erfolgreich geändert");
			} else {
				player.sendMessage("leider ist ein Fehler aufgetreten");
			}
			return true;

		}
		player.sendMessage("Something broke:-(");
		return true;
	}

	public boolean openMainMenu() {
		String title = "Flags";
		PlotInventory inventory = new PlotInventory(PlotPlayer.wrap(player), 1, title) {
			@Override
			public boolean onClick(int i) {
				instance.mainMenuClicked(i);
				close();
				return true;
			}
		};

		int c = 0;
		for (MenuEntry item : mainMenu) {
			inventory.setItem(c, new PlotItemStack(item.material, 1, item.name, "Momentan:", item.getValue()));
			c++;
		}

		inventory.openInventory();
		return true;

	}

	@Override
	public boolean execute(Player player, String[] args) {
		PlotPlayer pp = PlotPlayer.wrap(player);
		Location location = Utils.getLocationFull(player);
		plot = location.getPlotAbs();
		UUID uuid = pp.getUUID();
		if (plot == null) {
			player.sendMessage("Du bist nicht auf einem Plot!");
			return false;
		}
		if (!player.hasPermission("plots.admin") && (!plot.hasOwner() || !plot.isOwner(uuid))) {
			player.sendMessage("Das ist nicht dein Plot!");
			return false;
		}
		setupMainMenu();
		return openMainMenu();
	}

}
