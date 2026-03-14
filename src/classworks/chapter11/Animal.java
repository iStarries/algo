package classworks.chapter11;

public abstract class Animal {
    public abstract void shout();
}
class Dog extends Animal{
    @Override
    public void shout() {
        System.out.println("狗叫");
    }
}
class Cat extends Animal{
    @Override
    public void shout() {
        System.out.println("猫叫");
    }
}
class TestAnimal{
    public static void main(String[] args) {
        new Dog().shout();
        new Cat().shout();
    }
}