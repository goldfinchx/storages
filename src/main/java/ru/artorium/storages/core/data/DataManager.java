package ru.artorium.storages.core.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;
import ru.artorium.storages.Storages;
import ru.artorium.storages.core.cache.CacheManager;
import ru.artorium.storages.core.cache.LocalCacheManager;
import ru.artorium.storages.core.cache.RedisCacheManager;
import ru.artorium.storages.utils.TextUtils;

@Accessors(chain = true, fluent = true)
public abstract class DataManager<D extends Data<I>, I> {

    private final CacheManager<D, I> cacheManager;
    private final MongoCollection<D> collection;

    @Setter@Getter private Consumer<D> onLoad = $ -> {};
    @Setter@Getter private Consumer<D> onUnload = $ -> {};

    public DataManager(JavaPlugin plugin, boolean useRedis) {
        this.collection = (MongoCollection<D>) Storages.get().getMongo().getClient()
            .getDatabase(plugin.getName())
            .getCollection(TextUtils.getCollectionName(this.getTemplate().getClass()), this.getTemplate().getClass());

        this.cacheManager = useRedis ? new RedisCacheManager<>(this.getTemplate().getClass()) : new LocalCacheManager<>();

    }

    public D get(I id) {
        return this.isCached(id) ? this.getCached(id) : this.load(id);
    }
    public D load(I id) {
        if (this.isCached(id)) {
            return this.getCached(id);
        }

        if (this.isExists(id)) {
            final D data = this.getStored(id);
            this.onLoad.accept(data);
            this.cache(data);
            return data;
        }

        throw new NoSuchElementException("Data Element with id " + id + " not found");
    }
    public void unload(I id) {
        if (!this.isCached(id)) {
            return;
        }

        final D data = this.getCached(id);
        this.onUnload.accept(data);
        this.save(id);
        this.invalidate(id);
    }
    public void save(I id) {
        if (!this.isCached(id)) {
            return;
        }

        final D data = this.getCached(id);
        this.updateRemote(data);
    }

    public void unloadAll() {
        this.cacheManager.getAll().forEach((id, data) -> this.unload(id));
    }

    public Map<I, D> getCache() {
        return this.cacheManager.getAll();
    }

    public Map<I, D> getStorage() {
        final Map<I, D> storage = new HashMap<>();
        for (final D data : this.collection.find()) {
            storage.put(data.getId(), data);
        }

        return storage;
    }

    protected void cache(D data) {
        this.cacheManager.save(data);
    }
    protected void invalidate(I id) {
        this.cacheManager.invalidate(id);
    }
    protected boolean isCached(I id) {
        return this.cacheManager.isCached(id);
    }
    protected boolean isExists(I id) {
        return this.collection.find(Filters.eq("_id", id)).first() != null;
    }
    protected void updateRemote(D data) {
        this.collection.replaceOne(Filters.eq("_id", data.getId()), data);
    }

    protected D getStored(I id) {
        return this.collection.find(Filters.eq("_id", id)).first();
    }
    protected D getCached(I id) {
        return this.cacheManager.get(id);
    }

    protected abstract D getTemplate();
}
