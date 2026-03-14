package classworks.chapter8;

public class Test {
    public double calSalaryAll(Employee employee) {
//        employee.getBonus();
        return employee.calSalary();
    }

    public void testWork(Employee employee) {
        if (employee instanceof NormalEmployee) {
            System.out.println(((NormalEmployee) employee).work());;
        }
    }

}
