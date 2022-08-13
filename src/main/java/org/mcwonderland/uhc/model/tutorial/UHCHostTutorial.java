package org.mcwonderland.uhc.model.tutorial;

import org.mcwonderland.uhc.model.tutorial.model.Tutorial;
import org.mcwonderland.uhc.model.tutorial.model.TutorialSection;
import org.bukkit.entity.Player;

public class UHCHostTutorial extends Tutorial {

    public UHCHostTutorial(Player player) {
        super(player);
    }

    @Override
    protected TutorialSection getFirstSection() {
        return new Intro();
    }

    private class Intro extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&6&l歡迎來到WonderlandUHC教學 - 主持部分",
                    " ",
                    "以下將會教你如何完整主持一場UHC",
                    "在進行過程中，你隨時可以輸入 &b&lexit &f來停止教學",
                    " ",
                    "&e如果你已經準備好了，請輸入任意字元前往下個教學…"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new CheckSettingsSection();
        }
    }

    private class CheckSettingsSection extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "首先最重要的，請先&b&l確認你的遊戲設定無誤",
                    "如果有任何問題，趁白名單還沒關閉之前趕快做調整！",
                    " ",
                    "提醒: 大部分設定現在調整都還來得及，但請&4&l千萬不要把邊界大小條大！",
                    "否則玩家若傳送到未載入過的地方，會造成&c&l嚴重的伺服器卡頓。",
                    " ",
                    "&e確認設定無誤後，請輸入任一字元前往下個教學。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new StaffOptionsTutorial();
        }
    }

    private class StaffOptionsTutorial extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&b要擔任主持人，當然要有合適的管理工具。",
                    " ",
                    "只需輸入 &e/staff &f指令，即可開啟管理員模式&7(再輸入一次關閉)",
                    " ",
                    "&b此模式共有以下特點：",
                    "  1. 隱形，玩家與觀戰者不會看到你",
                    "  2. 玩家挖掘礦物通知(每五個通知一次)",
                    "  3. 更方便的管理工具，能切換移動速度…等",
                    "  4. 醒目的聊天文字",
                    " ",
                    "&6&l不妨現在就試試看吧！",
                    " ",
                    "&e了解如何使用管理員模式後，請輸入任一字元前往下個教學。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new WhitelistTutorial();
        }
    }

    private class WhitelistTutorial extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&a現在你可以將白名單關閉，開放玩家入場了。",
                    " ",
                    "備註: 你也可以手動使用 &e/whitelist &f指令來控制誰能加入遊戲。",
                    " ",
                    "&e白單關閉後，請輸入任一字元前往下個教學。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new WaitingPlayer();
        }
    }

    private class WaitingPlayer extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&a接著，只需等待玩家到齊，即可開始遊戲。",
                    " ",
                    "等待的過程中，你也可以&2再次確認本場遊戲的設定是否符合預期&f。",
                    " ",
                    "&e玩家到齊後，請輸入任一字元前往下個教學。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new TeamTutorialSection();
        }
    }

    private class TeamTutorialSection extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "在遊戲開始之前，還想告訴你這個插件的&5分隊功能&f：",
                    " ",
                    "  /uhc switchteam <A玩家> <B玩家> &e- 強制把A玩家加入B玩家的隊伍",
                    "  /uhc resetteam &e- 強制解散所有人的隊伍",
                    "  /uhc splitteam &e- 對&6沒有隊伍&e的玩家進行分隊",
                    "  /uhc splitteam true &e- 重新洗牌(先解散所有隊伍，再重新分配一次)",
                    " ",
                    "以上指令&3並非一定要使用&f，因為遊戲在開始的瞬間",
                    "系統也會自動使用 /uhc splitteam 指令來分隊。",
                    " ",
                    "&e學會如何手動分配隊伍後，請輸入任一字元前往下個教學。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new PrepareForStart();
        }
    }

    private class PrepareForStart extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&e&l很好，現在可以準備開始遊戲了！",
                    " ",
                    "請開啟設定介面&7(指令為 /uhc edit)&f，並&b點擊右下角的物品來開始傳送倒數。",
                    "以下大致為遊戲開始前的流程:",
                    " ",
                    "  &3傳送倒數 -> 傳送玩家並使其無法移動 -> 傳送完畢 -> ",
                    "  &3開始遊戲倒數 -> 倒數結束 -> 解除移動限制並開始遊戲。",
                    " ",
                    "&e遊戲開始後，請輸入任一字元前往下個教學。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new UsefulCommands();
        }
    }

    private class UsefulCommands extends TutorialSection {
        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&6&l恭喜你已完成此教學，你已大致學會如何主持一場UHC！",
                    " ",
                    "&b最後想再告訴你一些實用的指令，希望對你有幫助：",
                    "  /border &e- 強制於 10 秒後收縮邊界",
                    "  /respawn &e- 復活玩家",
                    "  /giveall &e- 給予所有玩家(不包含觀戰者與管理員)指令數量的物品",
                    " ",
                    "&e&l本教學已告一段落。"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return TutorialSection.END_TUTORIAL;
        }
    }
}
