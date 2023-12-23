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

        final TestObjectDataManager testObjectDataManager = new TestObjectDataManager(this);
        testObjectDataManager.load("test"); // load object from storage to cache
        testObjectDataManager.save("test"); // save object from cache to storage
        testObjectDataManager.unload("test"); // unload object from cache
        testObjectDataManager.unloadAll(); // unload all objects from cache

        final TestObject object = testObjectDataManager.get("test"); // get object from cache
        final Map<String, TestObject> allStoredObjects = testObjectDataManager.getStorage(); // get all objects from storage
        final Map<String, TestObject> allCachedObjects = testObjectDataManager.getCache(); // get all objects from cache
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
