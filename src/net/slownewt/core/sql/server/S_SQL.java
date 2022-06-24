package net.slownewt.core.sql.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.slownewt.core.Main;
import net.slownewt.core.sql.SQL;
import net.slownewt.core.sql.SQL_Connection;
import net.slownewt.core.utils.RandomString;
import net.slownewt.core.utils.ServerVersion;
import net.slownewt.core.utils.Utils;
import net.slownewt.core.utils.VersionConversion;

public class S_SQL {
	public static ArrayList<String> servers = new ArrayList<String>();
	Plugin plugin = Main.getPlugin(Main.class);
	public static String serverid = Main.getPlugin(Main.class).getConfig().getString("sql.serverid");

	public static String getId(String server) {
		if (!SQL.enabled) {
			Bukkit.getConsoleSender().sendMessage(Utils.chatother("&4&lSQL DISABLED"));
			return Utils.chatother("&4&lSQL DISABLED");
		}
		String b = "";
		try {
			PreparedStatement st = SQL_Connection.getConnection()
					.prepareStatement("SELECT * FROM " + SQL.serverData + " WHERE NAME=?");
			st.setString(1, server);
			ResultSet results = st.executeQuery();
			if (results.next()) {
				b = results.getString("ID");
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
		return b;
	}

	public static void getServers() {
		servers.clear();
		try {
			PreparedStatement st = (PreparedStatement) SQL_Connection.getConnection()
					.prepareStatement("SELECT ID FROM " + SQL.serverData + "");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (!servers.contains(rs.getString("ID"))) {
					servers.add(rs.getString("ID"));
				}
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
	}

	public static void createTable() {
		if (!SQL.enabled) {
			Bukkit.getConsoleSender().sendMessage(Utils.chatother("&4&lDISABLED"));
			return;
		}
		PreparedStatement st;
		try {
			Utils.cLog("sqlLog", "&aCreating table... If it dosen't esist alredy (Server)");
			st = SQL_Connection.getConnection()
					.prepareStatement("CREATE TABLE IF NOT EXISTS " + SQL.serverData + " (" + "NAME VARCHAR(20),"
							+ "ID VARCHAR(16)," + "STATUS BOOLEAN," + "ONLINE BIGINT," + "MAX BIGINT,"
							+ "WHITELIST BOOLEAN," + "IP VARCHAR(100)," + "PORT BIGINT," + "VERSION VARCHAR(100),"
							+ "MOTD VARCHAR(255)," + "LASTUPDATE VARCHAR(100)," + "TPS DOUBLE,"
							+ "LASTSTART VARCHAR(100)" + ", PRIMARY KEY (ID))");
			st.executeUpdate();
			Utils.cLog("sqlLog", "&aCreated table... If it dosen't esist alredy (Server)");
			S_SQL_C.upInfoS("LASTUPDATE", serverid, Utils.getCurrentTime());
			S_SQL_C.upInfoS("VERSION", serverid, ServerVersion.getServerVersion());
			updateServer(serverid);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void createServer() {
		if (SQL.enabled) {
			PreparedStatement st;
			try {
				if (!Main.getPlugin(Main.class).getConfig().contains("sql.serverid")) {
					st = SQL_Connection.getConnection()
							.prepareStatement("INSERT INTO " + SQL.serverData + " (NAME,ID,STATUS,ONLINE,"
									+ "MAX,WHITELIST,IP,PORT,VERSION,MOTD,LASTUPDATE,TPS," + "LASTSTART)"
									+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
					String id = RandomString.randomString(16);
					st.setString(1, VersionConversion.getServerName());
					st.setString(2, id);
					st.setBoolean(3, true);
					st.setInt(4, Bukkit.getServer().getOnlinePlayers().size());
					st.setInt(5, Bukkit.getServer().getMaxPlayers());
					st.setBoolean(6, Bukkit.hasWhitelist());
					st.setString(7, Bukkit.getIp().toString());
					st.setInt(8, Bukkit.getPort());
					st.setString(9, ServerVersion.getServerVersion());
					st.setString(10, Bukkit.getMotd());
					st.setString(11, Utils.getCurrentTime());
					st.setDouble(12, 0.0);
					st.setString(13, Utils.getCurrentTime());
					st.executeUpdate();
					Main.getPlugin(Main.class).getConfig().set("sql.serverid", id);
					serverid = id;
					Main.getPlugin(Main.class).saveConfig();
				}
			} catch (SQLException e) {
				Utils.cLog("sqlError", "&c" + e + " (Server)");
			}
		}
	}

	public static void updateServer(String id) {
		S_SQL_C.upInfoB("WHITELIST", id, Bukkit.hasWhitelist());
		S_SQL_C.upInfoI("ONLINE", id, Bukkit.getOnlinePlayers().size());
		S_SQL_C.upInfoI("MAX", id, Bukkit.getMaxPlayers());
		S_SQL_C.upInfoB("STATUS", id, true);
		S_SQL_C.upInfoS("NAME", id, VersionConversion.getServerName());
		S_SQL_C.upInfoS("IP", id, Bukkit.getIp());
		S_SQL_C.upInfoI("PORT", id, Bukkit.getPort());
		S_SQL_C.upInfoS("LASTUPDATE", id, Utils.getCurrentTime());
		S_SQL_C.upInfoD("TPS", id, VersionConversion.getTps());
	}

	public static boolean serverExistsI(String id) {
		if (SQL.enabled) {
			try {
				PreparedStatement st;
				st = SQL_Connection.getConnection().prepareStatement("SELECT * FROM " + SQL.serverData + " WHERE ID=?");
				st.setString(1, id);
				ResultSet results = st.executeQuery();
				if (results.next()) {
					return true;
				}
			} catch (SQLException e) {
				Utils.cLog("sqlError", "&c" + e + " (Server)");
				return false;
			}
			return false;
		}
		return false;
	}

	public static boolean serverExistsN(String name) {
		if (SQL.enabled) {
			try {
				PreparedStatement st;
				st = SQL_Connection.getConnection()
						.prepareStatement("SELECT * FROM " + SQL.serverData + " WHERE NAME=?");
				st.setString(1, name);
				ResultSet results = st.executeQuery();
				if (results.next()) {
					return true;
				}
			} catch (SQLException e) {
				Utils.cLog("sqlError", "&c" + e + " (Server)");
				return false;
			}
			return false;
		}
		return false;
	}
}