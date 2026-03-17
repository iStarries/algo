package classworks.chapter15;

import java.util.HashMap;
import java.util.Map;

public class homework01 {
    public static void main(String[] args) {

    }
}
class DAO <T>{
    private Map<String, T> map = new HashMap<>();

    public void save(String id, T entity){
        map.put(id, entity);
    }

}

class user{
    private int id;
    private int age;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public user(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
}
