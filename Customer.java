public class Customer implements Comparable<Customer> {

    private double arrivalTime;
    private int items;
    private double pickupTime;
    private boolean checkoutReady;
    private int id;

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

    public double checkoutReadyTime() {
        return items * pickupTime;
    }

    public boolean checkoutReady() {
        return checkoutReady;
    }

    public boolean isReady() {
        checkoutReady = true;
        return checkoutReady;
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
            return -1;
        }
        else if (this.arrivalTime > other.arrivalTime) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public String toString() {
        return  "Customer " + id;
    }

    
}