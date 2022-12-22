package org.mcwonderland.uhc.model.tutorial;

import org.mcwonderland.uhc.model.tutorial.model.Tutorial;
import org.mcwonderland.uhc.model.tutorial.model.TutorialSection;
import org.bukkit.entity.Player;

public class UHCConfigTutorial extends Tutorial {

    public UHCConfigTutorial(Player player) {
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
                    "&6&l歡迎來到WonderlandUHC教學 - 設定部分",
                    " ",
                    "以下將會教你如何從頭開始，完整設定出屬於自己的UHC。",
                    "在進行過程中，你隨時可以輸入 &b&lexit &f來停止教學",
                    " ",
                    "&e如果你已經準備好了，請輸入任意字元前往下個教學…"
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new EditorMenuTutorialSection();
        }
    }

    private class EditorMenuTutorialSection extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "首先，請透過輸入 &e/uhc edit &f來開啟設定介面。",
                    " ",
                    "你會發現，設定介面有許多功能",
                    "請你花個時間，&6由左上往右下一一點擊每個物品&f，並查看其功能使用方式。",
                    " ",
                    "了解插件的使用方式&6&l相當重要&f，在嘗試的過程來了解如何使用插件，&b&l日後遊戲主持起來方能得心應手",
                    "還請你在此階段要&d&l保持耐心&f，仔細的閱讀每個物品的說明文字並嘗試調整看看。",
                    " ",
                    "&e了解每個設定物品的用途後，請輸入任意字元前往下個教學..."
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new AnyQuestions();
        }
    }

    private class AnyQuestions extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&9到這裡有任何問題嗎?",
                    " ",
                    "如果不是很了解某些功能如何使用，歡迎至&b插件Discord社群&f向他人尋求協助。",
                    "連結: &3https://discord.gg/pJ3RkP5",
                    " ",
                    "&e加入社群後，請輸入任意字元前往下個教學..."
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new TemplateTutorialSection();
        }
    }

    private class TemplateTutorialSection extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "接著，這邊想跟你隆重介紹設定頁面裡的&e&l模板功能",
                    " ",
                    "如同該設定物品的說明文字所述，此功能可以&a儲存你設定好的模板&f，並於日後&a一鍵載入現在的設定&f。",
                    "如果你還未嘗試過，不仿現在使用看看吧！",
                    " ",
                    "先使用 &e/uhc edit &f，再&e點擊模板物品&f來進入頁面",
                    "最後點擊介面左下角的&2另存新檔&f物品來儲存模板。",
                    " ",
                    "&e嘗試過模板功能後，請輸入任意字元前往下個教學..."
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new CreatingWorldTutorialSection();
        }
    }

    private class CreatingWorldTutorialSection extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&e不錯！到目前為止你已經設定完你的第一場遊戲了",
                    " ",
                    "然而你現在所在的地方是&c大廳世界&f，並不能拿來玩UHC。",
                    "所以請你&b點擊設定頁面右下角的物品&f，或是使用 &e/uhc regen &f來創建遊戲世界。",
                    " ",
                    "&e世界創建完畢後，請輸入任意字元前往下個教學..."
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new FilterWorldTutorialSection();
        }
    }

    private class FilterWorldTutorialSection extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&6你滿意現在的世界嗎?",
                    " ",
                    "如果覺得中心點&c不夠平整，可以依照上面的文字所述，&6&l輸入指令重新生成世界&f，直到滿意為止。",
                    " ",
                    "&4&l注意！&c&l找到合適的世界後，別急著輸入指令載入地圖，這邊有些事情還沒交代給你，麻煩繼續把教學看完。",
                    " ",
                    "&e若已找到合適的世界，請輸入任意字元前往下個教學..."
            };
        }

        @Override
        protected TutorialSection nextSection() {
            return new PrepareForNextOne();
        }
    }

    private class PrepareForNextOne extends TutorialSection {

        @Override
        protected String[] getMessages() {
            return new String[]{
                    "&6&l恭喜你設定完成！現在可輸入指令開始生成地圖了。",
                    " ",
                    "屆時地圖載入完畢後，別忘了輸入 &b&l/uhc toturial host &f繼續下階段的教學！",
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
