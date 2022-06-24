package net.slownewt.core.utils;

import org.bukkit.Bukkit;

public class ServerVersion {
	public static String getServerVersion() {
		return removeLastChar(Bukkit.getVersion().substring(32));
	}

	public static String removeLastChar(String s) {
		// returns the string after removing the last character
		return s.substring(0, s.length() - 1);
	}
}
