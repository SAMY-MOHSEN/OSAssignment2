package org.example.process;

public class MProcess {
    private String processName;
    private int arrivalTime;
    private int endTime;

    @Override
    public String toString() {
        return "MProcess{" +
                "processName='" + processName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", endTime=" + endTime +
                ", burstTime=" + burstTime +
                ", processPriority=" + processPriority +
                ", inQueue=" + inQueue +
                ", turnAroundTime=" + turnAroundTime +
                ", waitTime=" + waitTime +
                ", realTimeBurstTime=" + realTimeBurstTime +
                ", quantum=" + quantum +
                ", realTimeQuantum=" + realTimeQuantum +
                '}';
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    private int burstTime;
    private double processPriority;
    private boolean inQueue = false;

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    // calculate
    private double turnAroundTime;
    private double waitTime;
    public double realTimeBurstTime;

    private static double quantum;
    private double realTimeQuantum;




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

    public MProcess(String processName, int arrivalTime, int burstTime, double processPriority) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.processPriority = processPriority;
    }
    public MProcess(){}


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public double getProcessPriority() {
        return processPriority;
    }

    public void setProcessPriority(double processPriority) {
        this.processPriority = processPriority;
    }

    public static double getQuantum() {
        return quantum;
    }

    public void setQuantum(double quantum) {
        this.quantum = quantum;
    }

    public double getRealTimeQuantum() {
        return realTimeQuantum;
    }

    public void setRealTimeQuantum(double realTimeQuantum) {
        this.realTimeQuantum = realTimeQuantum;
    }
}
