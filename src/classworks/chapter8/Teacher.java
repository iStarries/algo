package classworks.chapter8;

public class Teacher extends Person{
    private double salary;

    public Teacher(String name, int age, double salary) {
        super(name, age);
        this.salary = salary;
    }

    public String say(){
        return super.say() + "\t工资：" + salary;
    }
    public void teach(){
        System.out.println("teach方法");
    }
}
