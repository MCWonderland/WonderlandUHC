package org.mcwonderland.uhc.model.freeze;

import lombok.Getter;

/**
 * 2019-12-17 下午 04:20
 */
@Getter
public enum FreezeType {
    SIT(new SitFreeze()),
    POTION(new PotionFreeze());

    private final FreezeMode freezeMode;

    FreezeType(FreezeMode mode) {
        this.freezeMode = mode;
    }
}
