package eu.avalonya.api.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import eu.avalonya.api.AvalonyaAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class CustomItemStack extends ItemStack {

    private static final String BASE_URL = "https://textures.minecraft.net/texture/";

    public CustomItemStack(Material material) {
        super(material);
    }

    public CustomItemStack(ItemStack item) {
        super(item);
    }

    public CustomItemStack(ItemStack item, Consumer<ItemMeta> consumer) {
        super(item);

        final ItemMeta itemMeta = this.getItemMeta();

        consumer.accept(itemMeta);
        this.setItemMeta(itemMeta);
    }

    public CustomItemStack(Material material, Consumer<ItemMeta> consumer) {
        this(new ItemStack(material), consumer);
    }

    public CustomItemStack(Material material, String name, String... lore) {
        this(material, itemMeta -> {
            final List<TextComponent> lines = new ArrayList<>();

            for (String s : lore) {
                lines.add(Component.text(s));
            }
            itemMeta.displayName(Component.text(name));
            itemMeta.lore(lines);
        });
    }

    public CustomItemStack(Player player, String name, String... lore){
        this(Material.PLAYER_HEAD, itemMeta -> {
            final SkullMeta meta = (SkullMeta) itemMeta;
            final List<TextComponent> lines = new ArrayList<>();

            for (String s : lore){
                lines.add(Component.text(s));
            }
            meta.displayName(Component.text(name));
            meta.lore(lines);
            meta.setOwningPlayer(player);
        });
    }

    public CustomItemStack(Player player, Consumer<ItemMeta> consumer) {
        this(Material.PLAYER_HEAD, consumer);
    }

    public CustomItemStack(String texture, String name, String ...lore) {
        this(Material.PLAYER_HEAD, itemMeta -> {
            final SkullMeta meta = (SkullMeta) itemMeta;
            final List<TextComponent> lines = Arrays.stream(lore).map(Component::text).toList();
            final PlayerProfile profile = Bukkit.createProfileExact(UUID.randomUUID(), null);
            final PlayerTextures playerTextures = profile.getTextures();

            try {
                playerTextures.setSkin(new URL(BASE_URL + texture));
                profile.setTextures(playerTextures);
            } catch (MalformedURLException e) {
                AvalonyaAPI.getInstance().getLogger().warning("Error while setting texture: " + e.getMessage());
            }

            meta.displayName(Component.text(name));
            meta.lore(lines);
            meta.setPlayerProfile(profile);
        });
    }

    public void setName(String name) {
        final ItemMeta itemMeta = getItemMeta();

        itemMeta.displayName(Component.text(name));
        setItemMeta(itemMeta);
    }

    public void setLore(String ...lore) {
        final ItemMeta itemMeta = getItemMeta();
        final List<TextComponent> lines = new ArrayList<>();

        for (String s : lore) {
            lines.add(Component.text(s));
        }
        itemMeta.lore(lines);
        setItemMeta(itemMeta);
    }

    public void replace(String old, String newString) {
        final ItemMeta itemMeta = getItemMeta();

        if (itemMeta == null) {
            return;
        }

        final TextReplacementConfig replacementConfig = TextReplacementConfig.builder().matchLiteral(old).replacement(newString)
                .build();

        List<Component> lore = itemMeta.lore();
        if (lore != null)
            itemMeta.lore(lore.stream().map(c -> c.replaceText(replacementConfig)).toList());

        final Component displayName = itemMeta.displayName();
        if (displayName != null)
            itemMeta.displayName(displayName.replaceText(replacementConfig));

        setItemMeta(itemMeta);
    }

    public void setAmount(int amount) {
        super.setAmount(amount);

        replace("{n}", String.valueOf(amount));
    }

}
