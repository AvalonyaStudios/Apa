package eu.avalonya.api.models;

import eu.avalonya.api.exceptions.TownRoleLimiteException;
import eu.avalonya.api.items.ItemAccess;
import net.kyori.adventure.text.Component;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Town model class that represents a town in the Avalonya api.
 */
public class Town implements ItemAccess {

    private String name;
    private String politicalStatus = "";
    private Citizen mayor;
    private float money = 0.0f;
    private float taxes = 0.0f;
    private final List<Chunk> claims = new ArrayList<>();
    private final List<Citizen> citizens = new ArrayList<>();
    private Location spawn;
    private boolean taxesEnabled = false;
    private boolean spawnHostileMob = false;
    private boolean fireSpread = false;
    private boolean explosions = false;
    private boolean publicTown = false;
    private boolean friendlyFire = false;
    private final List<Town> enemies = new ArrayList<>();
    private final List<Town> allies = new ArrayList<>();
    private List<Pattern> bannerPatterns = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();

    public Town(String name, Citizen mayor) {
        this.name = name;
        this.mayor = mayor;

        citizens.add(mayor);
        claims.add(
                mayor.getPlayer().getLocation().getChunk()
        );

        spawn = createSpawn();
        roles.add(
                new Role("citizen", Role.Color.CITIZEN)
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name){this.name =  name;}

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setMayor(Citizen mayor) {
        this.mayor = mayor;
    }

    public Citizen getMayor() {
        return mayor;
    }

    public float deposit(float amount) {
        return money += amount;
    }

    public float withdraw(float amount) {
        return money -= amount;
    }

    public float getMoney() {
        return money;
    }

    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }

    public float increaseTaxes(float amount) {
        this.taxes += amount;

        return taxes;
    }

    public float decreaseTaxes(float amount) {
        this.taxes -= amount;

        return taxes;
    }

    public float getTaxes() {
        return taxes;
    }

    public void addClaim(Chunk chunk) {
        claims.add(chunk);
    }

    public void removeClaim(Chunk chunk) {
        claims.remove(chunk);
    }

    public boolean hasClaim(Chunk chunk) {
        return claims.contains(chunk);
    }

    public List<Chunk> getClaims() {
        return claims;
    }

    public void addCitizen(@NotNull Citizen citizen) {
        citizen.setRole(roles.get(0));
        citizens.add(citizen);
    }

    public void removeCitizen(Citizen citizen) {
        citizens.remove(citizen);
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }

    public Citizen getCitizen(Player player) {
        return getCitizen(player.getUniqueId());
    }

    public Citizen getCitizen(UUID uuid) {
        for (Citizen citizen : citizens) {
            if (citizen.getPlayer().getUniqueId().equals(uuid)) {
                return citizen;
            }
        }
        return null;
    }

    public boolean hasCitizen(Player player) {
        return getCitizen(player) != null;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    protected Location createSpawn() {
        return claims.get(0).getBlock(8, 0, 8).getLocation();
    }

    public void setTaxesEnabled(boolean taxesEnabled) {
        this.taxesEnabled = taxesEnabled;
    }

    public boolean hasTaxesEnabled() {
        return taxesEnabled;
    }

    public void setSpawnHostileMob(boolean spawnHostileMob) {
        this.spawnHostileMob = spawnHostileMob;
    }

    public boolean hasSpawnHostileMob() {
        return spawnHostileMob;
    }

    public void setFireSpread(boolean fireSpread) {
        this.fireSpread = fireSpread;
    }

    public boolean hasFireSpread() {
        return fireSpread;
    }

    public void setExplosions(boolean explosions) {
        this.explosions = explosions;
    }

    public boolean hasExplosions() {
        return explosions;
    }

    public void setPublic(boolean publicTown) {
        this.publicTown = publicTown;
    }

    public boolean isPublic() {
        return publicTown;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public boolean hasFriendlyFire() {
        return friendlyFire;
    }

    public void addEnemy(Town town) {
        enemies.add(town);
    }

    public boolean hasEnemy(Town town) {
        return enemies.contains(town);
    }

    public void removeEnemy(Town town) {
        enemies.remove(town);
    }

    public List<Town> getEnemies() {
        return enemies;
    }

    public void addAlly(Town town) {
        allies.add(town);
    }

    public void removeAlly(Town town) {
        allies.remove(town);
    }

    public boolean hasAlly(Town town) {
        return allies.contains(town);
    }

    public List<Town> getAllies() {
        return allies;
    }

    public void setBannerMeta(List<Pattern> bannerPatterns) {
        this.bannerPatterns = bannerPatterns;
    }

    public List<Pattern> getBannerMeta() {
        return bannerPatterns;
    }

    public void addRole(String name) throws TownRoleLimiteException{

        if (this.roles.size() >= 4) {
            throw new TownRoleLimiteException();
        }
        addRole(new Role(name, Role.Color.values()[this.roles.size()]));
    }

    public void addRole(Role role) throws TownRoleLimiteException {

        if (this.roles.size() >= 4) {
            throw new TownRoleLimiteException();
        }
        this.roles.add(role);
    }

    public Role getRole(Role.Color color) {
        for (Role role : this.roles) {
            if (role.getColor().equalsIgnoreCase(color.getColor())) {
                return role;
            }
        }
        return null;
    }

    public Role getRole(String name) {
        for (Role role : this.roles) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(Material.WHITE_BANNER);
        final BannerMeta meta = (BannerMeta) item.getItemMeta();

        meta.setPatterns(bannerPatterns);
        meta.displayName(Component.text("§f§l" + name + " §7Flag"));
        item.setItemMeta(meta);

        return item;
    }

}
