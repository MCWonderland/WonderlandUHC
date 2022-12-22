package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use to check versions.
 * <p>
 * Requires format: {version}-{life cycle}-{cycle version}
 * Examples:
 * 2.0.1
 * 2.0.1-beta-2
 * 2.0
 * 2.0.1-alpha-5
 * <p>
 * Allowed Life Cycle Names(ignored cases):
 * PRE-ALPHA
 * ALPHA
 * BETA
 */
@UtilityClass
public class VersionComparator {

    public boolean isAtLeast(String current, String toCompare) {
        return compare(current, toCompare) >= 0;
    }

    public boolean isNewerThan(String current, String toCompare) {
        return compare(current, toCompare) > 0;
    }

    public int compare(String versionA, String versionB) {
        return compareVersion(toNumbers(versionA), toNumbers(versionB));
    }

    private int compareVersion(List<Integer> currentVersion, List<Integer> newestVersion) {
        int runs = Math.max(currentVersion.size(), newestVersion.size());

        for (int i = 0; i < runs; i++) {
            int currVar = getInt(currentVersion, i);
            int newVar = getInt(newestVersion, i);

            if (newVar == currVar)
                continue;

            return Integer.compare(currVar, newVar);
        }

        return 0;
    }

    private int getInt(List<Integer> list, int index) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return 0;
        }
    }

    private List<Integer> toNumbers(String version) {
        String formattedString = formatVersion(version);

        try {
            return Arrays.stream(formattedString.split("[.]"))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ex) {
            return Arrays.asList(-1);
        }
    }

    @NotNull
    private String formatVersion(String version) {
        return version.toUpperCase()
                .replace("-", ".")
                .replace("PRE-ALPHA", "-3")
                .replace("ALPHA", "-2")
                .replace("BETA", "-1")
                // 移除不包含數字 (正/負數) 的字元
                // \d -> 數字
                // -? -> 讓系統也可以偵測負數 (是否包含一次負號)
                // + -> 前方regex(\d)包含 1 次以上 (讓系統偵測 36 時選取的範圍是 "36" 而不是 "3" 跟 "6")
                .replaceAll("[^-?\\d+. ]", "")
                .replaceAll("\\s+", ".");
    }
}
