package probe;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tpc2.Utils;

public class App {

    final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) 
            throws ParseException, IllegalAccessException, InstantiationException
    {
        Student s1 = new Student(31531, sdf.parse("05-6-1994"), "Jose Cocacola", null);
        Map<String, Object> s1fields = Binder.getFieldsValues(s1);

        StudentDto s2 = Binder.bindTo(StudentDto.class, s1fields);
        assert s2.id == s1.id;
        assert s2.name.equals(s1.name);
        assert s2.birthDate == null;
    }
}

class StudentDto {

    int id;
    String birthDate;
    String name;
}

class Grade {
}

class Course {
}

class Student {

    final int id;
    final Date birthDate;
    final List<Grade> grades;
    final String name;
    final Course course;

    public Student(int id, Date birthDate, String name, Course course) {
        this.id = id;
        this.birthDate = birthDate;
        this.name = name;
        this.course = course;
        this.grades = new LinkedList<>();
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
                /*map.get(name): Returns the value to which the specified key is mapped, 
                                 or null if this map contains no mapping for the key. */
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
