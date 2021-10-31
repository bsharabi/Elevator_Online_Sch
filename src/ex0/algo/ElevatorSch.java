package ex0.algo;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.PriorityQueue;

class ElevatorSch {
    private final Elevator thisElev;
    private int _direction = 0;
    private int maxFloor, minFloor, startFloorToAction;
    public static final int UP = 1, DOWN = -1;
    private final int _numberOfFloors;
    private final double speed;
    private final int maxStop;
    private boolean busy;
    private final double go2FloorTime;
    private final IgoTo goTo = Elevator::goTo;
    private final Istop stop = Elevator::stop;
    private final PriorityQueue<CallForElevator> _queue;
    private final PriorityQueue<Integer> moveUP;
    private final PriorityQueue<Integer> moveDOWN;
    private final Itime calcTime = (e, src, dest, speed) -> {
        double actionElev = e.getTimeForOpen() + e.getTimeForClose() + e.getStopTime() + e.getStartTime();
        return (e.getPos() == src) ? (Math.abs(src - dest) / speed) + actionElev :
                (Math.abs(e.getPos() - src) / speed) + actionElev + (Math.abs(src - dest) / speed) + actionElev;

    };

    public ElevatorSch(Elevator e, int floorNumber, int numberOfElevator) {
        thisElev = e;
        maxFloor = e.getMaxFloor();
        minFloor = e.getMinFloor();
        speed = e.getSpeed();
        go2FloorTime = e.getTimeForClose() + e.getStartTime() + e.getStopTime() + e.getTimeForOpen();
        _numberOfFloors = floorNumber;
        busy = false;
        maxStop = (int) ((_numberOfFloors / speed) / numberOfElevator);
        _queue = new PriorityQueue<>(new ElevatorCompareQueue());
        moveUP = new PriorityQueue<>();
        moveDOWN = new PriorityQueue<>((x, y) -> Integer.compare(y, x));
    }

    public void init() {
        _direction = (moveDOWN.isEmpty() && moveUP.isEmpty()) ? 0 : (moveDOWN.isEmpty()) ? UP : DOWN;
        busy = ((!moveUP.isEmpty() && maxStop == moveUP.size() - 2) || (!moveDOWN.isEmpty() && maxStop == moveDOWN.size() - 2)) ? true : false;
        if (!_queue.isEmpty()) {
            CallForElevator c;
            if (_direction == UP) {
                _queue.removeIf(n -> {
                    if (n.getType() == UP && (thisElev.getPos() <= n.getSrc() && maxFloor >= n.getDest() || thisElev.getPos() <= n.getSrc() && n.getDest() > maxFloor)) {
                        addToAction(UP, n, moveUP);
                        maxFloor = Math.max(n.getDest(), maxFloor);
                        return true;
                    }
                    return false;

                });
            } else if (_direction == DOWN) {
                _queue.removeIf(n -> {
                    if (n.getType() == DOWN && (thisElev.getPos() >= n.getSrc() && minFloor <= n.getDest() || thisElev.getPos() >= n.getSrc() && n.getDest() < minFloor)) {
                        addToAction(DOWN, n, moveDOWN);
                        minFloor = Math.min(n.getDest(), minFloor);
                        return true;
                    }
                    return false;
                });

            } else {
                c = _queue.peek();
                minFloor = maxFloor = c.getDest();
                _direction = c.getType();
                while (!_queue.isEmpty() && _queue.peek().getType() == _direction) {
                    c = _queue.remove();
                    if (_direction == UP) {
                        addToAction(UP, c, moveUP);
                        maxFloor = Math.max(c.getDest(), maxFloor);
                    } else if (_direction == DOWN) {
                        addToAction(DOWN, c, moveDOWN);
                        minFloor = Math.min(c.getDest(), minFloor);
                    }
                }
                startFloorToAction = (_direction == UP) ? moveUP.peek() : moveDOWN.peek();
            }

        }

    }

    private void addToAction(int UoD, CallForElevator c, PriorityQueue action) {
        if (!action.contains(c.getSrc()))
            action.add(c.getSrc());
        if (!action.contains(c.getDest()))
            action.add(c.getDest());
    }

    public void action() {
        init();
        if (!moveUP.isEmpty()) {
            cmdLevel(UP);
        } else if (!moveDOWN.isEmpty()) {
            cmdLevel(DOWN);
        }
    }

    private void cmdLevel(int UoD) {
        PriorityQueue<Integer> action = (UoD == -1) ? moveDOWN : moveUP;
        int baseFloor = (UoD == -1) ? minFloor : maxFloor;
        int floor = action.peek();
        if (startFloorToAction == floor) {
            goTo.run(thisElev, startFloorToAction);
            if (thisElev.getPos() == floor) {
                startFloorToAction = Integer.MAX_VALUE;
                action.remove();
            }
        } else {
            goTo.run(thisElev, baseFloor);
            if (!action.isEmpty())
                stop.run(thisElev, floor);
            if (thisElev.getPos() == floor)
                action.remove();
        }
    }

    public double getTimeToComplete(CallForElevator c) {
        int src = c.getSrc();
        int dest = c.getDest();
        int type = c.getType();
        double sum;
        boolean sFlag, dFlag;
        if (busy)
            return Integer.MAX_VALUE;
        if (Math.abs(src - dest) < speed)
            return Integer.MAX_VALUE;
        if (_queue.isEmpty() && _direction == 0)
            return calcTime.run(thisElev, src, dest, speed);
        if (type == UP && _direction == UP) {
            if (moveUP.isEmpty())
                return calcTime.run(thisElev, src, dest, speed);
            if (thisElev.getPos() <= src && maxFloor >= dest) {
                sum = (moveUP.size() * go2FloorTime) + ((maxFloor - thisElev.getPos()) / speed);
                sFlag = moveUP.contains(src);
                dFlag = moveUP.contains(dest);
                sum += (sFlag) ? 0 : go2FloorTime;
                sum += (dFlag) ? 0 : go2FloorTime;
                return sum;
            } else if (thisElev.getPos() <= src && dest > maxFloor) {
                sFlag = moveUP.contains(src);
                sum = (moveUP.size() * go2FloorTime) + ((dest - thisElev.getPos()) / speed);
                sum += (sFlag) ? 0 : go2FloorTime;
                return sum;
            }
        } else if (type == DOWN && _direction == DOWN) {
            if (moveDOWN.isEmpty())
                return calcTime.run(thisElev, src, dest, speed);
            if (thisElev.getPos() >= src && minFloor <= dest) {
                sum = (moveDOWN.size() * go2FloorTime) + ((thisElev.getPos() - minFloor) / speed);
                sFlag = moveDOWN.contains(src);
                dFlag = moveDOWN.contains(dest);
                sum += (sFlag) ? 0 : go2FloorTime;
                sum += (dFlag) ? 0 : go2FloorTime;
                return sum;
            } else if (thisElev.getPos() >= src && dest < minFloor) {
                sum = (moveDOWN.size() * go2FloorTime) + ((thisElev.getPos() - dest) / speed);
                sFlag = moveUP.contains(src);
                sum += (sFlag) ? 0 : go2FloorTime;
                return sum;
            }
        }   if (!_queue.isEmpty()) {
            return ((_queue.size() * go2FloorTime) + _numberOfFloors / speed);
        }
        return Integer.MAX_VALUE;
    }

    public void setToQueue(CallForElevator c) {
        _queue.add(c);
    }
}
