package org.example.schedules;

import org.example.process.MProcess;

import java.util.ArrayList;

public class PreemptiveSchedule extends ScheduleTechnique{
    private MProcess head = null;
    private int time = 0;
    private double agingFactor = 0.1;

    private ArrayList<MProcess> processes;
    private ArrayList<MProcess> readyQueue = new ArrayList<>();
    private int contextSwitching;
    public PreemptiveSchedule(ArrayList<MProcess> processes, int contextSwitching) {
        super();
        this.processes = processes;
        this.contextSwitching = contextSwitching;
    }

    @Override
    public void run() {
        processes.sort((o1, o2) -> (int) (o1.getArrivalTime() - o2.getArrivalTime()));


    }
}
