package classworks.chapter8;

public class Person {
    private String name;
    private int age;
    private String job;

    public Person(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String say(){
        return "名字：" + name + "\t年龄：" + age;
    }
}
