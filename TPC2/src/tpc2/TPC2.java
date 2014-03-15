package tpc2;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/** AUTOIDENT : Alt+Shift+F */

/**
 *
 * @author Cadete
 */
public class TPC2 {

    static class Student {

        int id;
        char c = 'c';
        final private int avgGrade = 20;
        String name;
        protected int totalGrades = 20;

        Student(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class StudentDTO {

        String name;
        int id;
        char xpto;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Student s = new Student(35383, "Cadete");
        Map<String, Object> map = Binder.getFieldsValues(s);
        StudentDTO sdto = Binder.bindTo(StudentDTO.class, map);
        System.out.println(sdto.id + " - " + sdto.name);
    }
}

class Binder {

    static Map<String, Object> getFieldsValues(Object o) throws IllegalAccessException {
        Map<String, Object> ret = new HashMap();
        if (o != null) {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field fld : fields) {
                if (!fld.isAccessible()) {
                    fld.setAccessible(true);
                }
                ret.put(fld.getName(), fld.get(o));
            }
        }
        return ret;
    }
   
    static <T> T bindTo(Class<T> klass, Map<String, Object> fieldsVals) throws InstantiationException, IllegalAccessException {
        T target = klass.newInstance();
        for (Field klassfld : klass.getDeclaredFields()) {
                Object value = fieldsVals.get(klassfld.getName());
                 /*fieldsVals.get: Returns the value to which the specified key is mapped, 
                 or null if this map contains no mapping for the key.*/
                if(value != null){
                    Class type = klassfld.getType();
                    if(type.isPrimitive()) 
                        type = Utils.getPrimitiveWrappedClass(type);
                    if(type.isAssignableFrom(value.getClass())) 
                        klassfld.set(target, value);
                }
        }
        return target;
    }
}
