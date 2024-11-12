package problem2.company;

public class SalesWorker extends PermanentWorker {
    private int salesAmount;
    private double bonusRatio;

    public SalesWorker(String name, int salary, int salesAmount, double bonusRatio) {
        super(name, salary);
        this.salesAmount = salesAmount;
        this.bonusRatio = bonusRatio;
    }

    public int getPay() {
        return super.getPay() + (int)(salesAmount * bonusRatio);
    }

    public void showSalaryInfo(String name) {
        String salaryStr = String.format("%,d", super.getPay());
        String bonusStr = String.format("%,d", (int)(salesAmount * bonusRatio));
        String totalStr = String.format("%,d", getPay());
        System.out.printf("사원 %s의 급여는 월급 %s원, 수당 %s원을 합한 총액 %s원\n", super.name, salaryStr, bonusStr, totalStr);
    }
}
