import java.util.PriorityQueue;

public class CheckoutLane extends PriorityQueue<Customer> implements Comparable<CheckoutLane> {

    private double minPerItem = 0.05;
    private double processing = 2.0;

    public CheckoutLane() {

    }

    public double checkoutTime(Customer customer) {
        return (customer.getItems() * minPerItem) + processing;
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
}