package eu.avalonya.api.sql;

import eu.avalonya.api.models.AvalonyaPlayer;
import eu.avalonya.api.models.Rank;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Cache
{
    public static HashMap<UUID, AvalonyaPlayer> avaloniaPlayers = new HashMap<UUID, AvalonyaPlayer>();
    public static HashMap<Rank, List<String>> permissions = new HashMap<>();

    /**
     * Pour le moment on n'a pas besoin de stocker un AvalonyaPlayer, un Player spigot suffit.
     * Car on s'en sert uniquement pour envoyer un message, à voir dans le temps si on a besoin de plus.
     */
    public static ArrayList<Player> staffList = new ArrayList<>();
}
