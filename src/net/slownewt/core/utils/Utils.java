package net.slownewt.core.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;
//import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.slownewt.core.Information;
import net.slownewt.core.Main;
import net.slownewt.core.sql.messages.MLog_SQL;
import net.slownewt.core.sql.messages.M_SQL;
import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.sql.server.S_SQL_C;

public class Utils extends Information {
	public static String Disabled = DISABLED;
	public static String Enabled = ENABLED;

	public static String chat(Player p, String message) {
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			String m = message;
			// m = PlaceholderAPI.setPlaceholders(p, m);
			return ChatColor.translateAlternateColorCodes('&',
					m.replaceAll("&a", Main.color1).replaceAll("&2", Main.color2));
		} else {
			return ChatColor.translateAlternateColorCodes('&',
					message.replaceAll("&a", Main.color1).replaceAll("&2", Main.color2));
		}
	}

	public static void sendMessageToServer(String s, String m) {
		M_SQL.sendMessage(s, "Command", m);
		MLog_SQL.sendMessage(s, "Command", m);
	}

	public static void cLog(String level, String m) {
		String prefix = Main.getPlugin(Main.class).getDescription().getPrefix();
		if (level.equals("sqlError")) {
			if (!Main.sqlError)
				return;
			Bukkit.getConsoleSender().sendMessage(chatothern("[" + prefix + "] &4&lERROR &f" + m));
			return;
		} else if (level.equals("startupLog")) {
			if (!Main.startupLog)
				return;
			Bukkit.getConsoleSender().sendMessage(chatothern("[" + prefix + "] &f&lLOG &f" + m));
			return;
		} else if (level.equals("sqlLog")) {
			if (!Main.sqlLog)
				return;
			Bukkit.getConsoleSender().sendMessage(chatothern("[" + prefix + "] &f&lLOG &f" + m));
			return;
		} else if (level.equals("Importent")) {
			Bukkit.getConsoleSender().sendMessage(chatothern("[" + prefix + "] &3&lIMP &f" + m));
			return;
		} else {
			return;
		}
	}

	public static String chatn(Player p, String message) {
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			String m = message;
			// m = PlaceholderAPI.setPlaceholders(p, m);
			return ChatColor.translateAlternateColorCodes('&', m);
		} else {
			return ChatColor.translateAlternateColorCodes('&', message);
		}
	}

	public static String Whistlist(String server) {
		String id = S_SQL.getId(server);
		if (S_SQL_C.getInfoB("STATUS", id)) {
			if (S_SQL_C.getInfoB("WHITELIST", id)) {
				return chatother(SERVER_WHITELISTED);
			} else {
				return chatother(SERVER_ONLINE);
			}
		} else {
			return chatother(SERVER_OFFLINE);
		}
	}

	public static String chatother(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static String chatothern(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static int globalcount() {
		int b = 0;
		S_SQL.getServers();
		String[] Servers = S_SQL.servers.toString().replace("[", "").replace("]", "").split(",");
		for (String key : Servers) {
			if (S_SQL_C.getInfoB("STATUS", S_SQL.getId(key)) == true)
				;
			int a = S_SQL_C.getInfoI("ONLINE", S_SQL.getId(key));
			b = b + a;
		}
		S_SQL.servers.clear();
		return b;
	}

	public static int getonlineservercount() {
		int b = 0;
		S_SQL.getServers();
		String[] Servers = S_SQL.servers.toString().replace("[", "").replace("]", "").split(",");
		for (String key : Servers) {
			if (S_SQL_C.getInfoB("STATUS", key) == true && S_SQL_C.getInfoB("WHITELIST", key) == false) {
				int a = S_SQL_C.getInfoI("ONLINE", key);
				b = b + a;
			}
		}
		return b;

	}

	public static void connect(Player p, String server) {
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.getPlugin(Main.class), "BungeeCord");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		p.sendPluginMessage(Main.getPlugin(Main.class), "BungeeCord", out.toByteArray());
		p.sendMessage(Utils.chat(p, "&aConnecting you to " + server + "!"));
	}

	public static int getonlineserveramount() {
		int b = 0;
		String[] Servers = S_SQL.servers.toString().replace("[", "").replace("]", "").split(",");
		for (String key : Servers) {
			if (S_SQL_C.getInfoB("STATUS", S_SQL.getId(key)) == true
					&& S_SQL_C.getInfoB("WHITELIST", S_SQL.getId(key)) == false) {
				b = b + 1;
			}
		}
		return b;
	}

	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy - HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}

	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static String formatValue(double value) {
		if (value == 0) {
			return 0 + "";
		} else {
			int power;
			String suffix = " kmbt";
			String formattedNumber = "";

			NumberFormat formatter = new DecimalFormat("#,###.#");
			power = (int) StrictMath.log10(value);
			value = value / (Math.pow(10, (power / 3) * 3));
			formattedNumber = formatter.format(value);
			formattedNumber = formattedNumber + suffix.charAt(power / 3);
			return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
		}
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public static String formattedTPS(String id) {
		double tps = S_SQL_C.getInfoD("TPS", id);
		if (tps >= 18) {
			if (tps == 20.1) {
				return "&a*20";
			} else {
				return "&a" + tps;
			}
		} else if (tps >= 15) {
			return "&e" + tps;
		} else if (tps >= 0.1) {
			return "&c" + tps;
		} else {
			return "&4&lERROR";
		}
	}
}