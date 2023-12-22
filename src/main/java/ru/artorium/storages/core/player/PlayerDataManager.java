package ru.artorium.storages.core.player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;
import ru.artorium.storages.core.data.DataManager;

@Accessors(chain = true, fluent = true)
public abstract class PlayerDataManager<P extends PlayerData> extends DataManager<P, UUID> {

    @Setter private Consumer<P> onCreate = $ -> {};

    public PlayerDataManager(JavaPlugin plugin) {
        super(plugin, false);
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(this), plugin);
    }

    private P create(UUID uuid) {
        final P data;

        try {
            data = (P) this.getTemplate().getClass().getConstructor(UUID.class).newInstance(uuid);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        this.onCreate.accept(data);
        return data;
    }

    @Override
    public P load(UUID id) {
        if (this.isCached(id)) {
            return this.getCached(id);
        }

        if (this.isExists(id)) {
            final P data = this.getStored(id);
            this.onLoad().accept(data);
            this.cache(data);
            return data;
        } else {
            return this.create(id);
        }
    }

}
