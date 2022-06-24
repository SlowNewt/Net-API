package net.slownewt.core;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import net.slownewt.core.commands.serverinfo.Admin;
import net.slownewt.core.commands.serverinfo.ServerList;
import net.slownewt.core.events.Events;
import net.slownewt.core.sql.SQL;
import net.slownewt.core.sql.SQL_Connection;
import net.slownewt.core.sql.messages.M_SQL_C;
import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.sql.server.S_SQL_C;
import net.slownewt.core.utils.Utils;

public class Main extends JavaPlugin {
	public static String color1 = "&a";
	public static String color2 = "&2";
	public static String color3 = "&c";
	public static String permission = "core.";
	public static boolean sqlError = true;
	public static boolean sqlLog = false;
	public static boolean startupLog = true;
	public static int version = 1;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		Events();
		if (SQL.enabled) {
			connectSQL();
		}
		if (!startupLog) {
			Utils.cLog("Importent", "&4Startup Log &cis currently Disabled!");
		}
		if (!sqlLog) {
			Utils.cLog("Importent", "&4SQL Log &cis currently Disabled!");
		}
		if (!sqlError) {
			Utils.cLog("Importent", "&4SQL Error &cis currently Disabled!");
		}
		Commands();
		String sql_enabled;
		if (SQL.enabled) {
			sql_enabled = Utils.Enabled;
		} else {
			sql_enabled = Utils.Disabled;
		}
		Utils.cLog("startupLog", "&7&m--------------------------------------------");
		Utils.cLog("startupLog", "&e&lLOAD SETTINGS");
		Utils.cLog("startupLog", "&7");
		Utils.cLog("startupLog", "&eServer Info");
		Utils.cLog("startupLog", "&7Server Name: &e" + S_SQL_C.getInfoS("NAME", S_SQL.serverid));
		Utils.cLog("startupLog", "&7ServerIP: &e" + S_SQL_C.getInfoS("IP", S_SQL.serverid) + ":"
				+ S_SQL_C.getInfoS("PORT", S_SQL.serverid));
		Utils.cLog("startupLog", "&7Server Status: &e" + S_SQL_C.getInfoS("STATUS", S_SQL.serverid));
		Utils.cLog("startupLog", "&7Server ID: &e" + S_SQL.serverid);
		Utils.cLog("startupLog", "&7");
		if (SQL.enabled) {
			Utils.cLog("startupLog", "&eSql");
			Utils.cLog("startupLog", "&7Status: &e" + sql_enabled);
			Utils.cLog("startupLog", "&7Host: &e" + SQL.host + ":" + SQL.port);
			Utils.cLog("startupLog", "&7Database: &e" + SQL.database);
			Utils.cLog("startupLog", "&7Username: &e" + SQL.username);
			Utils.cLog("startupLog", "&7Password: &e" + SQL.password);
		} else {
			Utils.cLog("startupLog", "&7");
			Utils.cLog("startupLog", "&7Sql: &e" + sql_enabled);
			Utils.cLog("startupLog", "&7");
		}
		Utils.cLog("startupLog", "&7&m--------------------------------------------");
	}

	@Override
	public void onDisable() {
		if (SQL.enabled) {
			disconnectSQL();
		}
	}

	public void Events() {
		getServer().getPluginManager().registerEvents(new Events(), this);
		if (SQL.enabled) {
			getServer().getPluginManager().registerEvents(new ServerList(), this);
		}
	}

	public void Commands() {
		new Admin();
	}

	public void connectSQL() {
		try {
			SQL_Connection.connect();
			S_SQL.createServer();
			S_SQL_C.upInfoB("STATUS", S_SQL.serverid, true);
			S_SQL_C.upInfoS("LASTSTART", S_SQL.serverid, Utils.getCurrentTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQL.updateSQL();
		M_SQL_C.checkSQL();
	}

	public void disconnectSQL() {
		S_SQL_C.upInfoB("STATUS", S_SQL.serverid, false);
		SQL_Connection.disconnect();
	}
}
