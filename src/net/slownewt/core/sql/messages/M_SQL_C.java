package net.slownewt.core.sql.messages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.slownewt.core.Main;

public class M_SQL_C {
	public static void checkSQL() {
		Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin) Main.getPlugin(Main.class), new Runnable() {
			public synchronized void run() {
			}
		}, 0L, 10L);
	}
}