package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class TargetInfo implements Builder {

    public static TargetInfo getTargetInfo(DataInputStream file, int targetType) throws DecompileException {
        TargetInfo targetInfo = getTargetInfoBuilder(targetType);
        targetInfo.build(file);
        return targetInfo;
    }

    private static TargetInfo getTargetInfoBuilder(int targetType) throws DecompileException {
        switch (targetType) {
            case 0x00:
            case 0x01:
                return new TypeParameterTarget();
            case 0x10:
                return new SupertypeTarget();
            case 0x11:
            case 0x12:
                return new TypeParameterBoundTarget();
            case 0x13:
            case 0x14:
            case 0x15:
                return new EmptyTarget();
            case 0x16:
                return new FormalParameterTarget();
            case 0x17:
                return new ThrowsTarget();
            case 0x40:
            case 0x41:
                return new LocalvarTarget();
            case 0x42:
                return new CatchTarget();
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46:
                return new OffsetTarget();
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4A:
            case 0x4B:
                return new TypeArgumentTarget();
            default:
                throw new DecompileException(
                        String.format("target type of value %d not match for target info", targetType)
                );
        }
    }
}

class TypeParameterTarget extends TargetInfo {
    private static final int TYPE_PARAMETER_INDEX_SIZE = 1;
    private int typeParameterIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        typeParameterIndex = Read.readBytes(file, TYPE_PARAMETER_INDEX_SIZE);
    }

    public int getTypeParameterIndex() {
        return typeParameterIndex;
    }
}

class SupertypeTarget extends TargetInfo {
    private static final int SUPERTYPE_INDEX_SIZE = 2;
    private int supertypeIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        supertypeIndex = Read.readBytes(file, SUPERTYPE_INDEX_SIZE);
    }

    public int getSupertypeIndex() {
        return supertypeIndex;
    }
}

class TypeParameterBoundTarget extends TypeParameterTarget {
    private static final int BOUND_INDEX_SIZE = 1;
    private int boundIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        super.build(file);
        boundIndex = Read.readBytes(file, BOUND_INDEX_SIZE);
    }
}

class EmptyTarget extends TargetInfo {
    @Override
    public void build(DataInputStream file) throws DecompileException {

    }
}

class FormalParameterTarget extends TargetInfo {
    private static final int INDEX_SIZE = 1;
    private int formalParameterIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        formalParameterIndex = Read.readBytes(file, INDEX_SIZE);
    }

    public int getFormalParameterIndex() {
        return formalParameterIndex;
    }
}

class ThrowsTarget extends TargetInfo {
    private static final int TYPE_INDEX_SIZE = 2;
    private int throwsTypeIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        throwsTypeIndex = Read.readBytes(file, TYPE_INDEX_SIZE);
    }

    public int getThrowsTypeIndex() {
        return throwsTypeIndex;
    }
}


class LocalvarTarget extends TargetInfo {
    private class Table {
        public int startPC, length, index;
        public static final int SIZE = 2;
    }

    private final static int TABLE_LENGTH_SIZE = 2;
    private Table[] tables;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        int tableSize = Read.readBytes(file, TABLE_LENGTH_SIZE);
        tables = new Table[tableSize];
        for (int i = 0; i < tableSize; i++) {
            tables[i] = new Table();
            tables[i].startPC = Read.readBytes(file, Table.SIZE);
            tables[i].length = Read.readBytes(file, Table.SIZE);
            tables[i].index = Read.readBytes(file, Table.SIZE);
        }
    }

    public Table[] getTables() {
        return tables;
    }
}

class CatchTarget extends TargetInfo {
    private static final int INDEX_SIZE = 2;
    private int exceptionTableIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        exceptionTableIndex = Read.readBytes(file, INDEX_SIZE);
    }

    public int getExceptionTableIndex() {
        return exceptionTableIndex;
    }
}

class OffsetTarget extends TargetInfo {
    private static final int OFFSET_SIZE = 2;
    private int offset;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offset = Read.readBytes(file, OFFSET_SIZE);
    }

    public int getOffset() {
        return offset;
    }
}

class TypeArgumentTarget extends TargetInfo {
    private static final int OFFSET_SIZE = 2;
    private static final int INDEX_SIZE = 1;

    private int offset;
    private int index;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offset = Read.readBytes(file, OFFSET_SIZE);
        index = Read.readBytes(file, INDEX_SIZE);
    }

    public int getOffset() {
        return offset;
    }

    public int getIndex() {
        return index;
    }
}


