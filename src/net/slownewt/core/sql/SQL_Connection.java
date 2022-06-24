package net.slownewt.core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.slownewt.core.sql.messages.MLog_SQL;
import net.slownewt.core.sql.messages.M_SQL;
import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.utils.Utils;

public class SQL_Connection {
	public static Connection connection;

	public static boolean isConnected() {
		return (connection != null);
	}

	public static void connect() throws SQLException {
		if (!isConnected()) {
			Utils.cLog("sqlLog", "&aConnecting to &2MySQL&a...");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + SQL.host + ":" + SQL.port + "/" + SQL.database + "?useSSL=false", SQL.username,
					SQL.password);
			Utils.cLog("sqlLog", "&aConnected to &2MySQL&a.");
			M_SQL.createTable();
			MLog_SQL.createTable();
			S_SQL.createTable();
		}
	}

	public static void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}