// class that represents the customer
public class Customer {
    // customer fields
    private double arrivalTime;
    private int items;
    private double pickupTime;
    private int id;
    private int laneID;
    private double waitTime;
    private int sizeOfLane;

    // customer constructor that holds the items, pickUpTime per item, and their ID number
    public Customer(double arrivalTime, int items, double pickupTime, int id) {
        this.arrivalTime = arrivalTime;
        this.items = items;
        this.pickupTime = pickupTime;
        this.id = id;
    }
    // returns the customer's arrival time
    public double getArrivalTime() {
        return arrivalTime;
    }
    // setting the LaneSize with variable passed in
    public void setLaneSize(int size) {
        this.sizeOfLane = size;
    }
    // getting the size of the Lane
    public int getLaneSize() {
        return sizeOfLane;
    }
    // returning time it takes for customer to get in checkout line
    public double checkoutReadyTime() {
        return this.arrivalTime + items * pickupTime;
    }
    // setting time the customer has to wait
    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }
    // getting the time customer has to wait
    public double getWaitTime() {
        return waitTime;
    }
    // setting lane ID with number passed in
    public void setLaneID(int laneID) {
        this.laneID = laneID;
    }
    // getting the lane ID that customer is in
    public int getLaneID() {
        return laneID;
    }
    // determining if the customer is eligible for express lane or not
    public boolean expressEligible() {
        if (items <= 12) {
            return true;
        }
        else {
            return false;
        }
    }
    // getter for customer number of items
    public int getItems() {
        return items;
    }
    // getter for the customer ID
    public int getCustomerID() {
        return id;
    }
    // toString that returns the customer's ID
    public String toString() {
        return  "Customer " + id;
    }

    
}