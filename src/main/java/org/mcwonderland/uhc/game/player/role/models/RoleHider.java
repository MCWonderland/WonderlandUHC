package org.mcwonderland.uhc.game.player.role.models;

import org.mcwonderland.uhc.util.PlayerHider;
import org.bukkit.entity.Player;

public abstract class RoleHider {

    /*
    情況一覽:
    1. 加入伺服器
      - 遊戲尚未開始: 以lobbyhide為主判斷是否隱藏
      - 遊戲開始:
          如果是玩家: 所有人都看的到他, 隱藏spectator, staff
          如果是觀察者: 看的到玩家,但玩家看不到他,並讓開啟顯示觀察者的staff不要隱藏他
          如果是staff: 依照選項看是否顯示玩家或觀察者
    2. 死亡(變為觀察者)
        讓所有玩家跟觀察者看不到他 // 只有開啟顯示觀察者的人,才能看到他
    3. 使用/staff (toggle,untoggle)
        toggle: 讓所有玩家跟觀察者看不到他,staff則判斷是否看到staff
        untoggle: 同變為觀察者的方式
     */

    protected Player player;

    public void check(Player player) {
        this.player = player;

        checkHide();
    }

    protected abstract void checkHide();

    protected void hide(Player target) {
        PlayerHider.hide(player, target);
    }

    protected void show(Player target) {
        PlayerHider.show(player, target);
    }

    protected void visFor(Player target) {
        PlayerHider.show(target, player);
    }

    protected void invisFor(Player target) {
        PlayerHider.hide(target, player);
    }
}
