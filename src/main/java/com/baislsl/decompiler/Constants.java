package com.baislsl.decompiler;

public final class Constants {
    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_SYNCHRONIZED = 0x0020;
    public static final int ACC_SUPER = 0x0020;
    public static final int ACC_BRIDGE = 0x0040;
    public static final int ACC_VOLATILE = 0x0040;
    public static final int ACC_VARARGS = 0x0080;
    public static final int ACC_TRANSIENT = 0x0080;
    public static final int ACC_NATIVE = 0x0100;
    public static final int ACC_INTERFACE = 0x0200;
    public static final int ACC_ABSTRACT = 0x0400;
    public static final int ACC_STRICT = 0x0800;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ANNOTATION = 0x2000;
    public static final int ACC_ENUM = 0x4000;
    public static final int ACC_MANDATED = 0x8000;


    public final static String[] CLASS_ACC_TABLE = {
            "public", "final", "interface", "abstract",
            "annotation", "enum"
    };
    public final static String[] FIELD_ACC_TABLE = {
            "public", "private", "protected", "static",
            "final", "volatile", "transient", "synthetic", "enum"
    };
    public final static String[] METHOD_ACC_TABLE = {
            "public", "private", "protected", "static",
            "final", "synchronized", "native", "abstract"
    };

    private static String nameToFieldName(String name){
        return "ACC_" + name.toUpperCase();
    }

    private static String fieldNameToName(String fieldName){
        return fieldName.substring("ACC_".length()).toLowerCase();
    }

    public static int getInt(String fieldName){
        try {
            java.lang.reflect.Field field = Constants.class.getField(nameToFieldName(fieldName));
            return field.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // This should be impossible to happen
            throw new RuntimeException("Something error when decode access flag of " + fieldName);
        }
    }
}
