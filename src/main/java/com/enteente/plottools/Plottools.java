package com.enteente.plottools;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.enteente.plottools.handler.CommandHandler;
import com.enteente.plottools.utils.Configs;
import com.enteente.plottools.utils.MenuEntry;

import net.milkbowl.vault.economy.Economy;

import java.util.logging.Logger;

public class Plottools extends JavaPlugin {

	private static Plottools instance;
    private String packet = Bukkit.getServer().getClass().getPackage().getName();
    @SuppressWarnings("unused")
	private String version = packet.substring(packet.lastIndexOf('.') + 1);	
	
    private Configs config = null;
    private Configs flagsconfig = null;
    private Economy econ = null;

    private static final Logger log = Logger.getLogger("Minecraft");
    
	@Override
    public void onEnable() {
		instance = this;
    	
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }    	

    	getCommand("plottools").setExecutor(new CommandHandler());
    	
    	config=new Configs(this, "config.yml", true);
    	flagsconfig=new Configs(this, "flags.yml", true);
    	ConfigurationSerialization.registerClass(MenuEntry.class);

        
    }
    @Override
    public void onDisable() {
    	// Empty
    }
    
    public static Plottools getInstance() {
        return instance;
    }
    
    public Economy getEconomy() {
        return econ;
    }

    public Configs getConfigs() {
        return config;
    }   
    public Configs getFlagsConfigs() {
        return flagsconfig;
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
