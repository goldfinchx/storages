package ru.artorium.storages.core.cache;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RedissonClient;
import ru.artorium.storages.Storages;
import ru.artorium.storages.core.data.Data;

public class RedisCacheManager<D extends Data<I>, I> implements CacheManager<D, I> {

    private final RedissonClient client;
    private final String prefix;

    public RedisCacheManager(Class<D> clazz) {
        this.client = Storages.get().getRedis().getClient();
        this.prefix = clazz.getName();
    }

    @Override
    public void save(D data) {
        this.client.getBucket(this.prefix + ":" + data.getId()).set(data);
    }

    @Override
    public void invalidate(I id) {
        this.client.getBucket(this.prefix + ":" + id).delete();
    }

    @Override
    public D get(I id) {
        return (D) this.client.getBucket(this.prefix + ":" + id).get();
    }

    @Override
    public Map<I, D> getAll() {
        final Map<I, D> map = new HashMap<>();
        this.client.getKeys().getKeysByPattern(this.prefix + ":*").forEach(key -> {
            map.put((I) key, (D) this.client.getBucket(key).get());
        });

        return map;
    }

    @Override
    public boolean isCached(I id) {
        return this.client.getBucket(this.prefix + ":" + id).isExists();
    }


}
