package ru.artorium.storages;

import java.util.Map;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.artorium.storages.data.Mongo;
import ru.artorium.storages.data.Redis;
import ru.artorium.storages.example.TestObject;
import ru.artorium.storages.example.TestObjectDataManager;

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
