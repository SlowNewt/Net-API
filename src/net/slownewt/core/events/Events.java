package net.slownewt.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import net.slownewt.core.sql.server.S_SQL;
import net.slownewt.core.sql.server.S_SQL_C;

public class Events implements Listener {

	@EventHandler
	public void onServerListPing(final ServerListPingEvent e) {
		e.setMotd(S_SQL_C.getInfoS("MOTD", S_SQL.serverid));
	}
}