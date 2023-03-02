package xyz.rh.common.eventpublisher;

/**
 * Created by rayxiong on 2022/11/12.
 */

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericHelper {

    /**
     * 通过反射的方式,获取到某个类型的泛型信息
     *
     * @param clazz    具体类型
     * @param topClazz 最上层的泛型类型
     * @param index    泛型参数的索引位置
     * @return
     */
    public static Class<? extends Object> getGenericTypeArgument(Class clazz, Class topClazz, final int index) {
        if (clazz == null) {
            return null;
        }
        /** 从父类查询泛型信息*/
        Class genericClass = getSupperGenericType(topClazz, index, clazz.getGenericSuperclass());
        if (genericClass != null) {
            return genericClass;
        }
        /** 从父接口查询泛型信息*/
        genericClass = getSupperGenericType(topClazz, index, clazz.getGenericInterfaces());
        if (genericClass != null) {
            return genericClass;
        }

        /** 都没有找到, 查找到父类,继续查找*/
        return getGenericTypeArgument(getParentTypeClass(clazz, topClazz), topClazz, index);
    }

    private static Class<? extends Object> getParentTypeClass(Class clazz, Class topClazz) {
        /** 先看父类型*/
        Class parent = clazz != null ? clazz.getSuperclass() : null;
        if (parent != null && topClazz.isAssignableFrom(parent)) {
            return parent;
        }

        Class[] interfaces = clazz != null ? clazz.getInterfaces() : null;
        int len = interfaces != null ? interfaces.length : 0;
        for (int i = 0; i < len; i++) {
            if (topClazz.isAssignableFrom(interfaces[i])) {
                return interfaces[i];
            }
        }
        return null;
    }

    private static Class getSupperGenericType(Class topClazz, int index, Type... types) {
        int len = types != null ? types.length : 0;
        for (int i = 0; i < len; i++) {
            /** 不是泛型类型,处理下一个 */
            if (!(types[i] instanceof ParameterizedType)) {
                continue;
            }
            /** 泛型类型不是一个类,不做处理 */
            ParameterizedType pType = (ParameterizedType) types[i];
            if (!(pType.getRawType() instanceof Class)) {
                continue;
            }
            /** 如果不是BaseInterfac的类型或子类型,处理下一个 */
            Class rawClazz = (Class) pType.getRawType();
            if (!(topClazz.isAssignableFrom(rawClazz))) {
                continue;
            }
            /** 泛型索引信息不正确,处理下一个 */
            Type[] params = pType.getActualTypeArguments();
            if (index < params.length && index >= 0 && (params[index] instanceof Class)) {
                return (Class) params[index];
            } else {
                break;
            }
        }

        return null;
    }
}
