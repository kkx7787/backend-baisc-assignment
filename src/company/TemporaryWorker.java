package company;

public class TemporaryWorker extends Worker{
    int payPerHour;
    int workTime;

    public TemporaryWorker(String name, int payPerHour, int workTime) {
        super(name);
        this.payPerHour = payPerHour;
        this.workTime = workTime;
    }
    public int getPay() {
        return payPerHour * workTime;
    }

    public void showSalaryInfo(String name) {
        String payPerHourStr = String.format("%,d", payPerHour);
        String totalPayStr = String.format("%,d", getPay());
        System.out.printf("사원 %s의 근무시간은 %d시간, 시간 수당은 %s원 급여는 %s원\n", name, workTime, payPerHourStr, totalPayStr);
    }
}
