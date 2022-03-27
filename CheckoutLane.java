import java.util.PriorityQueue;

public class CheckoutLane extends PriorityQueue<Customer> {

    private double minPerItem = 0.05;
    private double processing = 2.0;

    public CheckoutLane() {
       
    }

    public double checkoutTime(Customer customer) {
        return (customer.getItems() * minPerItem) + processing;
    }
}