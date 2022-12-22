package org.mcwonderland.uhc.model.tutorial.model;

import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.mineacademy.fo.remain.CompMetadata;

public abstract class Tutorial {
    public static final String CANCELLER = "exit";
    private static final String TUTORIAL_TAG = "wonderlanduhc_tutorial_tag";

    public static Tutorial getCurrentTutorial(Player player) {
        MetadataValue metadata = CompMetadata.getTempMetadata(player, TUTORIAL_TAG);

        return metadata == null ? null : (Tutorial) metadata.value();
    }

    public static boolean isInTutorial(Player player) {
        return CompMetadata.hasTempMetadata(player, TUTORIAL_TAG);
    }

    public static void exit(Player player) {
        CompMetadata.removeTempMetadata(player, TUTORIAL_TAG);
    }

    private TutorialSection currentSection;
    private final Player player;

    public Tutorial(Player player) {
        this.player = player;
    }

    protected abstract TutorialSection getFirstSection();

    public final void start() {
        CompMetadata.setTempMetadata(player, TUTORIAL_TAG, this);

        showSection(getFirstSection());
    }

    public final void showNextSection() {
        showSection(currentSection.nextSection());
    }

    private final void showSection(TutorialSection section) {
        section.show(player);

        if (section.isLastOne()) {
            Tutorial.exit(player);
            Extra.sound(player, Sounds.Tutorial.FINISHED);
        } else
            Extra.sound(player, Sounds.Tutorial.NEXT_SECTION);

        currentSection = section;
    }
}
