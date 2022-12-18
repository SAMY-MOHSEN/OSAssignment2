package org.example.schedules;

import org.example.process.MProcess;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
/*4
0
1
0
17
4
7
2
2
6
7
9
3
5
11
3
4
4
15
4
6
6
4
*/
public class AGSchedule extends ScheduleTechnique {
    private MProcess head;
    private double time;
    private ArrayList<MProcess>readyQueue = new ArrayList<>();
    private int contextSwitching;
    private ArrayList<MProcess>processes;
    private int i = 0;
    private final ArrayList<MProcess> priorityQueue = new ArrayList<>();
    private final ArrayList<MProcess> burstTimeQueue = new ArrayList<>();
    private final Stack<MProcess> executionOrder = new Stack<>();
    public AGSchedule(ArrayList<MProcess> processes, int contextSwitching) {
        super();
        this.contextSwitching = contextSwitching;
        this.processes = processes;
    }
    @Override
    public void run() {
        time = 0;
        i = 0;
        int currentCondition = 0;
        double taken = 0;
        head = null;
        processes.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));

        while(true){
            // note: there is entry and exist condition
            if(head == null){
                if(readyQueue.size() == 0 && !getFromProcesses()){
                    break;
                }
                head = readyQueue.get(0);
                removeFromqueue(head);
                head.setQuantum(head.getRealTimeQuantum());
            }else if(head != null){
                if(currentCondition == 0){
                    MProcess prev = head;
                    addToQueue(prev);
                    head = priorityQueue.get(0);
                    removeFromqueue(head);
                    if(head == prev){
                        currentCondition++;
                    }
                    else{
                        prev.setRealTimeQuantum(prev.getRealTimeQuantum() + Math.ceil(prev.getQuantum() / 2));
                        head.setQuantum(head.getRealTimeQuantum());
                        currentCondition = 0;
                    }
                }
                else if(currentCondition == 1){
                    MProcess prev = head;
                    addToQueue(prev);
                    head = burstTimeQueue.get(0);
                    removeFromqueue(head);
                    if(head == prev){
                        currentCondition = 2;
                    }
                    else{
                        prev.setRealTimeQuantum(prev.getRealTimeQuantum() + prev.getQuantum());
                        head.setQuantum(head.getRealTimeQuantum());
                        currentCondition = 0;
                    }
                }
            }
            if(executionOrder.size() == 0 || executionOrder.get(executionOrder.size() - 1) != head){
                executionOrder.add(head);
            }
            if(currentCondition < 2){
                taken = Math.ceil(head.getRealTimeQuantum()*0.25);
            }
            else{
                taken = 1;
            }
            head.setQuantum(head.getQuantum()-taken);
            if(head.getBurstTime() - taken <= 0){
                time += head.getBurstTime();
                head.setBurstTime(0);
                head.setQuantum(0);
                head.setWaitTime(time + 1 - head.getArrivalTime() - head.getRealTimeBurstTime());
                head.setTurnAroundTime(time + 1 - head.getArrivalTime());
                head = null;
                currentCondition = 0;
                getFromProcesses(time);
            }
            else{
                head.setBurstTime(head.getBurstTime() - taken);
                time+=taken;
                getFromProcesses(time);
                if(head.getQuantum() == 0){
                    head.setRealTimeQuantum(head.getRealTimeQuantum() + 2);
                    getFromProcesses(time);
                    addToQueue(head);
                    head = null;
                    currentCondition = 0;
                }
            }
        }
        statistics();
    }

    public boolean getFromProcesses(){
        if(i==processes.size())return false;
        time = processes.get(i).getArrivalTime();
        getFromProcesses(time);
        return true;
    }
    public boolean getFromProcesses(double time){
        boolean flag = false;
        if(i==processes.size())return false;
        while(i<processes.size()){
            if(processes.get(i).getArrivalTime() <= time){
                addToQueue(processes.get(i));
                i++;
                flag = true;
            }else{
                break;
            }
        }
        return flag;
    }
    private void addToQueue(MProcess process) {
        readyQueue.add(process);
        priorityQueue.add(process);
        burstTimeQueue.add(process);
        priorityQueue.sort((o1, o2) -> (int) (o1.getProcessPriority() - o2.getProcessPriority()));
        burstTimeQueue.sort((o1, o2) -> (int) (o1.getBurstTime() - o2.getBurstTime()));
    }
    private void removeFromqueue(MProcess process) {
        readyQueue.remove(process);
        burstTimeQueue.remove(process);
        priorityQueue.remove(process);
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
        System.out.println("Quantam history for each process:- ");
        for(var process: processes){
            process.getQuantamList().remove(0);
            for (Double x:process.getQuantamList()) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
        System.out.println("Average waiting time:- ");
        System.out.println(averageWaitingTime/processes.size());
        System.out.println("Average turn around time:- ");
        System.out.println(averageTurnAroundTime/processes.size());
    }
}