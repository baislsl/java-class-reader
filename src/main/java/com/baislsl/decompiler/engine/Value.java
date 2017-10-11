package com.baislsl.decompiler.engine;

/**
 * value 不要直接修改name, used要达到类似的目标直接创建一个新的value对象
 */
public class Value implements Cloneable {
    // public Integer value;
    public final String name;
    public final Boolean used;

    public Value(String name) {
        this.name = name;
        this.used = true;
    }

    public Value(String name, boolean used) {
        this.name = name;
        this.used = used;
    }

    public Value(int value) {
        this.name = Integer.toString(value);
        this.used = true;
    }

    public Value(int value, boolean used) {
        this.name = Integer.toString(value);
        this.used = used;
    }

    @Override
    public String toString() {
        // TODO: 对于区分开long和int, double和float
        return name;
    }

}
