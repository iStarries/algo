package classworks.chapter14;

import java.util.ArrayList;
import java.util.Objects;

public class chapterworks2 {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        Car car = new Car("1", 1);
        arrayList.add(car);
        arrayList.add(new Car("2", 2));
        car.setName("3333");
        arrayList.remove(car);
        System.out.println(arrayList);

//        arrayList.remove(1);
//        arrayList.remove(car);

//        arrayList.clear();
//        ArrayList arrayList1 = new ArrayList();
//        arrayList1.add(car);
//        arrayList1.add(car);
//        arrayList1.add(car);
//
//        arrayList.addAll(arrayList1);
//        arrayList1.remove(0);
//        System.out.println(arrayList.removeAll(arrayList1));
//        System.out.println(arrayList);
    }
}

class Car {
    private String name;
    private double price;

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Car(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(car.price, price) == 0 &&
                Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
