package ru.artorium.storages.example;

import org.bukkit.plugin.java.JavaPlugin;
import ru.artorium.storages.core.data.DataManager;

public class TestObjectDataManager extends DataManager<TestObject, String> {

    public TestObjectDataManager(JavaPlugin plugin) {
        super(plugin.getName(), false);
    }

    @Override
    protected TestObject getTemplate() {
        return new TestObject();
    }
}
