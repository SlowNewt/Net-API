package net.slownewt.core.sql.messages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import net.slownewt.core.sql.SQL;
import net.slownewt.core.sql.SQL_Connection;
import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.utils.RandomString;
import net.slownewt.core.utils.Utils;

public class M_SQL {
	public static void createTable() {
		if (!SQL.enabled) {
			Bukkit.getConsoleSender().sendMessage(Utils.chatother("&4&lDISABLED"));
			return;
		}
		PreparedStatement st;
		try {
			Utils.cLog("sqlLog", "&aCreating table... If it dosen't esist alredy (Messaging)");
			st = SQL_Connection.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + SQL.pluginMessaging
					+ " ("
					+ "ID VARCHAR(5),TIME VARCHAR(100),FROMSERVER VARCHAR(16),TOSERVER VARCHAR(16),TYPE VARCHAR(30)"
					+ ",MESSAGE TEXT, PRIMARY KEY (ID))");
			st.executeUpdate();
			Utils.cLog("sqlLog", "&aCreated table... If it dosen't esist alredy (Messaging)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void sendMessage(String server, String type, String message) {
		if (SQL.enabled) {
			PreparedStatement st;
			try {
				st = SQL_Connection.getConnection().prepareStatement("INSERT INTO " + SQL.pluginMessaging
						+ " (ID,TIME,FROMSERVER,TOSERVER,TYPE,MESSAGE)" + " VALUES(?,?,?,?,?,?)");
				String id = RandomString.randomString(5);
				st.setString(1, id);
				st.setString(2, Utils.getCurrentTime());
				st.setString(3, S_SQL.serverid);
				st.setString(4, server);
				st.setString(5, type);
				st.setString(6, message);
				st.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean messageExists(String name) {
		if (SQL.enabled) {
			try {
				PreparedStatement st;
				st = SQL_Connection.getConnection()
						.prepareStatement("SELECT * FROM " + SQL.pluginMessaging + " WHERE ID=?");
				st.setString(1, name);
				ResultSet results = st.executeQuery();
				if (results.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}
}