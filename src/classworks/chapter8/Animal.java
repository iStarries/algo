package classworks.chapter8;

public class Animal {
    public String name;
    public int weight;

    public Animal(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

//    private void show(){
//        System.out.println("这是父类的show");
//    }
}
