package ru.artorium.storages.data;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Getter
public class Redis {

    private final RedissonClient client;
    private static final String URL = "redis://redis-14894.c250.eu-central-1-1.ec2.cloud.redislabs.com:14894/0";
    private static final String USER = "default";
    private static final String PASSWORD = "2vfJF4ijU3azjEFqt7tQmyNwMNpCk8G0";

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
