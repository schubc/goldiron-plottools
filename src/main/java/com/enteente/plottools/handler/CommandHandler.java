package com.enteente.plottools.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.enteente.plottools.command.BuyCommand;
import com.enteente.plottools.command.FlagsCommand;
import com.enteente.plottools.command.InfoCommand;

public class CommandHandler implements CommandExecutor {
	private final String usage="Benutzung: /pt <info|buy|flags>";
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            
            if(args.length == 0) {
        		// TODO: open GUI
            	player.sendMessage(usage);
        		return false;
        	}            
            if(args.length > 1) {
            	player.sendMessage(usage);
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
            	return new BuyCommand(player).execute();
            case "flags":
            	return new FlagsCommand(player).execute();
            default:
            	player.sendMessage(usage);
            	return false;
            }
        }
		
		return false;
	}

}
