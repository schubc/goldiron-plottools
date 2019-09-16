package com.enteente.plottools.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.enteente.plottools.command.InfoCommand;

public class CommandHandler implements CommandExecutor {
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            
            if(args.length == 0) {
        		// TODO: open GUI
            	player.sendMessage("Benutzung: /pt <info|buy>");
        		return false;
        	}            
            if(args.length > 1) {
            	player.sendMessage("Benutzung: /pt <info|buy>");
        		return false;
            }
            String subcommand=args[0];
            int n=args.length-1;
            String[] subargs=new String[n];
            if(n>0) {
                System.arraycopy(args,1,subargs,0,n);
            }
            switch(subcommand) {
            case "info":
            	return new InfoCommand(player).execute();
            case "buy":
            	return true;
            }
        	player.sendMessage("Benutzung: /pt <info|buy>");
        	return false;
        }
		
		return false;
	}

}
