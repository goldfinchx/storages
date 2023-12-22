package ru.artorium.storages.core.player;

import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class PlayerListener implements Listener {

    private final PlayerDataManager manager;

    public PlayerListener(PlayerDataManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void on(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED)
            return;

        final UUID uuid = event.getUniqueId();
        this.manager.load(uuid);
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();
        this.manager.unload(uuid);
    }

    @EventHandler
    public void on(PluginDisableEvent event) {
        this.manager.unloadAll();
    }

}
