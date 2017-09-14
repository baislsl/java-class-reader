package com.baislsl.decompiler.javap;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.javap.descriptor.Descriptor;
import com.baislsl.decompiler.javap.descriptor.FieldDescriptor;
import com.baislsl.decompiler.javap.descriptor.MethodDescriptor;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.*;
import com.baislsl.decompiler.structure.constantPool.*;

import java.util.ArrayList;
import java.util.List;

public class Javap {

    public static String getMethodDescription(Result result, Method method)
            throws DecompileException {
        StringBuilder ans = new StringBuilder();
        String[] accFlags = getAccessFlagDescription(method.getAccessFlag());
        String descriptorInfo = result.getUTF8Info(method.getDescriptorIndex());
        MethodDescriptor methodDescriptor = new MethodDescriptor(descriptorInfo);
        String name = result.getUTF8Info(method.getNameIndex());

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

        for (Attribute attribute : method.getAttributes()) {
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
                ans.append(result.getUTF8Info(parameter.nameIndex));
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

                ConstantPool cp = result.getConstantPool(index);
                if (!(cp instanceof ClassTag)) {
                    throw new DecompileException("Exception not found");
                }
                ans.append(result.getUTF8Info(((ClassTag) cp).getNameIndex())
                        .replaceAll("/", "."));
            }
        }

        return ans.toString();
    }

    public static String getFieldDescription(Result result, Field field)
            throws DecompileException {
        String[] accFlags = getAccessFlagDescription(field.getAccessFlag());
        String descriptorInfo = result.getUTF8Info(field.getDescriptorIndex());
        Descriptor descriptor = FieldDescriptor.getFieldDescriptors(descriptorInfo)[0];
        String name = result.getUTF8Info(field.getNameIndex());

        StringBuilder ans = new StringBuilder();

        for (String accFlag : accFlags) {
            ans.append(accFlag);
            ans.append(" ");
        }

        ans.append(descriptor.toString());
        ans.append(" ");
        ans.append(name);

        /**
         * each attributes of field must be one of following attributes:
         *      ConstantValue
         *      Synthetic
         *      Deprecated
         *      RuntimeVisibleAnnotations
         *      RuntimeInvisibleAnnotations
         *      RuntimeVisibleTypeAnnotations
         *      RuntimeInvisibleTypeAnnotations
         *  for decompile, we not just solve ConstantValue and Deprecated attributes
         */
        for (Attribute attribute : field.getAttributes()) {
            if (attribute instanceof ConstantValueAttr) {
                int index = ((ConstantValueAttr) attribute).getConstantValueIndex();
                ConstantPool cp = result.getConstantPool(index);
                ans.append(" = ");
                if (cp instanceof StringTag) {
                    ans.append(result.getUTF8Info(((StringTag) cp).getStringIndex()));
                } else {
                    // cp must be in DoubleTag, LongTag, FloatTag, IntegerTag
                    ans.append(cp.toString());
                }
            } else if (attribute instanceof DeprecatedAttr) {
                ans.insert(0, "@Deprecated ");
            }
        }

        return ans.toString();
    }


    public static String[] getAccessFlagDescription(int accessFlag) {
        List<String> ans = new ArrayList<>();
        int cur = 1, cnt = 16;
        for (int i = 0; i < cnt; i++) {
            if ((accessFlag & cur) != 0) {
                ans.add(Constants.accessName[i]);
            }
            cur <<= 1;
        }
        return ans.toArray(new String[0]);
    }


    public static String getConstantPoolInfo(ConstantPool cp, Result clazz)
            throws DecompileException {
        String[] cpDescription = cp.description(clazz);
        StringBuilder ans = new StringBuilder();
        if (cpDescription.length <= 3) {
            for (String s : cpDescription) {
                ans.append(s);
                ans.append("  ");
            }
        } else {
            ans.append(cpDescription[0]);
            ans.append("  ");
            ans.append(cpDescription[1]);
            ans.append("  ");
            for (int i = 2; i < cpDescription.length; i++) {
                ans.append(cpDescription[i]);
                if (i != cpDescription.length - 1) ans.append(":");
            }
        }
        return ans.toString();
    }


    public static String getClassName(Result clazz) throws DecompileException {
        int classIndex = clazz.getThisClass();
        ConstantPool classInfo = clazz.getConstantPool(classIndex);
        if (!(classInfo instanceof ClassTag))
            throw new DecompileException("Error format of class info");
        int index = ((ClassTag) classInfo).getNameIndex();
        return clazz.getUTF8Info(index);
    }

    /**
     * return direct super class name
     * for class return null if no super class
     * for interface, return null, too
     *
     * @param clazz
     * @return super class name
     * @throws DecompileException
     */
    public static String getSuperClassName(Result clazz) throws DecompileException {
        if (isInterface(clazz)) return null;

        int superIndex = clazz.getSuperClass();
        if (superIndex == 0) return null;
        ConstantPool classInfo = clazz.getConstantPool(superIndex);
        if (!(classInfo instanceof ClassTag))
            throw new DecompileException("Error format of class info");
        int index = ((ClassTag) classInfo).getNameIndex();
        return clazz.getUTF8Info(index);
    }

    public static String[] getInterfacesName(Result clazz) throws DecompileException {
        int count = clazz.getInterfacesCount();
        String[] ans = new String[count];
        int[] interfaceIndex = clazz.getInterfaces();
        for (int i = 0; i < count; i++) {
            ConstantPool interfaceInfo = clazz.getConstantPool(interfaceIndex[i]);
            if (!(interfaceInfo instanceof ClassTag))
                throw new DecompileException("Error format of class info");
            int nameIndex = ((ClassTag) interfaceInfo).getNameIndex();
            ans[i] = clazz.getUTF8Info(nameIndex);
        }
        return ans;
    }


    public static int getAccessFlag(Result clazz) {
        return clazz.getAccessFlag();
    }

    public static boolean isPublic(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_PUBLIC) != 0;
    }

    public static boolean isFinal(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_FINAL) != 0;
    }

    public static boolean isSuper(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_FINAL) != 0;
    }

    public static boolean isClass(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_INTERFACE) == 0;
    }

    public static boolean isInterface(Result clazz) {
        return !isClass(clazz);
    }

    public static boolean isAbstract(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_ABSTRACT) != 0;
    }

    public static boolean isSynthetic(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_SYNTHETIC) != 0;
    }

    public static boolean isAnnotation(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_ANNOTATION) != 0;
    }

    public static boolean isEnum(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_ENUM) != 0;
    }
}
