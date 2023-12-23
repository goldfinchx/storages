package ru.artorium.storages.core.cache;

import java.util.Map;
import ru.artorium.storages.core.data.DataObject;

public interface CacheManager<D extends DataObject<I>, I> {

    void save(D data);
    void invalidate(I id);
    D get(I id);

    Map<I, D> getAll();

    boolean isCached(I id);

}
