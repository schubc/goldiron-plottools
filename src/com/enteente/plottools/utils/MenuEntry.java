package com.enteente.plottools.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class MenuEntry implements ConfigurationSerializable {

	public String id;
	public String name;
	public String flag;
	public String material;
	public String permission;
	public String type;

	@Override
	public Map<String, Object> serialize() {
		
		Map<String, Object> serialized=new HashMap<String,Object>();
		serialized.put("id", id);
		serialized.put("name", name);
		serialized.put("flag", flag);
		serialized.put("material", material);
		serialized.put("permission", permission);
		serialized.put("type", type);
		
		return serialized;
	}

	
	public static MenuEntry deserialize(final Map<String, String> map) {
		return new MenuEntry(
				map.get("id"),
				map.get("name"),
				map.get("flag"),
				map.get("material"),
				map.get("type"),
				map.get("permission")
				);
		
		//return null;
	}
	
	public MenuEntry(String id, String name, String flag, String material, String type, String permission) {
		this.id=id;
		this.name=name;		
		this.flag=flag;
		this.material=material;
		this.type=type;		
		this.permission=permission;		
	}
	
}
