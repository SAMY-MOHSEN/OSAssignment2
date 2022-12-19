package org.example.schedules;

import org.example.process.MProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
public class PreemptiveSchedule extends ScheduleTechnique{
    private int time = -1;
    private final List<MProcess> processes;
    private final List<MProcess> readyQueue = new ArrayList<>();
    private final Stack<MProcess> executionOrder = new Stack<>();
    private final int contextSwitching;
    public PreemptiveSchedule(List<MProcess> processes, int contextSwitching) {
        super();
        this.processes = processes;
        this.contextSwitching = contextSwitching;
    }

    public void addToReadyQueue(int time){
        for(var tmp_process : processes){
            if(tmp_process.getArrivalTime() <= time && !tmp_process.isInQueue()){
                readyQueue.add(tmp_process);
                tmp_process.setInQueue(true);
            }
        }
        readyQueue.sort((o1, o2) -> (int) (o1.getProcessPriority() - o2.getProcessPriority()));
        //aging 0..10
//        var tmp_process = readyQueue.get(readyQueue.size()-1);
//        if(tmp_process.getProcessPriority()>0){
//            tmp_process.setProcessPriority(tmp_process.getProcessPriority()-.5);
//        }
        //aging 0..10
    }

    public boolean allFinished(){
        boolean flag = true;
        for(var tmp_process : processes){
            flag=flag&&tmp_process.isInQueue()&&tmp_process.getRealTimeBurstTime()>=tmp_process.getBurstTime();
        }
        return flag;
    }
    @Override
    public void run() {
        MProcess head;
        processes.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));
        while(!allFinished()){
            time++;
            int next_second = time;

            addToReadyQueue(time);
            if(readyQueue.size()==0)continue;
            head = readyQueue.get(0);
            head.setRealTimeBurstTime(head.getRealTimeBurstTime() + 1);
            if(head.getRealTimeBurstTime() >= head.getBurstTime()){
                readyQueue.get(0).setEndTime(++next_second);
                double burstTime = readyQueue.get(0).getBurstTime();
                double arrivalTime = readyQueue.get(0).getArrivalTime();
                readyQueue.get(0).setWaitTime(next_second-arrivalTime-burstTime);
                readyQueue.get(0).setTurnAroundTime(next_second-arrivalTime+contextSwitching);
                readyQueue.remove(0);
            }

            if(executionOrder.isEmpty()){
                executionOrder.push(head);
            }else if(!executionOrder.peek().getProcessName().equals(head.getProcessName())){
                time+=contextSwitching;
                executionOrder.push(head);
            }
        }
//             this comment is to show the ending time.
//            for(var tmp :processes){
//                System.out.println(tmp.getProcessName()+" "+tmp.getEndTime());
//            }
        statistics();
    }
    public void statistics(){
        double averageWaitingTime = 0;
        double averageTurnAroundTime = 0;
        System.out.println("....statistics....");
        System.out.println("Execution Order: ");
        for(var process : executionOrder){
            System.out.print(process.getProcessName()+" ");
        }
        System.out.println();

        System.out.println("Waiting time for each process:- ");
        for(var process: processes){
            averageWaitingTime+= process.getWaitTime();
            System.out.println(process.getProcessName()+" "+process.getWaitTime());
        }
        System.out.println("Turn around for each process:- ");
        for(var process: processes){
            averageTurnAroundTime+= process.getTurnAroundTime();
            System.out.println(process.getProcessName()+" "+process.getTurnAroundTime());
        }
        System.out.println("Average waiting time:- ");
        System.out.println(averageWaitingTime/processes.size());
        System.out.println("Average turn around time:- ");
        System.out.println(averageTurnAroundTime/processes.size());
    }
}

/*
5
0
0
P1
0
4
1
0
P2
0
3
2
0
P3
6
7
1
0
P4
11
4
3
0
P5
12
2
2
0
*/