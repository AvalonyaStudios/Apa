package eu.avalonya.api.sql;

import eu.avalonya.api.models.AvalonyaPlayer;
import eu.avalonya.api.models.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Cache
{
    public static HashMap<UUID, AvalonyaPlayer> avaloniaPlayers = new HashMap<UUID, AvalonyaPlayer>();
    public static HashMap<Rank, List<String>> permissions = new HashMap<>();
    public static ArrayList<String> staffList = new ArrayList<String>();
}
