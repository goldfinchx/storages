package ru.artorium.storages.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import ru.artorium.storages.core.data.DataObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@BsonDiscriminator
public class TestObject extends DataObject<String> {

    private int level;

}
