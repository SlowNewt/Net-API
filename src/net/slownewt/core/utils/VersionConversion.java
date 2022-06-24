package net.slownewt.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import net.slownewt.core.Main;

public class VersionConversion {
	public static double tps1;

	public static double getTps() {
		if (Main.version == 2) {
			// 1.16
			// tps1 = 777;
			// for (double tps :
			// net.minecraft.server.v1_16_R3.MinecraftServer.getServer().recentTps) {
			// if (tps1 == 777) {
			// Double str1 = new Double(String.format("%.2f", tps));
			// if (str1 > 20) {
			// tps1 = 20.1;
			// } else {
			// tps1 = str1;
			// }
			// }
			// }
			// return (tps1);
		} else if (Main.version == 1) {
			// 1.8
			tps1 = 777;
			for (double tps : net.minecraft.server.v1_8_R3.MinecraftServer.getServer().recentTps) {
				if (tps1 == 777) {
					Double str1 = new Double(String.format("%.2f", tps));
					if (str1 > 20) {
						tps1 = 20.1;
					} else {
						tps1 = str1;
					}
				}
			}
			return (tps1);
		}
		return 0.0;
	}

	public static boolean getTitle(Inventory e, String n) {
		if (Main.version == 2) {
			// 1.16
			// if (e.getType().getDefaultTitle().equalsIgnoreCase(Utils.chatother(n))) {
			// return true;
			// } else {
			// return false;
			// }
		} else if (Main.version == 1) {
			// 1.8
			if (e.getTitle().equalsIgnoreCase(Utils.chatother(n))) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static String getServerName() {
		if (Main.version == 2) {
			return Bukkit.getServer().getName();
		} else if (Main.version == 1) {
			return Bukkit.getServerName();
		}
		return null;
	}

	public static Material getOldClay() {
		if (Main.version == 2) {
			// 1.16
			// return Material.getMaterial("STAINED_CLAY", true);
		} else if (Main.version == 1) {
			// 1.8
			return Material.getMaterial("STAINED_CLAY");
		}
		return null;
	}
}
