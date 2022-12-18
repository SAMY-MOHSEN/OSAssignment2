package org.example.process;
import java.util.ArrayList;
public class SJFTSchedule {
    private final int n;
    private final Process[] pro;
    private final int contextSwitch;
    public SJFTSchedule(Process[] pro, int n, int contextSwitch) {
        super();
        this.pro = pro;
        this.n = n;
        this.contextSwitch = contextSwitch;
    }
    public void run() {
        double[] waitingTime = new double[n];
        double[] turnaroundTime = new double[n];
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        // Calculating waiting time
        double[] remainingTime = new double[n];

        for (int i = 0; i < n; i++)
            remainingTime[i] = pro[i].burstTime;

        int allProcesses = 0;
        double time = 0;
        double minimumBurst = 100;
        int lowestProcess = 0;
        double finishTime;
        boolean check = false;
        int lastLowestProcess = -1;

        System.out.println("Processes execution order:");

        while (allProcesses != n) {

            for (int j = 0; j < n; j++) {
                if ((pro[j].arrivalTime <= time) && (remainingTime[j] < minimumBurst) && remainingTime[j] > 0) {
                    minimumBurst = remainingTime[j];
                    lowestProcess = j;
                    check = true;
                }
            }

            if (!check) {
                time++;
                continue;
            }

            // Display processes execution order
            if (lastLowestProcess == -1) {
                lastLowestProcess = 0;
                time = time - contextSwitch;
                if (lowestProcess == 0)
                    lastLowestProcess++;
            }
            String processExecuted;
            String lastProcessesExecuted;
            processExecuted = pro[lowestProcess].processName;
            lastProcessesExecuted = pro[lastLowestProcess].processName;
            if (!processExecuted.equals(lastProcessesExecuted)) {
                System.out.println("Executed: " + processExecuted);
                time = time + contextSwitch;
            }

            remainingTime[lowestProcess]--;

            minimumBurst = remainingTime[lowestProcess];
            if (minimumBurst == 0)
                minimumBurst = 100;

            lastLowestProcess = lowestProcess;

            if (remainingTime[lowestProcess] == 0) {

                allProcesses++;
                check = false;

                finishTime = time + 1;

                waitingTime[lowestProcess] = finishTime - pro[lowestProcess].burstTime - pro[lowestProcess].arrivalTime;

                if (waitingTime[lowestProcess] < 0)
                    waitingTime[lowestProcess] = 0;
            }
            time++;
        }

        // Calculating turn around time
        for (int i = 0; i < n; i++)
            turnaroundTime[i] = pro[i].burstTime + waitingTime[i];

        // Display
        System.out.println("Processes " + " Burst time " + " Arrival time " + " Waiting time " + " Turn around time");

        for (int i = 0; i < n; i++) {
            totalWaitingTime = totalWaitingTime + waitingTime[i];
            totalTurnaroundTime = totalTurnaroundTime + turnaroundTime[i];
            System.out.println("\t" + pro[i].processName + "\t\t\t" + pro[i].burstTime + "\t\t\t" + pro[i].arrivalTime + "\t\t\t " + waitingTime[i] + "\t\t\t\t" + turnaroundTime[i]);
        }

        System.out.println("Average waiting time = " + (float) totalWaitingTime / (float) n);
        System.out.println("Average turn around time = " + (float) totalTurnaroundTime / (float) n);
    }
}
