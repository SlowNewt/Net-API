package net.slownewt.core.sql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.slownewt.core.Main;
import net.slownewt.core.sql.server.S_SQL;

public class SQL {
	public static Boolean enabled = Main.getPlugin(Main.class).getConfig().getBoolean("sql.enabled");
	public static String host = Main.getPlugin(Main.class).getConfig().getString("sql.host");
	public static int port = Main.getPlugin(Main.class).getConfig().getInt("sql.port");
	public static String database = Main.getPlugin(Main.class).getConfig().getString("sql.database");
	public static String username = Main.getPlugin(Main.class).getConfig().getString("sql.username");
	public static String password = Main.getPlugin(Main.class).getConfig().getString("sql.password");
	public static String tableprefix = Main.getPlugin(Main.class).getConfig().getString("sql.tableprefix");
	public static String serverData = tableprefix + "server_data";
	public static String pluginMessaging = tableprefix + "messanger";
	public static String pluginMessagingLog = tableprefix + "messageLog";

	public static void updateSQL() {
		if (!Main.getPlugin(Main.class).getConfig().getString("sql.tableprefix").endsWith("_")) {
			tableprefix = tableprefix + "_";
		}
		Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin) Main.getPlugin(Main.class), new Runnable() {
			public synchronized void run() {
				S_SQL.updateServer(S_SQL.serverid);
			}
		}, 0L, 20L);
	}
}