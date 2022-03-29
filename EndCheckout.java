import java.util.PriorityQueue;

public class EndCheckout extends PriorityQueue<Customer> {

    public EndCheckout(double simClock) {
        this.poll();
    }
}