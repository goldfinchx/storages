package ru.artorium.storages;

import java.io.FileInputStream;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.artorium.storages.data.Mongo;
import ru.artorium.storages.data.Redis;

@Getter
public final class Storages extends JavaPlugin {

    private static Storages instance;
    private Mongo mongo;
    private Redis redis;

    @Override
    public void onEnable() {
        instance = this;
        this.mongo = new Mongo();
        this.redis = new Redis();
    }

    @Override
    public void onDisable() {
        this.mongo.close();
        this.redis.close();
    }

    public static Storages get() {
        return instance;
    }

}
