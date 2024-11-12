package problem2.company;

public class PermanentWorker extends Worker{
    private int salary;
    public PermanentWorker(String name, int salary) {
        super(name);
        this.salary = salary;
    }
    public int getPay() {
        return salary;
    }
    public void showSalaryInfo(String name) {
        String salaryStr = String.format("%,d", getPay());
        System.out.printf("사원 %s의 급여는 %s원\n", name, salaryStr);
    }
}
