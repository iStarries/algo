package classworks.chapter8;

public class NormalEmployee extends Employee {
    private int num = 333;

//    public int getNum() {
//        return num;
//    }

//    public void setNum(int num) {
//        this.num = num;
//    }

    public NormalEmployee(String name, double salary, int num) {
        super(name, salary);
    }

    public String work() {
        return getName() + "在工作";
    }

    public double calSalary() {
        return 12 * getSalary();
    }

    public void info() {
        System.out.println("name" + getName());
    }
}
