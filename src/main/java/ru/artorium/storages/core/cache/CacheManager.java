package ru.artorium.storages.core.cache;

import java.util.Map;
import ru.artorium.storages.core.data.Data;

public interface CacheManager<D extends Data<I>, I> {

    void save(D data);
    void invalidate(I id);
    D get(I id);

    Map<I, D> getAll();

    boolean isCached(I id);

}
