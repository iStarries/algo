package classworks.chapter8;

public class Student extends Person{
    private int id;
    private double score;

    public Student(String name, int age, int id, double score) {
        super(name, age);
        this.id = id;
        this.score = score;
    }
    public String say(){
        return super.say() + "id：" + id + "\t分数：" + score;
    }
    public void study(){
        System.out.println("study方法");
    }
}
