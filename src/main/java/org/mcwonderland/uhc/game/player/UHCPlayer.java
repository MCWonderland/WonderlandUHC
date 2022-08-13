package org.mcwonderland.uhc.game.player;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.role.Role;
import org.mcwonderland.uhc.game.player.role.SimpleRoleFactory;
import org.mcwonderland.uhc.game.player.role.models.RoleBoard;
import org.mcwonderland.uhc.game.player.role.models.RoleEventHandler;
import org.mcwonderland.uhc.game.player.staff.StaffOptions;
import org.mcwonderland.uhc.stats.UHCStats;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompMetadata;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class UHCPlayer {
    private static final Set<UHCPlayer> allPlayers = new HashSet<>();

    public static final String UHCPLAYER_TAG = "wonderlanduhc_uhcplayer_tag";

    @Getter(AccessLevel.PRIVATE)
    private Player entityPlayer;
    private UHCTeam team;
    private UHCStats stats;
    private StaffOptions staffOptions = new StaffOptions();
    private boolean teamChat;

    //new
    @Setter(AccessLevel.PRIVATE)
    private Role role;

    public static Set<UHCPlayer> getAllPlayers() {
        return Sets.newHashSet(allPlayers);
    }

    public static UHCPlayer getUHCPlayer(Player player) {
        if (!CompMetadata.hasTempMetadata(player, UHCPLAYER_TAG))
            CompMetadata.setTempMetadata(player, UHCPLAYER_TAG, new UHCPlayer(player));

        return getFromEntity(player);
    }

    public static UHCPlayer getFromEntity(Entity entity) {
        try {
            UHCPlayer uhcPlayer = ( UHCPlayer ) CompMetadata.getTempMetadata(entity, UHCPLAYER_TAG).value();

            if (entity instanceof Player)
                uhcPlayer.setEntityPlayer(( Player ) entity);

            return uhcPlayer;
        } catch (NullPointerException e) {
            return null;
        }
    }


    private UHCPlayer(Player player) {
        this.entityPlayer = player;

        initPlayerStatusFirstTime();
        allPlayers.add(this);

        loadStats();
    }

    private void initPlayerStatusFirstTime() {
        if (GameUtils.isGameStarted())
            this.role = SimpleRoleFactory.getRole(RoleName.SPECTATOR);
        else
            this.role = SimpleRoleFactory.getRole(RoleName.PLAYER);
    }

    private void loadStats() {
        this.stats = new UHCStats();
        Common.runLaterAsync(() -> this.stats = WonderlandUHC.getStatsStorage().loadOrCreate(this));
    }


    public boolean isOnline() {
        return entityPlayer.isOnline();
    }

    public boolean isDead() {
        return !isAlive();
    }

    public boolean isAlive() {
        return getRoleName() == RoleName.PLAYER;
    }

    public Player getPlayer() {
        return entityPlayer;
    }

    public LivingEntity getEntity() {
        LivingEntity entity = getPlayer();

        if (!getPlayer().isOnline() && !isDead())
            entity = CombatRelog.get(this).getEntity();

        return entity;
    }

    public String getName() {
        return entityPlayer.getName();
    }

    public UUID getUniqueId() {
        return entityPlayer.getUniqueId();
    }

    public int getKills() {
        return stats.kills;
    }

    //new
    public RoleEventHandler getEventHandler() {
        return role.getEventHandler();
    }

    public RoleBoard getBoard() {
        return role.getRoleboard();
    }

    public void chat(String message) {
        role.chat(entityPlayer, message);
    }

    private void changeRole(RoleName roleName) {
        Role role = SimpleRoleFactory.getRole(roleName);
        getRole().end(entityPlayer);

        setRole(role);
        checkHide();
        applyRoleStuff();
    }

    public void applyRoleStuff() {
        role.applyStuff(entityPlayer);
    }

    public void checkHide() {
        role.checkHide(entityPlayer);
    }

    public void changePlayerRole() {
        changeRole(RoleName.PLAYER);
    }

    public void changeSpectatorRole() {
        changeRole(RoleName.SPECTATOR);
    }

    public void changeStaffRole() {
        changeRole(RoleName.STAFF);
    }

    public RoleName getRoleName() {
        return role.getName();
    }
}
