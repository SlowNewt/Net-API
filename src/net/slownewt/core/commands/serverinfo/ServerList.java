package net.slownewt.core.commands.serverinfo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.slownewt.core.Main;
import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.sql.server.S_SQL_C;
import net.slownewt.core.utils.Utils;
import net.slownewt.core.utils.VersionConversion;

public class ServerList implements Listener {
	static String AList = "&8Admin Server List";

	public static void openAdminManu(Player p) {
		S_SQL.getServers();
		int invSize = Math.min(54, ((S_SQL.servers.size() - 1) / 9 + 1) * 9);
		int status = 0;
		int usedSlot = -1;
		String[] Servers = S_SQL.servers.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
		Inventory inva = Bukkit.getServer().createInventory(p, invSize, Utils.chatother(AList));
		for (String key : Servers) {
			if (S_SQL_C.getInfoB("STATUS", key)) {
				if (S_SQL_C.getInfoB("WHITELIST", key)) {
					status = 4;
				} else {
					status = 5;
				}
			} else {
				status = 14;
			}
			int onlinePlayers = S_SQL_C.getInfoI("ONLINE", key);
			int maxPlayers = S_SQL_C.getInfoI("MAX", key);
			String serverName = S_SQL_C.getInfoS("NAME", key);
			String lastStart = S_SQL_C.getInfoS("LASTSTART", key);
			String BaseVersion = S_SQL_C.getInfoS("VERSION", key);
			ItemStack it = new ItemStack(VersionConversion.getOldClay());
			ItemMeta itm = it.getItemMeta();
			itm.setDisplayName(Utils.chat(p, "&f" + serverName));
			it.setDurability((short) status);
			List<String> lore = new ArrayList<>();
			lore.add(Utils.chatn(p, "&8&m--------------------"));
			lore.add(Utils.chatn(p, "&7Server ID: &f" + key));
			lore.add(Utils.chatn(p, "&7Status: &f" + Utils.Whistlist(S_SQL_C.getInfoS("NAME", key))));
			lore.add(Utils.chatn(p, "&7Players: &f" + onlinePlayers + "/" + maxPlayers));
			lore.add(Utils.chatn(p, "&7Uptime: &f" + lastStart));
			lore.add(Utils.chatn(p, "&7Base Version: &f" + BaseVersion));
			lore.add(Utils.chatn(p, "&7TPS: &f" + Utils.formattedTPS(key)));
			if (!(status == 14)) {
				lore.add(Utils.chatn(p, "&f"));
				if (status == 5) {
					if (key.equals(S_SQL.serverid)) {
						lore.add(Utils.chatn(p, "&cYou are alredy connected to " + serverName + ""));
					} else {
						if (!Bukkit.getOnlineMode()) {
							lore.add(Utils.chatn(p, "&aClick to connect to " + serverName + "!"));
						} else {
							lore.add(Utils.chatn(p, "&cThe server you are on is not connected to bungeecord!"));
						}
					}
				} else {
					if (p.hasPermission(Main.permission + "whitelistbypass")) {
						if (!Bukkit.getOnlineMode()) {
							lore.add(Utils.chatn(p, "&aClick to connect to " + serverName + "!"));
						} else {
							lore.add(Utils.chatn(p, "&cThe server you are on is not connected to bungeecord"));
						}
					} else {
						lore.add(Utils.chatn(p, "&cYou cant connect to " + serverName + "!"));
					}
				}
			}
			lore.add(Utils.chatn(p, "&8&m--------------------"));
			itm.setLore(lore);
			it.setItemMeta(itm);
			usedSlot = usedSlot + 1;
			inva.setItem(usedSlot, it);
			p.openInventory(inva);

		}
		usedSlot = -1;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem() == null)
			return;
		if (VersionConversion.getTitle(e.getInventory(), AList)) {
			//
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			String lore = e.getCurrentItem().getItemMeta().getLore().toString();
			if (lore.toString().contains(Utils.chat(p, "&aClick to connect to "))) {
				Utils.connect(p, e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
			} else if (lore.toString().contains(Utils.chat(p, "&cYou are alredy connected to "))) {
				p.sendMessage(Utils.chat(p, "&cYou are alredy connected to "
						+ e.getCurrentItem().getItemMeta().getDisplayName().substring(2) + "!"));
			} else if (lore.toString()
					.contains(Utils.chatn(p, "&cThe server you are on is not connected to bungeecord!"))) {
				p.sendMessage(Utils.chatn(p, "&8&m------------------------------"));
				p.sendMessage(Utils.chatn(p, "&cThe server you are on is not connected to bungeecord!"));
				p.sendMessage(Utils.chatn(p, "&eHere's a guide to setting up bungeecord: "));
				p.sendMessage(Utils.chatn(p, "&a&nhttps://www.youtube.com/watch?v=rhv-W6_y5nI"));
				p.sendMessage(Utils.chatn(p, "&8&m------------------------------"));
			} else {
				p.sendMessage(Utils.chat(p, "&cYou can't connect to "
						+ e.getCurrentItem().getItemMeta().getDisplayName().substring(2) + "!"));
			}
		}

	}
}