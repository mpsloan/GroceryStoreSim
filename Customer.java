public class Customer implements Comparable<Customer> {
    
    private double arrivalTime;
    private int items;
    private double pickupTime;
    private boolean checkoutReady;

    public Customer(double arrivalTime, int items, double pickupTime) {
        this.arrivalTime = arrivalTime;
        this.items = items;
        this.pickupTime = pickupTime;
        this.checkoutReady = false;
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

    public double getArrivalTime() {
        return arrivalTime;
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
        return "Customer arrived at " + arrivalTime + " is getting " + items + " items and takes this long " + pickupTime;
    }

    
}