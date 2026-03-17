package classworks.chapter14;

import java.util.HashSet;
import java.util.Objects;

public class HashSet_ {
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        hashSet.add(new Employee("1", 12, new MyDate(1, 2, 2)));
        hashSet.add(new Employee("2", 122, new MyDate(2, 2, 2)));
        hashSet.add(new Employee("1", 132, new MyDate(1, 2, 2)));
        for (Object o : hashSet) {
            System.out.println(hashSet);
        }

    }
}

class Employee {
    private String name;
    private double sal;
    private MyDate date;
    private int id;

    public Employee(String name, double sal) {
        this.name = name;
        this.sal = sal;
    }

    public Employee(String name, double sal, int id) {
        this.name = name;
        this.sal = sal;
        this.id = id;
    }

    public Employee(String name, double sal, MyDate date) {
        this.name = name;
        this.sal = sal;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", sal=" + sal +
                ", date=" + date +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                date.equals(employee.date);
    }

}

class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDate myDate = (MyDate) o;
        return year == myDate.year &&
                month == myDate.month &&
                day == myDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
