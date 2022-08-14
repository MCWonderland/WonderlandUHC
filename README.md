# WonderlandUHC

一款支援 1.8-1.16.5 的 UHC 插件

![Marketing Seminar Facebook Cover](https://user-images.githubusercontent.com/41278925/184468277-25183ff3-07bd-4d61-9792-0045d36a0fcd.png)


## 專案故事
前身為 **魯大頭UHC**，於 2019 年以付費形式發布，一共累積客戶 25+ 位。

如今看來，雖然 PvP 市場的人數不減，但已從當時大而密集的活動形式，轉變成多為朋友圈內組團遊玩的休閒風格，因此決定開源此專案，讓任何玩家都能成為主持人，跟朋友一起玩 UHC！

## 安裝步驟
**!! 須知：由於插件剛經過大量整合，目前穩定度並不高。若遊玩過程發現 Bug，請[依照此處說明回報](https://github.com/MCWonderland/WonderlandUHC#%E5%9B%9E%E5%A0%B1-bug)。 !!**

1. 至 [release](https://github.com/MCWonderland/WonderlandUHC/releases) 處下載最新插件檔案
2. 參考教學影片系列安裝插件 [連結](https://www.youtube.com/playlist?list=PL86KqzDo7_n0Vkthb9I85hfS390i-4NPb)
3. 開心遊玩！

## 回報 Bug
麻煩至 [issue](https://github.com/MCWonderland/WonderlandUHC/issues) 處回報 Bug，麻煩在回報過程請盡量提供完整敘述，若能**附上圖片**輔助說明，將能加速我們開發者更快抓到問題來源！

## 協作 (Contribute)
本專案使用 IntelliJ 開發，歡迎有興趣協作的開發者 Fork 此專案一起讓它變得更好。

**非常歡迎大家踴躍 PR！**有任何意見交流，歡迎到我們的 Discord 聊聊，也可以直接私訊我。

### 交流平台相關資訊

Discord 群組連結: https://discord.gg/vTnEaX37Nr

群組內屬於開發者的文字聊天頻道: [點我加入](https://discord.com/channels/574167124579319809/1007964615050788924)

我的 Discord ID: `魯魯#5475`

### 專案使用的 Framework
這是在 2019 年開始的專案，當時因為方便選用了 Foundation 這個 Library 來開發，可惜框架一直變化，一年沒關注後，框架 API 的改版也短時間讓這專案很難跟上最新版本。

專案使用的 Foundation，我已令公開於 [獨立 Repo](https://github.com/MCWonderland/lib-foundation) 有興趣或需要的開發者歡迎去看看。

### 非必要時，不要再使用 Foundation 的內容
專案的未來，我想盡量讓他脫離框架，不僅對於不熟悉 Foundation 的開發者來說比較方便，也不會再出現上述問題。

目前專案還在重構當中，再麻煩所有開發者，盡量額外創建 Util Class 取代直接存取框架的函式。
