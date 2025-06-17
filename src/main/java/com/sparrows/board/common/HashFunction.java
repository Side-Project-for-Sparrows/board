package com.sparrows.board.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class HashFunction {
    public static String hash(String origin){
        return murmurHash3_32(origin,42);
    }

    private static String murmurHash3_32(String key, int seed) {
        byte[] data = key.getBytes(StandardCharsets.UTF_8);
        int length = data.length;
        int h1 = seed;
        int c1 = 0xcc9e2d51;
        int c2 = 0x1b873593;
        int r1 = 15;
        int r2 = 13;
        int m = 5;
        int n = 0xe6546b64;

        ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.remaining() >= 4) {
            int k1 = buffer.getInt();
            k1 *= c1;
            k1 = Integer.rotateLeft(k1, r1);
            k1 *= c2;

            h1 ^= k1;
            h1 = Integer.rotateLeft(h1, r2);
            h1 = h1 * m + n;
        }

        int k1 = 0;
        int remaining = buffer.remaining();
        if (remaining > 0) {
            for (int i = 0; i < remaining; i++) {
                k1 ^= (buffer.get() & 0xff) << (i * 8);
            }
            k1 *= c1;
            k1 = Integer.rotateLeft(k1, r1);
            k1 *= c2;
            h1 ^= k1;
        }

        h1 ^= length;
        h1 ^= (h1 >>> 16);
        h1 *= 0x85ebca6b;
        h1 ^= (h1 >>> 13);
        h1 *= 0xc2b2ae35;
        h1 ^= (h1 >>> 16);

        return Integer.toHexString(h1);
    }
}
