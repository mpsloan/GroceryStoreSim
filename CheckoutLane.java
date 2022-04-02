import java.util.PriorityQueue;

public class CheckoutLane extends PriorityQueue<Customer> implements Comparable<CheckoutLane> {

    private double minPerItem = 0.05;
    private double processing = 2.0;
    private int id;

    public CheckoutLane(int id) {
        this.id = id;
    }

    public double checkoutTime(Customer customer) {
        return (customer.getItems() * minPerItem) + processing;
    }

    public int getLaneID() {
        return id;
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
        return "Lane " +id+ " (" +this.size()+ ")";
    }
}