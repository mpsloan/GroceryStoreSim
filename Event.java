import java.util.PriorityQueue;

public class Event extends PriorityQueue<Customer> implements Comparable<Event> {
    
    private Customer customer;
    private double time;

    public Event(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        }
        else if (this.time > other.time) {
            return 1;
        }
        else {
            return 0;
        }
    }
}