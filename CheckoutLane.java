import java.util.ArrayList;

public class CheckoutLane extends ArrayList<Customer> implements Comparable<CheckoutLane> {

    private double minPerItem = 0.05;
    private double processing = 2.0;
    private int id;
    private int processCount = 0;
    private double totalWaitTime = 0;
    public CheckoutLane(int id) {
        this.id = id;
    }

    public double checkoutTime(Customer customer) {
        return (customer.getItems() * minPerItem) + processing;
    }

    public int getLaneID() {
        return id;
    }

    public int getProcessCount() {
        return this.processCount;
    }

    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }

    public double getTotalWaitTime() {
        return this.totalWaitTime;
    }

    public void setTotalWaitTime(double totalWaitTime) {
        this.totalWaitTime += totalWaitTime;
    }


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

    public String toString() {
        return "Lane " +id+ " (" +(this.size())+ ")";
    }
}