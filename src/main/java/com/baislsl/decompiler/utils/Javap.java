package com.baislsl.decompiler.utils;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.Method;

import java.util.*;
import java.util.stream.Stream;

import static com.baislsl.decompiler.Constants.*;

public class Javap {
    private static Map<Class<?>, String[]> map = new HashMap<>();

    static {
        map.put(Method.class, METHOD_ACC_TABLE);
        map.put(Field.class, FIELD_ACC_TABLE);
        map.put(Result.class, CLASS_ACC_TABLE);
    }

    private static String[] getAccessFlagDescription(int accessFlag, String[] table) {
        List<String> ans = new ArrayList<>();
        Stream.of(table).forEach(
                (s) -> {
                    if ((accessFlag & getInt(s)) != 0) ans.add(s);
                }
        );
        return ans.toArray(new String[0]);
    }

    /**
     * @param accessFlag access flag to decode
     * @param type       be in Method.class, Field.class and Result.class
     * @return
     */
    public static String[] getAccessFlagDescription(int accessFlag, Class<?> type) {
        return getAccessFlagDescription(accessFlag, map.get(type));
    }

    /**
     * @param accessFlag access flag to decode
     * @param type       be in Method.class, Field.class and Result.class
     * @return
     */
    public static String accessClassFlagDescription(int accessFlag, Class<?> type) {
        String[] description = getAccessFlagDescription(accessFlag, type);
        return Stream
                .of(description)
                .reduce((pre, cur) -> pre + " " + cur)
                .orElse("");
    }

}
