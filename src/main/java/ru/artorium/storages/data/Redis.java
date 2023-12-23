package ru.artorium.storages.data;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Getter
public class Redis {

    private final RedissonClient client;
    private static final String URL = "your redis url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public Redis() {
        final Config config = new Config();
        config.useSingleServer()
            .setAddress(URL)
            .setPassword(PASSWORD)
            .setUsername(USER);


        this.client = Redisson.create(config);
    }


    public void close() {
        this.client.shutdown();
    }

}
