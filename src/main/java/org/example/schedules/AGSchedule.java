package org.example.schedules;

import org.example.process.MProcess;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AGSchedule extends ScheduleTechnique {
    private MProcess head = null;
    private int time = 0;
    private ArrayList<MProcess>readyQueue = new ArrayList<>();
    private int contextSwitching;
    private ArrayList<MProcess>processes;
    private int i = 0;
    public AGSchedule(ArrayList<MProcess> processes, int contextSwitching) {
        super();
        this.contextSwitching = contextSwitching;
        this.processes = processes;
    }
    public boolean getFromProcesses(){
        boolean flag = false;
        if(i==processes.size())return false;

        head = processes.get(i);
        i++;
        while(i<processes.size()){
            if(processes.get(i).getArrivalTime() == head.getArrivalTime()){
                readyQueue.add(processes.get(i++));
                flag = true;
            }else{
                break;
            }
        }
        return flag;
    }
    @Override
    public void run() {
        int currentCondition = 0;
        double taken;

        processes.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));

        while(true){
            // note: there is entry and exist condition
            if(head == null && readyQueue.isEmpty()){
                if(!getFromProcesses()){
                    break;
                }
            }else if(head == null){

            }else if(readyQueue.isEmpty()){

            }else{

            }
            assert head != null;
            taken = Math.ceil(head.getQuantum()*0.25);
            head.setQuantum(head.getQuantum()-taken);
            time+=taken;
            currentCondition++;
        }
    }
}
