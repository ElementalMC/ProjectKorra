package com.projectkorra.projectkorra.hooks;

import org.bukkit.Bukkit;

import com.projectkorra.projectkorra.ProjectKorra;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class WorldGuardFlag {
	public static void registerBendingWorldGuardFlag() {
		final FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

		try {
			if (registry.get("bending") != null) {
				Bukkit.getLogger().warning("[ProjectKorra] Bending flag already exists");  //Can't use the PK logger as this is called
				return;																			//before the plugin is initialized
			}
			registry.register(new StateFlag("bending", false));
		} catch (final Exception e) {
			Bukkit.getLogger().severe("[ProjectKorra] Unable to register bending WorldGuard flag: " + e);
			ProjectKorra.log.log(java.util.logging.Level.WARNING, e.getMessage(), e);
		}
	}
}
