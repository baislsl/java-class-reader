package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.javap.Javap;
import com.baislsl.decompiler.javap.descriptor.Descriptor;
import com.baislsl.decompiler.javap.descriptor.FieldDescriptor;
import com.baislsl.decompiler.javap.descriptor.MethodDescriptor;
import com.baislsl.decompiler.structure.attribute.*;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.StringTag;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class Method extends FieldMethodBasic {
    private Method(int accessFlag, int nameIndex, int descriptorIndex,
                   Attribute[] attributes, ConstantPool[] constantPools) {
        super(accessFlag, nameIndex, descriptorIndex, attributes, constantPools);
    }

    public static Method build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        FieldMethodBasic basic = FieldMethodBasic.build(file, constantPools);
        return new Method(basic.getAccessFlag(), basic.getNameIndex(),
                basic.getDescriptorIndex(), basic.getAttributes(), constantPools);
    }

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder();
        String[] accFlags = Javap.getAccessFlagDescription(accessFlag);
        String descriptorInfo = constantPools[descriptorIndex].name();
        MethodDescriptor methodDescriptor = new MethodDescriptor(descriptorInfo);
        String name = constantPools[nameIndex].name();

        /**
         * each attribute of method must be one of following attributes:
         *      Code
         *      Exceptions
         *      RuntimeVisibleParameterAnnotations
         *      RuntimeInvisibleParameterAnnotations
         *      AnnotationDefault
         *      MethodParameters
         *      Synthetic
         *      Deprecated
         *      Signature
         *      RuntimeVisibleAnnotations
         *      RuntimeInvisibleAnnotations
         *      RuntimeVisibleTypeAnnotations
         *      RuntimeInvisibleTypeAnnotations
         */

        MethodParametersAttr parametersAttr = null;
        DeprecatedAttr deprecatedAttr = null;
        List<ExceptionsAttr> exceptionsAttrs = new ArrayList<>();
        List<CodeAttr> codeAttrs = new ArrayList<>();

        for (Attribute attribute : attributes) {
            if (attribute instanceof DeprecatedAttr) {
                deprecatedAttr = (DeprecatedAttr) attribute;
            } else if (attribute instanceof MethodParametersAttr) {
                parametersAttr = (MethodParametersAttr) attribute;
            } else if (attribute instanceof ExceptionsAttr) {
                exceptionsAttrs.add((ExceptionsAttr) attribute);
            } else if (attribute instanceof CodeAttr) {
                codeAttrs.add((CodeAttr) attribute);
            }
        }

        if (deprecatedAttr != null)
            ans.append("@Deprecated ");
        for (String accFlag : accFlags) {
            ans.append(accFlag);
            ans.append(" ");
        }

        ans.append(methodDescriptor.getReturnDescriptor().toString());
        ans.append(" ");
        ans.append(name);
        ans.append("(");
        Descriptor[] descriptors = methodDescriptor.getParamDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (i != 0) ans.append(" ,");
            if (parametersAttr != null) {
                Parameter parameter = parametersAttr.getParameters()[i];
                ans.append(" ");
                if ((parameter.accessFlag & Constants.ACC_FINAL) != 0) ans.append("final ");
                // if ((parameter.accessFlag & Constants.ACC_SYNTHETIC) != 0) ans.append("synthetic ");
                // if((parameter.accessFlag & Constants.ACC_MANDATED) != 0) ans.append("mandated ");
                ans.append(descriptors[i].toString());
                ans.append(constantPools[parameter.nameIndex].name());
            } else {
                ans.append(descriptors[i].toString());
            }
        }
        ans.append(") ");

        boolean hasException = false;
        for (ExceptionsAttr attribute : exceptionsAttrs) {
            for (int index : attribute.getExceptionIndexTable()) {
                if (!hasException) {
                    hasException = true;
                    ans.append(" throws ");
                } else {
                    ans.append(", ");
                }

                ConstantPool cp = constantPools[index];
                if (!(cp instanceof ClassTag)) {
                    throw new DecompileException("Exception not found");
                }
                ans.append(
                        constantPools[((ClassTag) cp).getNameIndex()]
                                .name()
                                .replaceAll("/", ".")
                );
            }
        }

        for (Attribute attribute : attributes) {
            ans.append("\n")
                    .append(attribute.getClass().getSimpleName())
                    .append(":\n")
                    .append(attribute.name())
                    .append("\n");
        }

        return ans.toString();
    }
}
