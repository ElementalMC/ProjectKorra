package com.projectkorra.projectkorra.firebending.passive;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.earthbending.Tremorsense;
import com.projectkorra.projectkorra.firebending.Illumination;

public class FirePassive {

	public static void handle(final Player player) {
		if (Commands.isToggledForAll && ConfigManager.defaultConfig.get().getBoolean("Properties.TogglePassivesWithAllBending")) {
			return;
		}
		final BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (bPlayer == null) {
			return;
		}

		// Illumination is only registered by name when it is enabled. If it is disabled it still
		// exists as a "fake" instance by class, so look it up by class and bail out if it's missing
		// or disabled. This avoids passing a null ability into canBend(...) which would throw an NPE.
		final CoreAbility illumination = CoreAbility.getAbility(Illumination.class);
		if (illumination == null || !illumination.isEnabled()) {
			return;
		}

		if (bPlayer.canBendPassive(illumination) && bPlayer.canUsePassive(illumination)) {
			if (!CoreAbility.hasAbility(player, Illumination.class) && (!CoreAbility.hasAbility(player, Tremorsense.class)
					|| (CoreAbility.getAbility(player, Tremorsense.class) != null && !CoreAbility.getAbility(player, Tremorsense.class).isGlowing()))
					&& bPlayer.canBendIgnoreBinds(illumination) && ConfigManager.defaultConfig.get().getBoolean("Abilities.Fire.Illumination.Passive")) {
				if (bPlayer.isIlluminating()) {
					new Illumination(player);
				}
			}
		}
	}
}
