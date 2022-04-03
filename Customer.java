public class Customer implements Comparable<Customer> {

    private double arrivalTime;
    private int items;
    private double pickupTime;
    private boolean checkoutReady;
    private int id;
    private int laneID;
    private double waitTime = 0.0;
    private int sizeOfLane;
    private double checkoutReadyTime;
    private double checkedOutTime;

    public Customer(double arrivalTime, int items, double pickupTime, int id) {
        this.arrivalTime = arrivalTime;
        this.items = items;
        this.pickupTime = pickupTime;
        this.checkoutReady = false;
        this.id = id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setLaneSize(int size) {
        this.sizeOfLane = size;
    }

    public int getLaneSize() {
        return sizeOfLane;
    }

    public double checkoutReadyTime() {
        return this.arrivalTime + items * pickupTime;
    }

    public boolean checkoutReady() {
        return checkoutReady;
    }


    public boolean isReady() {
        checkoutReady = true;
        return checkoutReady;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setLaneID(int laneID) {
        this.laneID = laneID;
    }

    public int getLaneID() {
        return laneID;
    }

    public boolean expressEligible() {
        if (items <= 12) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getItems() {
        return items;
    }

    public int getCustomerID() {
        return id;
    }
    
    @Override
    public int compareTo(Customer other) {
        if (this.arrivalTime < other.arrivalTime) {
            return 1;
        }
        else if (this.arrivalTime > other.arrivalTime) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    public String toString() {
        return  "Customer " + id;
    }

    
}