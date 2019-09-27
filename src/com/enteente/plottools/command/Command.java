package com.enteente.plottools.command;

import org.bukkit.entity.Player;


public abstract class Command {
	private Player player;
	public Command() {
		this.player=null;
	}
	public Command(Player player) {
		this.player=player;
	}
	public abstract boolean execute(Player player, String[] args);
	public boolean execute(String[] args) {
		if(this.player==null) {
			return false;
		}
		return this.execute(this.player, args);
	}
	public boolean execute() {
		if(this.player==null) {
			return false;
		}
		return this.execute(this.player, new String[0]);
	}
	public boolean execute(Player player) {
		return this.execute(player, new String[0]);
	}
}
