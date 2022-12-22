package org.mcwonderland.uhc.model.broadcast;

import lombok.Builder;
import lombok.Getter;

/**
 * 2019-11-20 下午 08:25
 */

@Getter
@Builder
public class GameStartTimeInfo {
    private final String ip;
    private final String joinTime;
    private final String startTime;

    public GameStartTimeInfo(String ip, String joinTime, String startTime) {
        this.ip = ip;
        this.joinTime = joinTime;
        this.startTime = startTime;
    }
}
