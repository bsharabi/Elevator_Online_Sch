package ex0.algo;

import ex0.CallForElevator;

import java.util.Comparator;

class ElevatorCompareQueue implements Comparator<CallForElevator> {
    @Override
    public int compare(CallForElevator o1, CallForElevator o2) {
        if (o1.getTime(0) < o2.getTime(0)) return -1;
        if (o1.getTime(0) > o2.getTime(0)) return 1;
        else return 0;
    }
}
