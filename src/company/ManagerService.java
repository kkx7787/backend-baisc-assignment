package company;

public class ManagerService {
    private Worker[] workers;

    public ManagerService() {
        workers = new Worker[10];
    }

    public void addWorker(Worker worker) {
        boolean added = false;
        for (int i = 0; i < workers.length; i++) {
            if (workers[i] == null) {
                workers[i] = worker;
                added = true;
                break;
            }
        }
        if (!added) {
            System.out.println("직원 목록이 가득 찼습니다. 더 이상 추가할 수 없습니다.");
        }
    }

    public void showAllSalaryInfo() {
        for (Worker value : workers) {
            if (value != null) {
                value.showSalaryInfo(value.name);
            }
        }
    }

    public void showSalaryInfo(String name) {
        boolean found = false;
        for (Worker value : workers) {
            if (value != null && value.name.equals(name)) {
                value.showSalaryInfo(name);
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 이름의 직원을 찾을 수 없습니다.");
        }
    }

    public void showTotalSalary() {
        int total = 0;
        for (Worker value : workers) {
            if (value != null) {
                total += value.getPay();
            }
        }
        String totalSalaryStr = String.format("%,d", total);
        System.out.println("모든 사원들의 급여 총액은: " + totalSalaryStr + "원");
    }
}
