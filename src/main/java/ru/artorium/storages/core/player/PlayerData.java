package ru.artorium.storages.core.player;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import ru.artorium.storages.core.data.DataObject;

@lombok.Data
@BsonDiscriminator
@NoArgsConstructor
@AllArgsConstructor
public abstract class PlayerData extends DataObject<UUID> {

}
