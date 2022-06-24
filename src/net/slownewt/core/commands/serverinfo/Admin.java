package net.slownewt.core.commands.serverinfo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.slownewt.core.Information;
import net.slownewt.core.Main;
import net.slownewt.core.sql.SQL;
import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.sql.server.S_SQL_C;
import net.slownewt.core.utils.ServerVersion;
import net.slownewt.core.utils.Utils;
import net.slownewt.core.utils.VersionConversion;

public class Admin extends Information implements CommandExecutor {

	public Admin() {
		Main.getPlugin(Main.class).getCommand("admin").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission(Main.permission + "admin")) {
			sender.sendMessage(Utils.chatothern(NO_PERMISSION).replace("%perm", Main.permission + "admin"));
			return true;
		}
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(Utils.chatothern(CONSOLE_SENDER));
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("utils.admin")) {
			if (args.length != 0) {
				if (args[0].toString().equalsIgnoreCase("server")
						|| args[0].toString().equalsIgnoreCase("serverinfo")) {
					if (args.length == 2) {
						if (S_SQL.serverExistsN(args[1])) {
							String server = args[1];
							String id = S_SQL.getId(server);
							int onlinePlayers = S_SQL_C.getInfoI("ONLINE", id);
							int maxPlayers = S_SQL_C.getInfoI("MAX", id);
							String ip = S_SQL_C.getInfoS("IP", id);
							int port = S_SQL_C.getInfoI("PORT", id);
							String motd = S_SQL_C.getInfoS("MOTD", id);
							String lastStart = S_SQL_C.getInfoS("LASTSTART", id);
							String BaseVersion = S_SQL_C.getInfoS("VERSION", id);
							p.sendMessage(Utils.chatn(p, "&8&m--------------------"));
							p.sendMessage(Utils.chatn(p, "&7Server Name: &f" + server));
							p.sendMessage(Utils.chatn(p, "&7Status: &f" + Utils.Whistlist(server)));
							p.sendMessage(Utils.chatn(p, "&7Players: &f" + onlinePlayers + "/" + maxPlayers));
							p.sendMessage(Utils.chatn(p, "&7Server ID: &f" + id));
							p.sendMessage(Utils.chatn(p, "&7TPS: &a" + Utils.formattedTPS(id)));
							p.sendMessage(Utils.chatn(p, "&7Motd: &f" + motd));
							p.sendMessage(Utils.chatn(p, "&7IP: &f" + ip + ":" + port));
							p.sendMessage(Utils.chatn(p, "&7Last Start: &f" + lastStart));
							p.sendMessage(Utils.chatn(p, "&7Base Version: &f" + BaseVersion));
							p.sendMessage(Utils.chatn(p, "&8&m--------------------"));
						} else {
							p.sendMessage(Utils.chat(p, UNKNOWN_SERVER.replace("%s", args[1])));

						}
					} else {
						p.sendMessage(Utils.chat(p, INVALID_USAGE.replace("%u", "/admin serverinfo <server>")));
					}
				} else if (args[0].toString().equalsIgnoreCase("list")) {
					ServerList.openAdminManu(p);
				} else if (args[0].toString().equalsIgnoreCase("forceupdate")) {
					S_SQL.updateServer(S_SQL.serverid);
					p.sendMessage(
							Utils.chat(p, "&aUpdated all SQL data for &2" + VersionConversion.getServerName() + "&a!"));
				} else if (args[0].toString().equalsIgnoreCase("sql")) {
					String enabled = "";
					if (SQL.enabled)
						;
					if (SQL.enabled) {
						enabled = ENABLED;
					} else {
						enabled = DISABLED;
					}
					p.sendMessage(Utils.chatn(p, "&8&m--------------------"));
					p.sendMessage(Utils.chatn(p, "&7Status: &f" + enabled));
					p.sendMessage(Utils.chatn(p, "&7TablePrefix: &f" + SQL.tableprefix));
					p.sendMessage(Utils.chatn(p, "&7Host: &f" + SQL.host + ":" + SQL.port));
					p.sendMessage(Utils.chatn(p, "&7Database: &f" + SQL.database));
					p.sendMessage(Utils.chatn(p, "&7Username: &f" + SQL.username));
					p.sendMessage(Utils.chatn(p, "&7Password: &f" + SQL.password));
					p.sendMessage(Utils.chatn(p, "&8&m--------------------"));
				} else if (args[0].toString().equalsIgnoreCase("command")
						|| args[0].toString().equalsIgnoreCase("cmd")) {
					if (S_SQL.serverExistsN(args[1]) || args[1].equalsIgnoreCase("all")) {
						String server = args[1];
						StringBuilder message = new StringBuilder();
						String id = "";
						if (args.length >= 3) {
							for (int i = 2; i < args.length; i++)
								message.append(String.valueOf(String.valueOf(String.valueOf(args[i]))) + " ");
							if (server.equalsIgnoreCase("all")) {
								id = "ALL";
								p.sendMessage(Utils.chatn(p,
										"&aSent the command \"&2/" + message + "&r&2\"&a to all the all servers!"));
							} else {
								id = S_SQL.getId(server);
								p.sendMessage(Utils.chatn(p,
										"&aSent the command \"&2/" + message + "&r&2\"&a to " + server + " server!"));
							}
							Utils.sendMessageToServer(id, message.toString());
						} else {
							p.sendMessage(Utils.chat(p, INVALID_USAGE.replace("%u", "/admin cmd <server/all> <cmd>")));
						}
					} else {
						p.sendMessage(Utils.chat(p, UNKNOWN_SERVER.replace("%s", args[1])));
					}
				} else if (args[0].toString().equalsIgnoreCase("test")) {
					p.sendMessage(Utils.globalcount() + "");
				} else {
					sendHelpMessage(p);
				}
			} else {
				sendHelpMessage(p);
			}
		} else {

		}
		return false;

	}

	public void sendHelpMessage(Player p) {
		p.sendMessage(Utils.chatn(p, "&8&m-------------------------"));
		p.sendMessage(Utils.chatn(p, "&f/admin server <server> &7Shows info about the server!"));
		p.sendMessage(Utils.chatn(p, "&f/admin list &7Lists all servers!"));
		p.sendMessage(Utils.chatn(p, "&f/admin forceupdate &7forces a server SQL update!"));
		p.sendMessage(Utils.chatn(p, "&f/admin sql &7Shows SQL info!"));
		p.sendMessage(Utils.chatn(p, "&f/admin cmd <server/all> <cmd> &7sends a command to the server!"));
		p.sendMessage(Utils.chatn(p, "&f"));
		p.sendMessage(Utils.chatn(p,
				"&a" + Main.getPlugin(Main.class).getDescription().getName() + " by "
						+ ServerVersion.removeLastChar(
								Main.getPlugin(Main.class).getDescription().getAuthors().toString().substring(1) + "")
						+ " &2&o" + Main.getPlugin(Main.class).getDescription().getVersion()));
		p.sendMessage(Utils.chatn(p, "&8&m-------------------------"));
	}
}