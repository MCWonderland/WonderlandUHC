package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.util.RuntimeUtil;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class StaffLines extends GameLines {

    public StaffLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replaceGlobal(SimpleReplacer simpleReplacer) {
        return super.replaceGlobal(simpleReplacer)
                .replace("{tps}", RuntimeUtil.getTPSFormat().format(RuntimeUtil.getTPS(0)))
                .replace("{free_ram}", RuntimeUtil.AvailableMemory());
    }
}
