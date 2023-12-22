package ru.artorium.storages.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

@Getter
public class Mongo {

    private final MongoClient client;

    private static final String URL = "mongodb+srv://root:k9f2GJ8gj8GJ8I3to1jk0j0Q3OTqo31-124@green-cluster.gucfufg.mongodb.net";

    public Mongo() {
        final ConnectionString connectionString = new ConnectionString(URL);
        final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .retryWrites(true)
            .writeConcern(WriteConcern.MAJORITY)
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .codecRegistry(
                CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder()
                    .automatic(true)
                    .build())))
            .build();

        this.client = MongoClients.create(settings);
    }

    public void close() {
        this.client.close();
    }

}
