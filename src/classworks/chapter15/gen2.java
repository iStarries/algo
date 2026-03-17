package classworks.chapter15;


import java.util.ArrayList;
import java.util.Comparator;

public class gen2 {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1", 1200, new MyDate<Integer>(1, 1, 1)));
        employees.add(new Employee("2", 2200, new MyDate<Integer>(2, 2, 2)));
        employees.add(new Employee("3", 3200, new MyDate<Integer>(3, 3, 3)));
        System.out.println(employees);
        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                int res1 = o2.getName().compareTo(o1.getName());
                if (res1 != 0){
                    return res1;
                }

                return res1;
            }
        });
    }

    static class MyDate<E> implements Comparable<MyDate> {
        private E year;
        private E month;
        private E day;

        public MyDate(E year, E month, E day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        public int compareTo(MyDate o) {
            return 0;
        }
    }

    static class Employee {
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

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", sal=" + sal +
                    ", date=" + date +
                    '}';
        }

        public Employee(String name, double sal, MyDate date) {
            this.name = name;
            this.sal = sal;
            this.date = date;
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


    }
}
