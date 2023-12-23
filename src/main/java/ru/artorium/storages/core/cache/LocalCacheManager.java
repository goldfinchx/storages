package ru.artorium.storages.core.cache;

import java.util.HashMap;
import java.util.Map;
import ru.artorium.storages.core.data.DataObject;

public class LocalCacheManager<D extends DataObject<I>, I> implements CacheManager<D, I> {

    private final Map<I, D> cache = new HashMap<>();

    @Override
    public void save(D data) {
        this.cache.put(data.getId(), data);
    }

    @Override
    public void invalidate(I id) {
        this.cache.remove(id);
    }

    @Override
    public D get(I id) {
        return this.cache.get(id);
    }

    @Override
    public Map<I, D> getAll() {
        return this.cache;
    }

    @Override
    public boolean isCached(I id) {
        return this.cache.containsKey(id);
    }
}
