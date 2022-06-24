package net.slownewt.core.sql.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.slownewt.core.Information;
import net.slownewt.core.sql.SQL;
import net.slownewt.core.sql.SQL_Connection;
import net.slownewt.core.utils.Utils;

public class S_SQL_C extends Information {

//
// String - Get and update data about Strings
//

	public static void upInfoS(String stat, String id, String update) {
		PreparedStatement st;
		try {
			if (S_SQL.serverExistsI(id)) {
				st = SQL_Connection.getConnection()
						.prepareStatement("UPDATE " + SQL.serverData + " SET " + stat + "=?  WHERE ID=?");
				st.setString(1, update);
				st.setString(2, id);
				st.executeUpdate();
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
	}

	public static String getInfoS(String stat, String id) {
		String update = null;
		try (PreparedStatement stmt = SQL_Connection.getConnection()
				.prepareStatement("SELECT " + stat + " FROM " + SQL.serverData + " WHERE ID= ?;")) {
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				update = results.getString(stat);
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
		return update;
	}

//
// Integer - Get and update data about Integers
//

	public static void upInfoI(String stat, String id, int update) {
		PreparedStatement st;
		try {
			if (S_SQL.serverExistsI(id)) {
				st = SQL_Connection.getConnection()
						.prepareStatement("UPDATE " + SQL.serverData + " SET " + stat + "=?  WHERE ID=?");
				st.setInt(1, update);
				st.setString(2, id);
				st.executeUpdate();
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
	}

	public static int getInfoI(String stat, String id) {
		int update = 0;
		try (PreparedStatement stmt = SQL_Connection.getConnection()
				.prepareStatement("SELECT " + stat + " FROM " + SQL.serverData + " WHERE ID= ?;")) {
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				update = results.getInt(stat);
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
		return update;
	}

//
// Double - Get and update data about Doubles
//

	public static void upInfoD(String stat, String id, Double update) {
		PreparedStatement st;
		try {
			if (S_SQL.serverExistsI(id)) {
				st = SQL_Connection.getConnection()
						.prepareStatement("UPDATE " + SQL.serverData + " SET " + stat + "=?  WHERE ID=?");
				st.setDouble(1, update);
				st.setString(2, id);
				st.executeUpdate();
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
	}

	public static Double getInfoD(String stat, String id) {
		Double update = 0.0;
		try (PreparedStatement stmt = SQL_Connection.getConnection()
				.prepareStatement("SELECT " + stat + " FROM " + SQL.serverData + " WHERE ID= ?;")) {
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				update = results.getDouble(stat);
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
		return update;
	}

//
// Boolean - Get and update data about Booleans
//

	public static void upInfoB(String stat, String id, boolean update) {
		PreparedStatement st;
		try {
			if (S_SQL.serverExistsI(id)) {
				st = SQL_Connection.getConnection()
						.prepareStatement("UPDATE " + SQL.serverData + " SET " + stat + "=?  WHERE ID=?");
				st.setBoolean(1, update);
				st.setString(2, id);
				st.executeUpdate();
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
	}

	public static boolean getInfoB(String stat, String id) {
		boolean update = false;
		try (PreparedStatement stmt = SQL_Connection.getConnection()
				.prepareStatement("SELECT " + stat + " FROM " + SQL.serverData + " WHERE ID= ?;")) {
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				update = results.getBoolean(stat);
			}
		} catch (SQLException e) {
			Utils.cLog("sqlError", "&c" + e + " (Server)");
		}
		return update;
	}
}
