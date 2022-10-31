package org.mcwonderland.uhc.scenario.impl.special;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScenarioMole extends ConfigBasedScenario implements Listener {

    // 間諜模式
    // 20分鐘+5秒時，間諜產生，當上間諜的人會收到"你是間諜"的訊息，不是間諜的玩家會收到"你不是間諜"的訊息
    // 遊戲到最後，若只剩下一個隊伍但尚未結束，就代表隊伍內還有間諜

    /* 間諜玩家指令
    * /mole chat (/mc)
    * /mole kit
    * /mole scs
    * /mole list
    *

    * 非間諜玩家指令
    * /findmole
    * */

    // 邏輯
    // 間諜產生 -> 每隊隨機選出一位玩家擔任間諜 擔任間諜的玩家會獲得MoleTag
    // 分隊 -> 表面上=>原隊伍，實際上=>間諜私底下組成一個隊伍
    // 例：12人to4，間諜產生後變to3
    // MoleTag是間諜的通行證，擁有MoleTag的玩家才能使用間諜指令
    // 場上玩家同時有Mole跟平民 -> 遊戲繼續
    // 場上玩家只剩Mole -> 間諜獲勝
    // 場上玩家只剩平民 -> 間諜已被全數剷除 恢復正常勝利判斷

    //todo 記分板顯示是否為間諜
    // 自定義模式
    // 設定時間倒數讓間諜產生
    // MoleTag

//    @FilePath(name = "Mole-Spawn-Time")
    private Integer moleSpawnTime = 1;

    private static Set<UHCPlayer> MolePlayers = new HashSet<>();

    public ScenarioMole(ScenarioName name) {
        super(name);
    }

    // 讓間諜玩家獲得MoleTag
    private void moleTag() {}

    public static Set<UHCPlayer> getMoleList() { return Sets.newHashSet(MolePlayers); }

    // 是否為間諜
    public void ifMole() {}

}
