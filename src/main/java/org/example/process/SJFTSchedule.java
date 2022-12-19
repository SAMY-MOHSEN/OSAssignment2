package org.example.process;
import org.example.schedules.ScheduleTechnique;

import java.util.ArrayList;
import java.util.List;

public class SJFTSchedule extends ScheduleTechnique {
    private int n;
    private MProcess[] pro;
    private int contextSwitch;

    public SJFTSchedule(List<MProcess> processes, int contextSwitching) {
        pro = processes.stream().toArray(MProcess[] :: new);
        this.n = processes.size();
        this.contextSwitch = contextSwitching;
    }
    @Override
    public void run() {
        double[] waitingTime = new double[n];
        double[] turnaroundTime = new double[n];
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        // Calculating waiting time
        double[] remainingTime = new double[n];

        for (int i = 0; i < n; i++)
            remainingTime[i] = pro[i].getBurstTime();

        int allProcesses = 0;
        double time = 0;
        double minimumBurst = Double.MAX_VALUE;
        int lowestProcess = 0;
        double finishTime;
        boolean check = false;
        int lastLowestProcess = -1;

        System.out.println("Processes execution order:");

        while (allProcesses != n) {

            for (int j = 0; j < n; j++) {
                if ((pro[j].getArrivalTime() <= time) && (remainingTime[j] < minimumBurst) && remainingTime[j] > 0) {
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
            processExecuted = pro[lowestProcess].getProcessName();
            lastProcessesExecuted = pro[lastLowestProcess].getProcessName();
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

                waitingTime[lowestProcess] = finishTime - pro[lowestProcess].getBurstTime() - pro[lowestProcess].getArrivalTime();

                if (waitingTime[lowestProcess] < 0)
                    waitingTime[lowestProcess] = 0;
            }
            time++;
        }

        // Calculating turn around time
        for (int i = 0; i < n; i++)
            turnaroundTime[i] = pro[i].getBurstTime() + waitingTime[i];

        // Display
        System.out.println("Processes " + " Burst time " + " Arrival time " + " Waiting time " + " Turn around time");

        for (int i = 0; i < n; i++) {
            totalWaitingTime = totalWaitingTime + waitingTime[i];
            totalTurnaroundTime = totalTurnaroundTime + turnaroundTime[i];
            System.out.println("\t" + pro[i].getProcessName() + "\t\t\t" + pro[i].getBurstTime() + "\t\t\t" + pro[i].getArrivalTime() + "\t\t\t " + waitingTime[i] + "\t\t\t\t" + turnaroundTime[i]);
        }

        System.out.println("Average waiting time = " + (float) totalWaitingTime / (float) n);
        System.out.println("Average turn around time = " + (float) totalTurnaroundTime / (float) n);
    }
}

/*
5
0
P1
2
6
0
0
P2
5
2
0
0
P3
1
8
0
0
P4
0
3
0
0
P5
4
4
0
0
*/