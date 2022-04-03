// CheckoutLane class that extends ArrayList of type customer and implements comparable
import java.util.ArrayList;

public class CheckoutLane extends ArrayList<Customer> implements Comparable<CheckoutLane> {
    // initializing private fields
    private double minPerItem = 0.05;
    private double processing = 2.0;
    private int id;
    private int processCount = 0;
    private double totalWaitTime = 0;
    
    // setting lane number in constructor
    public CheckoutLane(int id) {
        this.id = id;
    }
    // returning the time it takes for customer to fully checkout
    public double checkoutTime(Customer customer) {
        return (customer.getItems() * this.minPerItem) + this.processing;
    }
    // getting the laneID
    public int getLaneID() {
        return id;
    }
    // getting processCounter
    public int getProcessCount() {
        return this.processCount;
    }
    // setting the processCount to what I pass in
    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }
    // returning the total waitTime in line
    public double getTotalWaitTime() {
        return this.totalWaitTime;
    }
    // setting the totalWaitTime in line, to use for average
    public void setTotalWaitTime(double totalWaitTime) {
        this.totalWaitTime += totalWaitTime;
    }

    // comparing lanes by amount of customers in the lane
    @Override
    public int compareTo(CheckoutLane other) {
        if (this.size() < other.size()) {
            return -1;
        }
        else if (this.size() > other.size()) {
            return 1;
        }
        else {
            return 0;
        }
    }
    // toString that returns Lane, it's number, and it's size
    public String toString() {
        return "Lane " +id+ " (" +this.size()+ ")";
    }
}