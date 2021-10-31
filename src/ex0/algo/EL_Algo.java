package ex0.algo;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.*;


public class EL_Algo implements ElevatorAlgo {

    private int numberOfFloors;
    private int numOfElevator;
    private Building _building;
    ElevatorSch[] _elevatorSch;

    public EL_Algo(Building b) {
        _building = b;
        this.numOfElevator = b.numberOfElevetors();
        this.numberOfFloors = b.maxFloor() - b.minFloor();
        _elevatorSch = new ElevatorSch[numOfElevator];
        for (int i = 0; i < numOfElevator; i++) {
            _elevatorSch[i] = new ElevatorSch(b.getElevetor(i), numberOfFloors, numOfElevator);
        }
    }

    @Override
    public Building getBuilding() {
        return _building;
    }

    @Override
    public String algoName() {
        return "Ex0_OOP_AlgoBest";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        double temp = 0;
        double minTime = _elevatorSch[0].getTimeToComplete(c);
        int e = 0;
        for (int i = 1; i < numOfElevator; i++) {
            temp = _elevatorSch[i].getTimeToComplete(c);
            if (temp < minTime) {
                minTime = temp;
                e = i;
            }
        }
        _elevatorSch[e].setToQueue(c);
        return e;
    }

    @Override
    public void cmdElevator(int elev) {
        _elevatorSch[elev].action();
    }
}

