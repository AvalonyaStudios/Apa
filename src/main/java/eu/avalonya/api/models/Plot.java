package eu.avalonya.api.models;

import com.j256.ormlite.table.DatabaseTable;
import eu.avalonya.api.models.enums.PlotPermission;
import eu.avalonya.api.models.enums.PlotType;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Map;

/**
 * Un plot est une parcelle de terrain (un chunk) qui peut être achetée par un joueur,
 * avoir différents types ainsi que des permissions spécifiques.
 *
 * @version 1.0
 * @see PlotType
 */
@Getter
@Setter
@NoArgsConstructor
public class Plot extends AbstractModel {

    private int id;
    private int x;
    private int z;
    private String name;
    private int type;
    private int permissions;
    private boolean outpost;
    private Town town;

    public Plot(Chunk chunk, Town town) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.town = town;
    }

    public Pair<String, String> getId() {
        return Pair.of("plot_id", String.valueOf(id));
    }

    public Chunk getChunk(World world) {
        return world.getChunkAt(x, z);
    }

    public boolean isType(PlotType type) {
        return this.type == type.ordinal();
    }

    public boolean hasPermission(PlotPermission permission) {
        return (this.permissions & (1 << permission.ordinal())) != 0;
    }

    public void addPermission(PlotPermission permission) {
        this.permissions |= (1 << permission.ordinal());
    }

    @Override
    public Map<String, String> getRepositoryAttributes() {
        return Map.of(
                "town_name", this.getTown().getName(),
                "plot_id", String.valueOf(this.id)
        );
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "id", this.id,
                "name", this.name,
                "is_outpost", this.outpost,
                "x", this.x,
                "z", this.z,
                "type", this.type,
                "permissions", this.permissions
        );
    }

    public static Plot deserialize(Map<String, Object> data) {
        Plot plot = new Plot();

        plot.setId(Double.valueOf((double) data.get("id")).intValue());
        plot.setName((String) data.get("name"));
        plot.setTown((Town) data.get("town"));
        plot.setOutpost((Boolean) data.get("is_outpost"));
        plot.setPermissions((Integer) data.get("permissions"));

        return plot;
    }
}
