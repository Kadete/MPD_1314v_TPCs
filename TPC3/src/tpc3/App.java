package tpc3;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class App {

    final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args)
            throws ParseException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        /*
         Arrange
         */
        Student s1 = new Student(31531, sdf.parse("05-6-1994"), "Jose Cocacola", null);
        Map<String, Object> s1fields = Binder.getFieldsValues(s1);
        /*
         Act
         */
        StudentDto s2 = Binder.bindTo(StudentDto.class, s1fields);
        System.out.println(s2);

        /*
         if(s2.id != s1.id){
         throw new IllegalStateException();
         }
         */
        assert s2.id == s1.id;
        assert s2.name.equals(s1.name);
        assert s1.birthDate.equals(s2.birthDate);

        StudentDto s = Binder.bindToProperties(StudentDto.class, Binder.getFieldsValues(s1));
        s.print();

    }
}

class StudentDto {

    int id;
    String birthDate;
    String name;

    private int getId() {
        return id;
    }

    public void setId(int _id) {
        System.out.println(">>>>setId");
        id = _id;
    }

    public void setName(String _name) {
        System.out.println(">>>>setName");
        name = _name;
    }

    public String getName() {
        return name;
    }

    public String getN() {
        return name;
    }

    void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "StudentDto{" + "id=" + getId() + ", birthDate=" + birthDate + ", name=" + getName() + '}';
    }

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
