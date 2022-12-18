package org.example.schedules;

import org.example.process.MProcess;

import java.util.ArrayList;
import java.util.Stack;

public class AGSchedule extends ScheduleTechnique {
    private MProcess head;
    private int time = -1;
    private  ArrayList<MProcess> processes;
    private final ArrayList<MProcess> readyQueue = new ArrayList<>();
    private final ArrayList<MProcess> priorityQueue = new ArrayList<>();
    private final ArrayList<MProcess> burstTimeQueue = new ArrayList<>();
    private final Stack<MProcess> executionOrder = new Stack<>();
    private  int contextSwitching;
    public AGSchedule(ArrayList<MProcess> processes, int contextSwitching) {
        super();
        this.contextSwitching = contextSwitching;
        this.processes = processes;
        for(var tmp : processes){
            tmp.realTimeBurstTime = tmp.getBurstTime();
        }
    }

    public void addToReadyQueue(int time){
        for(var tmp_process : processes){
            if(tmp_process.getArrivalTime() <= time && !tmp_process.isInQueue()){
                readyQueue.add(tmp_process);
                priorityQueue.add(tmp_process);
                burstTimeQueue.add(tmp_process);
                tmp_process.setInQueue(true);
            }
        }
        priorityQueue.sort((o1, o2) -> (int) (o1.getProcessPriority() - o2.getProcessPriority()));
        burstTimeQueue.sort((o1, o2) -> (int) (o1.getBurstTime() - o2.getBurstTime()));
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
        processes.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));
        while(!allFinished()){
            time++;
            addToReadyQueue(time);
            if(head==null&&readyQueue.size()!=0){
                head = readyQueue.get(0);
            }
            //
            
            //
            if(executionOrder.isEmpty()){
                executionOrder.push(head);
            }else if(!executionOrder.peek().getProcessName().equals(head.getProcessName())){
                time+=contextSwitching;
                executionOrder.push(head);
            }
        }
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

