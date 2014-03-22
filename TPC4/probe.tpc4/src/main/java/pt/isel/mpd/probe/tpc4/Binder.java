package pt.isel.mpd.probe.tpc4;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Binder {

    public static Map<String, Object> getFieldsValues(Object o)
            throws IllegalArgumentException, IllegalAccessException {
        Map<String, Object> res = new HashMap<>();
        Field[] fs = o.getClass().getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            res.put(f.getName(), f.get(o));
        }
        return res;
    }

    public static <T> boolean bindField(T target, Field f, Object value)
            throws IllegalArgumentException, IllegalAccessException {

        Class<?> fType = f.getType();
        f.setAccessible(true);
        if (fType.isPrimitive()) {
            fType = f.get(target).getClass();
        }
        /*
         * Verifica se o tipo do campo (fType) é tipo base do tipo de fValue.
         * Nota: Tipo base inclui superclasses ou superinterfaces.
         */
        if (fType.isAssignableFrom(value.getClass())) {
            f.set(target, value);
            return true;
        }
        return false;
    }

    public static <T> T bindToFields(Class<T> klass, Map<String, Object> vals)
            throws InstantiationException, IllegalAccessException {
        T target = klass.newInstance();
        Field[] fields = klass.getDeclaredFields();
        for (Field f : fields) {
            String fName = f.getName();
            if (vals.containsKey(fName)) {
                bindField(target, f, vals.get(fName));
            }
        }
        return target;
    }

    private static <T> boolean bindProp(T target, Method m, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String mName = m.getName();
        if (mName.substring(0, 3).compareTo("set") != 0) {
            return false;
        }
        Class<?>[] paramsKlasses = m.getParameterTypes();
        if (paramsKlasses.length != 1) {
            return false;
        }
        Class<?> propType = WrapperUtilites.toWrapper(paramsKlasses[0]);
        if (propType.isAssignableFrom(value.getClass())) {
            m.setAccessible(true);
            m.invoke(target, value);
            return true;
        }
        return false;
    }

    public static <T> T bindToProps(Class<T> klass, Map<String, Object> vals)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (klass == null || vals == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> aux = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        aux.putAll(vals);
        vals = aux;

        T target = klass.newInstance();
        Method[] ms = klass.getMethods();
        for (Method m : ms) {
            String propName = m.getName().substring(3);
            if (vals.containsKey(propName)) {
                bindProp(target, m, vals.get(propName));
            }
        }
        return target;
    }

    /*
     Adicione à classe Binder o método estático <T> T bindToFieldsAndProps(Class<T> klass, Map<String, Object> vals)
     que retorna uma instância de T cujas propriedades e campos são afectados com os valores dos pares de nome correspondente em vals 
     -- só devem ser afectadas as propriedades ou campos com o nome igual ao do par e tipo compatível com o valor do par.
     Se existir uma propriedade e campo com o mesmo nome, então a propriedade prevalece sobre o campo.
     NOTA: deve reestrutar o codigo da class Binder de modo não repetir codigo entre os seus métodos.
     */
    public static <T> T bindToFieldsAndProperties(Class<T> klass, Map<String, Object> vals)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        if (klass == null || vals == null) {
            throw new IllegalArgumentException();
        }
        T target = klass.newInstance();
        for (Map.Entry<String, Object> e : vals.entrySet()) {
            boolean foundSetter = false;
            for (Method m : klass.getDeclaredMethods()) {
                if (m.getName().compareToIgnoreCase("set" + e.getKey()) == 0) {
                    if (bindProp(target, m, e.getValue())) {
                        foundSetter = true;
                        break;
                    }
                }
            }
            if (!foundSetter) {
                for (Field f : klass.getDeclaredFields()) {
                    if (f.getName().compareToIgnoreCase(e.getKey()) == 0) {
                        bindField(target, f, vals.get(f.getName()));
                        break;
                    }
                }
            }
        }
        return target;
    }

}

class WrapperUtilites {

    final static Map<Class<?>, Class<?>> wrappers = new HashMap<>();

    static {
        wrappers.put(boolean.class, Boolean.class);
        wrappers.put(short.class, Short.class);
        wrappers.put(boolean.class, Boolean.class);
        wrappers.put(int.class, Integer.class);
    }

    public static Class<?> toWrapper(Class<?> c) {
        return c.isPrimitive() ? wrappers.get(c) : c;
    }

}
