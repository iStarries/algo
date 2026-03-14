package classworks.chapter8;

public abstract class Employee {
    private String name;
    private double salary;
    private int num;

    public int getNum() {
        return num;
    }

//    public void setNum(int num) {
//        this.num = num;
//    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double calSalary() {
        return 12 * salary;
    }

    public abstract void info();
}
