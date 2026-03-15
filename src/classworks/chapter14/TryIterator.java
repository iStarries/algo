package classworks.chapter14;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TryIterator {
    public static void main(String[] args) {
        Dog dog1 = new Dog("1", 1);
        Dog dog2 = new Dog("2", 2);
        Dog dog3 = new Dog("3", 3);
        List al = new ArrayList();
        al.add(dog1);
        al.add(dog2);
        al.add(dog3);
        for (Object o : al) {
            System.out.println(o);
        }
        Iterator iterator = al.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
}

class Dog {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
