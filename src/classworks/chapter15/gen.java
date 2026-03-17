package classworks.chapter15;

import java.util.HashMap;
public class gen {
    public static void main(String[] args) {
        HashMap<String, Student<String>> stringStudentHashMap = new HashMap<>();
        stringStudentHashMap.put("abc", new Student<>("abc"));
        System.out.println(stringStudentHashMap);
    }
}
class Student<E>{
    private E name;
    public Student(E name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
