package org.example.process;
class Process
{
    String processName; // Process Name
    double burstTime; // Burst Time
    double arrivalTime; // Arrival Time
    private int endTime;

    public Process(String processName, double burstTime, double arrivalTime) {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public String getProcessName() {
        return processName;
    }
    public double getBurstTime() {
        return burstTime;
    }
    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }
    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setEndTime(int next_second) {
        endTime = next_second;
    }
}

