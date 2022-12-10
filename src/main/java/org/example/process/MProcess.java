package org.example.process;

public class MProcess {
    private String processName;
    private double arrivalTime;
    private double burstTime;
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
    private double realTimeBurstTime;

    private double quantum;
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

    public MProcess(String processName, double arrivalTime, double burstTime, double processPriority) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.processPriority = processPriority;
    }
    public MProcess(){}

    @Override
    public String toString() {
        return "MProcess{" +
                "processName='" + processName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", processPriority=" + processPriority +
                ", quantum=" + quantum +
                ", realTimeQuantum=" + realTimeQuantum +
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
