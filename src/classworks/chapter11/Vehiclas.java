package classworks.chapter11;

interface Vehicles {
    void work();
}
class Boat implements Vehicles{
    @Override
    public void work() {
        System.out.println("坐船");
    }
}
class Horse implements Vehicles{
    @Override
    public void work() {
        System.out.println("坐马");
    }
}
class toolsBox{
    public String road;

    public toolsBox(String road) {
        this.road = road;
    }
    public Vehicles getVehicles(){
        if(road.equals("he")){
            return new Boat();
        }else{
            return new Horse();
        }
    }
}
class Person{
    public String road;
    Vehicles v;
    public Person(String road) {
        this.road = road;
    }
    public void f1(){
        v = new toolsBox(road).getVehicles();
        v.work();
    }

}
class TestVehicles{
    public static void main(String[] args) {
        new Person("h").f1();
    }
}

