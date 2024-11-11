package company;

public class ManagerService {
    private final Worker[] worker;

    public ManagerService() {
        worker = new Worker[10];
    }
    public void addWorker(Worker newWorker) {
        boolean added = false;
        for (int i = 0; i < worker.length; i++) {
            if (worker[i] == null) { // 빈 자리를 찾아 추가
                worker[i] = newWorker;
                added = true;
                break;
            }
        }
        if (!added) {
            System.out.println("직원 목록이 가득 찼습니다. 더 이상 추가할 수 없습니다.");
        }
    }

    public void showAllSalaryInfo() {
        for (Worker value : worker) {
            if (value != null) {
                value.showSalaryInfo(value.name);
            }
        }
    }

    // 특정 직원의 월급 정보 출력 메서드
    public void showSalaryInfo(String name) {
        boolean found = false;
        for (Worker value : worker) {
            if (value != null && value.name.equals(name)) {
                value.showSalaryInfo(name);
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 이름의 직원을 찾을 수 없습니다.");
        }
    }

    // 모든 직원의 총 월급 계산 메서드
    public void showTotalSalary() {
        int total = 0;
        for (Worker value : worker) {
            if (value != null) {
                total += value.getPay();
            }
        }
        String totalSalaryStr = String.format("%,d", total);
        System.out.println("모든 사원들의 급여 총액은: " + totalSalaryStr + "원");
    }
}
