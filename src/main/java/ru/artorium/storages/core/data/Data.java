package ru.artorium.storages.core.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@lombok.Data
@BsonDiscriminator
@NoArgsConstructor
@AllArgsConstructor
public abstract class Data<I> {

    private I id;

}
