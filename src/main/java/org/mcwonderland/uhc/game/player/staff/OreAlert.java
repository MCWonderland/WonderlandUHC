package org.mcwonderland.uhc.game.player.staff;

import lombok.Getter;
import org.bukkit.Material;
import org.mineacademy.fo.remain.CompColor;
import org.mineacademy.fo.remain.CompMaterial;

@Getter
public enum OreAlert {
    GOLD_ORE(CompMaterial.GOLD_ORE, CompColor.YELLOW),
    DIAMOND_ORE(CompMaterial.DIAMOND_ORE, CompColor.AQUA);

    private final Material material;
    private final CompColor color;

    OreAlert(CompMaterial compMaterial, CompColor color) {
        this.material = compMaterial.getMaterial();
        this.color = color;
    }

    public static OreAlert fromMaterial(Material material) {
        for (OreAlert value : values()) {
            if (value.getMaterial() == material)
                return value;
        }

        return null;
    }

    public String colorizedName() {
        return color.getChatColor() + material.name();
    }
}
