package org.mcwonderland.uhc.api.game;

public interface UHCTimerSettings {
    void setPvpTime(Integer pvpTime);

    void setDamageTime(Integer damageTime);

    void setHealTime(Integer healTime);

    void setBorderShrinkTime(Integer borderShrinkTime);

    void setDisableNetherTime(Integer disableNetherTime);

    Integer getPvpTime();

    Integer getDamageTime();

    Integer getHealTime();

    Integer getBorderShrinkTime();

    Integer getDisableNetherTime();
}
