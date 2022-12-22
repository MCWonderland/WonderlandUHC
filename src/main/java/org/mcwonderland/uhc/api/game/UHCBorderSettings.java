package org.mcwonderland.uhc.api.game;

import org.mcwonderland.uhc.game.border.BorderType;

public interface UHCBorderSettings {
    void setInitialBorder(Integer initialBorder);

    void setInitialNetherBorder(Integer initialNetherBorder);

    void setFinalSizeOfShrinkModeBorder(Integer finalSizeOfShrinkModeBorder);

    void setBorderType(BorderType borderType);

    void setBorderShrinkSpeed(Double borderShrinkSpeed);

    Integer getInitialBorder();

    Integer getInitialNetherBorder();

    Integer getFinalSizeOfShrinkModeBorder();

    BorderType getBorderType();

    Double getBorderShrinkSpeed();
}
