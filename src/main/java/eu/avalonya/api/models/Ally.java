package eu.avalonya.api.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@DatabaseTable(tableName = "town_allies")
@Getter
@NoArgsConstructor
public class Ally {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "town_receiver", foreign = true, foreignAutoRefresh = true)
    private Town receiver;

    @DatabaseField(columnName = "town_sender", foreign = true, foreignAutoRefresh = true)
    private Town sender;

    @DatabaseField(columnName = "pending", defaultValue = "false", dataType = DataType.BOOLEAN)
    @Setter
    private boolean pending = false;

    @DatabaseField(columnName= "created_at")
    private final Date createdAt = new Date();

    public Ally(Town sender, Town receiver){
        this.sender = sender;
        this.receiver = receiver;
    }
}
