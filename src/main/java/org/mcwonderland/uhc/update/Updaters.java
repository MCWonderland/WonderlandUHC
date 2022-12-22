package org.mcwonderland.uhc.update;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Updaters {
    @Getter
    private List<Updater> updaters = new ArrayList<>();

    public Updaters() {
        updaters.add(new OldMenusCheck());
    }
}
