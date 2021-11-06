package cn.edu.fudan.yummyuser.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisUtils {
    public static String concatKeys(Object... segments) {
        return Arrays.stream(segments).map(String::valueOf).collect(Collectors.joining(":"));
    }
}
