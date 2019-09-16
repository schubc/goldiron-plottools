package com.enteente.plottools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.enteente.plottools.handler.CommandHandler;
import com.enteente.plottools.utils.Configs;

import net.milkbowl.vault.economy.Economy;

import java.util.logging.Logger;

public class Plottools extends JavaPlugin {

	private static Plottools instance;
    private String packet = Bukkit.getServer().getClass().getPackage().getName();
    private String version = packet.substring(packet.lastIndexOf('.') + 1);	
	
    private Configs config;
    private static Economy econ = null;

    private static final Logger log = Logger.getLogger("Minecraft");
    
	@Override
    public void onEnable() {
		instance = this;
    	getCommand("plottools").setExecutor(new CommandHandler());
    	
    	config=new Configs(this.getInstance(), "config.yml", true);
    	
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }    	
    	
    }
    @Override
    public void onDisable() {
    }
    
    public static Plottools getInstance() {
        return instance;
    }
    
    public static Economy getEconomy() {
        return econ;
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
       
}
