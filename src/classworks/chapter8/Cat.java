package classworks.chapter8;

public class Cat extends Animal{
    public String action;
    public String name;

    public Cat(String name, int weight, String action, String name1) {
        super(name, weight);
        this.action = action;
        this.name = name1;
    }

    public String getAction() {
        return action;
    }
//    private void show(){
//        System.out.println("这是子类的show");
//    }
}
