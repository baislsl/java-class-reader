package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;

public abstract class ConstantPool {
    public final static int TAG_SIZE = 1;
    protected int tag;

    public ConstantPool(int tag) {
        this.tag = tag;
    }

    public static ConstantPoolBuilder getConstantPoolBuilder(int tag) throws DecompileException {
        switch (tag){
            case 7: return new ClassTag(tag);
            case 9: return new FieldrefTag(tag);
            case 10: return new MethodrefTag(tag);
            case 11: return new InterfaceMethodrefTag(tag);
            case 8: return new StringTag(tag);
            case 3: return new IntegerTag(tag);
            case 4: return new FLoatTag(tag);
            case 5: return new LongTag(tag);
            case 6: return new DoubleTag(tag);
            case 12: return new NameAndTypeTag(tag);
            case 1: return new Utf8Tag(tag);
            case 15: return new MethodHandleTag(tag);
            case 16: return new MethodTypeTag(tag);
            case 18: return new InvokeDynamicTag(tag);
            default: throw new DecompileException(
                    String.format("tag number of %d not match when building constant pool", tag)
            );
        }
    }

    public int getTag() {
        return tag;
    }

}
