package org.example.process;

import java.util.ArrayList;
import java.util.List;

public class MProcess {
    private String processName; // Process Name
    private double burstTime; // Burst Time
    private double arrivalTime; // Arrival Time
    private int endTime;
    private double processPriority;

    private boolean inQueue = false;
    private double turnAroundTime;
    private double waitTime;
    private double realTimeBurstTime;
    List<Double> quantamList;
    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    // calculate
    public MProcess(String processName, double arrivalTime, double burstTime, double processPriority, double quantam) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.realTimeBurstTime = 0;
        this.burstTime = burstTime;
        this.realTimeBurstTime = burstTime;
        this.processPriority = processPriority;
        quantamList = new ArrayList<>();
        setQuantum(quantam);
    }

    public void setEndTime(int next_second) {
        endTime = next_second;
    }
    public double getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(double turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public double getRealTimeBurstTime() {
        return realTimeBurstTime;
    }

    public void setRealTimeBurstTime(double realTimeBurstTime) {
        this.realTimeBurstTime = realTimeBurstTime;
    }

    @Override
    public String toString() {
        return "MProcess{" +
                "processName='" + processName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", processPriority=" + processPriority +
                ", realTimeQuantum=" + getRealTimeQuantum() +
                '}';
    }


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public double getProcessPriority() {
        return processPriority;
    }

    public void setProcessPriority(double processPriority) {
        this.processPriority = processPriority;
    }

    public double getQuantum() {
        return quantamList.get(quantamList.size() - 1);
    }

    public void setQuantum(double quantum) {
        quantamList.add(quantum);
    }

    public double getRealTimeQuantum() {
        return quantamList.get(0);
    }

    public void setRealTimeQuantum(double quantum) {
        quantamList.set(0, quantum);
    }

    public List<Double> getQuantamList() {
        return quantamList;
    }
}
