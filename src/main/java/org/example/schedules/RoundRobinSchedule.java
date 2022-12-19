package org.example.schedules;

import org.example.process.MProcess;

import java.util.ArrayList;
import java.util.Stack;

public class RoundRobinSchedule extends ScheduleTechnique {
    private int time = -1;
    private final ArrayList<MProcess> processes;
    private final ArrayList<MProcess> readyQueue = new ArrayList<>();
    private final Stack<MProcess> executionOrder = new Stack<>();
    private final int contextSwitching;

    public RoundRobinSchedule(ArrayList<MProcess> processes, int contextSwitching) {
        super();
        this.processes = processes;
        this.contextSwitching = contextSwitching;
    }

    public void addToReadyQueue(int time) {
        for (var tmp_process : processes) {
            if (tmp_process.getArrivalTime() <= time && !tmp_process.isInQueue()) {
                readyQueue.add(tmp_process);
                tmp_process.setInQueue(true);
            }
        }
//        readyQueue.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));
    }

    public boolean allFinished() {
        boolean flag = true;
        for (var tmp_process : processes) {
            flag = flag && tmp_process.isInQueue() && tmp_process.getRealTimeBurstTime() >= tmp_process.getBurstTime();
        }
        return flag;
    }

    @Override
    public void run() {
        MProcess head;
        double round_robin_time = MProcess.getQuantum();
        processes.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));

        while (!allFinished()) {
            time++;
            int next_second = time;

            addToReadyQueue(time);
            if (readyQueue.size() == 0) continue;
            head = readyQueue.get(0);
            head.realTimeBurstTime++;
            round_robin_time--;

            if (head.realTimeBurstTime >= head.getBurstTime()) {
                readyQueue.get(0).setEndTime(++next_second);
                int burstTime = readyQueue.get(0).getBurstTime();
                int arrivalTime = readyQueue.get(0).getArrivalTime();
                readyQueue.get(0).setWaitTime(next_second - arrivalTime - burstTime);
                readyQueue.get(0).setTurnAroundTime(next_second - arrivalTime + contextSwitching);
                readyQueue.remove(0);
                round_robin_time = MProcess.getQuantum();
            } else if (round_robin_time == 0) {
                addToReadyQueue(time+1);
                round_robin_time = MProcess.getQuantum();
                MProcess tmp_process = readyQueue.get(0);
                readyQueue.remove(head);
                readyQueue.add(tmp_process);
                time += contextSwitching;
            }

            if (executionOrder.isEmpty()) {
                executionOrder.push(head);
            } else if (!executionOrder.peek().getProcessName().equals(head.getProcessName())) {
                time += contextSwitching;
                executionOrder.push(head);
            }

        }
//             this comment is to show the ending time.
        for (var tmp : processes) {
            System.out.println(tmp.getProcessName() + " " + tmp.getEndTime());
        }
        statistics();
    }
    public void statistics() {
        double averageWaitingTime = 0;
        double averageTurnAroundTime = 0;
        System.out.println("....statistics....");
        System.out.println("Execution Order: ");
        for (var process : executionOrder) {
            System.out.print(process.getProcessName() + " ");
        }
        System.out.println();

        System.out.println("Waiting time for each process:- ");
        for (var process : processes) {
            averageWaitingTime += process.getWaitTime();
            System.out.println(process.getProcessName() + " " + process.getWaitTime());
        }
        System.out.println("Turn around for each process:- ");
        for (var process : processes) {
            averageTurnAroundTime += process.getTurnAroundTime();
            System.out.println(process.getProcessName() + " " + process.getTurnAroundTime());
        }
        System.out.println("Average waiting time:- ");
        System.out.println(averageWaitingTime / processes.size());
        System.out.println("Average turn around time:- ");
        System.out.println(averageTurnAroundTime / processes.size());
    }
}
/*
P1
0
4
0
P2
1
8
0
P3
3
2
0
P4
10
6
0
P5
12
5
0
 */
/*
P1
0
5
0
P2
1
3
0
P3
2
1
0
P4
3
2
0
P5
4
3
0
 */
/*
P1
0
4
0
P2
1
5
0
P3
2
2
0
P4
3
1
0
P5
4
6
0
P6
6
3
0
 */
/*
P1
5
5
0
P2
4
6
0
P3
3
7
0
P4
1
9
0
P5
2
2
0
P6
6
3
0
 */
/*
P1
0
4
0
P2
0
1
0
P3
0
8
0
P4
0
1
0
 */
/*
P1
0
4
0
P2
1
8
0
P3
3
2
0
P4
10
6
0
P5
12
5
0
 */
/*
P1
0
21
0
P2
0
3
0
P3
0
6
0
P4
0
2
0
 */
/*
P1
0
5
0
P2
1
4
0
P3
2
2
0
P4
3
1
0
 */