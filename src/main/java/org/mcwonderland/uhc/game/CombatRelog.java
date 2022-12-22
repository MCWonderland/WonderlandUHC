package org.mcwonderland.uhc.game;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.model.InventoryContent;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.mineacademy.fo.model.ChunkKeeper;
import org.mineacademy.fo.remain.CompMetadata;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class CombatRelog {

    public static final String COMBAT_RELOG_TAG = "wonderlanduhc_relog_tag";
    //todo 變成 treeMap，依照秒數排序 (移除時如果發現A移除不了，A後面的就不用動了(秒數必大於目前的)，避免消耗效能)
    private static HashMap<UHCPlayer, CombatRelog> relogPlayer = new HashMap<>();

    private UHCPlayer uhcPlayer;
    private LivingEntity entity;
    private int time = Settings.CombatRelog.RELOG_IN_MINUTES * 60;
    private int level;
    private int expToLevel;
    private float xp;
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private PlayerInventory inventory;

    public CombatRelog(UHCPlayer uhcPlayer, LivingEntity relogEntity) {
        Player player = uhcPlayer.getPlayer();

        this.uhcPlayer = uhcPlayer;
        this.entity = relogEntity;
        this.inventory = player.getInventory();
        this.xp = player.getExp();
        this.level = player.getLevel();
        this.expToLevel = player.getExpToLevel();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            relogEntity.addPotionEffect(effect);
        }
    }

    public static List<CombatRelog> getRelogs() {
        return Lists.newArrayList(relogPlayer.values());
    }

    public static boolean isRelogEntity(Entity entity) {
        return getByRelogEntity(entity) != null;
    }

    public static CombatRelog get(UHCPlayer uhcPlayer) {
        return relogPlayer.get(uhcPlayer);
    }

    public static CombatRelog getByRelogEntity(Entity entity) {
        MetadataValue metadata = CompMetadata.getTempMetadata(entity, COMBAT_RELOG_TAG);
        return metadata == null ? null : ( CombatRelog ) metadata.value();
    }

    public static CombatRelog setCombatRelog(UHCPlayer uhcPlayer) {
        CombatRelog relog = CombatRelog.get(uhcPlayer);

        if (relog != null)
            return relog;

        Player player = uhcPlayer.getPlayer();
        Location location = player.getLocation();

        ChunkKeeper.setForceLoaded(location.getChunk(), true);

        Villager villager = (( Villager ) player.getWorld().spawnEntity(location, EntityType.VILLAGER));
        Extra.noAIAndSilent(villager);
        villager.setCustomNameVisible(true);
        villager.setCustomName(UHCTeam.getTeam(player).getPrefix() + player.getName());
        villager.setProfession(Villager.Profession.LIBRARIAN);

        villager.getEquipment().setItemInHand(player.getItemInHand());
        villager.getEquipment().setArmorContents(player.getInventory().getArmorContents());
        villager.setAdult();
        Extra.copyHealth(player, villager);
        villager.setFireTicks(player.getFireTicks());

        relog = new CombatRelog(uhcPlayer, villager);
        CompMetadata.setTempMetadata(villager, COMBAT_RELOG_TAG, relog);
        CompMetadata.setTempMetadata(villager, UHCPlayer.UHCPLAYER_TAG, uhcPlayer);
        relogPlayer.put(uhcPlayer, relog);

        return relog;
    }

    public InventoryContent getInventoryContent() {
        return new InventoryContent(inventory);
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setInventoryContent(InventoryContent content) {
        content.setContents(inventory);
    }

    public void addInventoryItem(ItemStack item) {
        inventory.addItem(item);
    }

    public void remove() {
        ChunkKeeper.setForceLoaded(getEntity().getLocation().getChunk(), false);
        relogPlayer.remove(uhcPlayer);
    }
}
