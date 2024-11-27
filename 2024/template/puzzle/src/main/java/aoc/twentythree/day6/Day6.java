package aoc.twentythree.day6;

import java.math.BigInteger;

public class Day6 {

    public static long getDistance(long maxTime, long pushDuration) {
        long remainingTime = maxTime - pushDuration;
        return pushDuration * remainingTime;
    }

    public static BigInteger getDistanceBigInt(long maxTime, long pushDuration) {
        long remainingTime = maxTime - pushDuration;
        return BigInteger.valueOf(pushDuration).multiply(BigInteger.valueOf(remainingTime));
    }

    public static long getWinningCount(long maxTime, long recordDistance) {
        long ways = 0;
        for (long duration = 0; duration <= maxTime; duration++) {
            long distance = getDistance(maxTime, duration);
            if (distance > recordDistance) {
                ++ways;
            }
        }
        return ways;
    }

    public static long getWinningCount(long maxTime, BigInteger recordDistance) {
        long ways = 0;
        for (long duration = 0; duration <= maxTime; duration++) {
            BigInteger distance = getDistanceBigInt(maxTime, duration);
            if (distance.compareTo(recordDistance) > 0) {
                ++ways;
            }
        }
        return ways;
    }
}
