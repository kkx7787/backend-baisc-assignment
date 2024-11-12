package problem2.company;

import java.util.ArrayList;
import java.util.List;

public class ManagerService {
    private List<Worker> workers;

    public ManagerService() {
        this.workers = new ArrayList<>();
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);
    }

    public void showAllSalaryInfo() {
        for (Worker worker : workers) {
            worker.showSalaryInfo(worker.name);
        }
    }

    public void showSalaryInfo(String name) {
        for (Worker worker : workers) {
            if (name.equals(worker.name)) {
                worker.showSalaryInfo(name);
            }
        }
    }

    public void showTotalSalary() {
        int totalSalary = 0;
        for (Worker value : workers) {
            totalSalary += value.getPay();
        }
        String totalSalaryStr = String.format("%,d", totalSalary);
        System.out.println("모든 사원들의 급여 총액은: " + totalSalaryStr + "원");
    }
}
