package eu.avalonya.api.models;

import eu.avalonya.api.models.serialization.ModelSerialization;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractModel implements ModelSerialization {

    private boolean created = false;

    public abstract Pair<String, String> getId();

    public abstract Map<String, String> getRepositoryAttributes();

    public static AbstractModel deserialize(Map<String, Object> data) {
        throw new RuntimeException("deserialize method is not implemented yet");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractModel model) {
            return model.getId().value().equals(getId().value());
        }

        return false;
    }
}
