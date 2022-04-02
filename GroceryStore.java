import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class GroceryStore {
    
    public static ArrayList<Customer> customers(String filename) throws FileNotFoundException {
        File file = new File(filename);
        ArrayList<Customer> customers = new ArrayList<>();
        Scanner scan = new Scanner(file);
        int id = 0;
        while (scan.hasNextLine()) {
            double arrivalTime = scan.nextDouble();
            int items = scan.nextInt();
            double pickupTime = scan.nextDouble();
            Customer customer = new Customer(arrivalTime, items, pickupTime, id);
            customers.add(customer);
            id ++;
        }
        scan.close();
        return customers;
    }
    public static void main(String[] args) throws IOException {
        double simClock = 0;
        double tempWait = 0;
        double averageWaitTime = 0;
        PriorityQueue<Event> events = new PriorityQueue<>();
        PriorityQueue<Event> events2 = new PriorityQueue<>();
        File f = new File("GroceryStoreData/test_solution.txt");
        FileWriter writer = new FileWriter(f);
        ArrayList<Customer> customers = customers("GroceryStoreData/arrival simple.txt");
        ArrayList<CheckoutLane> lanes = new ArrayList<>();
        for (Customer c: customers) {
            Arrival arrival = new Arrival(c.getArrivalTime(), c);
            events.add(arrival);
            events2.add(arrival);
        }
        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                CheckoutLane checkLane = new CheckoutLane(i);
                lanes.add(checkLane);
            }
            else {
                ExpressLane eLane = new ExpressLane(i);
                lanes.add(eLane);
            }

        }

        while (!events.isEmpty()) {
            Event event = events.poll();
            Collections.sort(lanes);
            if (event instanceof Arrival) {
                simClock = event.getArrivalTime();
                EndShopping eShopping = new EndShopping(event.endShoppingTime() + simClock, event.getCustomer());
                events.offer(eShopping);
                events2.offer(eShopping);
            }
            else if(event instanceof EndShopping) {
                simClock = event.endShoppingTime() + event.getCustomer().getArrivalTime();
                if (lanes.get(0) instanceof ExpressLane && event.getCustomer().expressEligible()) {
                    for (Customer c: lanes.get(0)) {
                        tempWait += lanes.get(0).checkoutTime(c);
                    }
                    event.getCustomer().setWaitTime(tempWait);
                    event.getCustomer().setLaneID(lanes.get(0).getLaneID());
                    lanes.get(0).add(event.getCustomer());
                }
                else {
                    for (Customer c: lanes.get(0)) {
                        tempWait += lanes.get(0).checkoutTime(c);
                    }
                    event.getCustomer().setWaitTime(tempWait);
                    event.getCustomer().setLaneID(lanes.get(0).getLaneID());
                    lanes.get(0).add(event.getCustomer());
                }
                EndCheckout eCheckout = new EndCheckout(event.endCheckoutTime(lanes.get(0)) + simClock, event.getCustomer(), lanes.get(0));
                events.offer(eCheckout);
                events2.offer(eCheckout);

                
            }
            else {
                simClock = event.endCheckoutTime(lanes.get(0));
            }
        }

        while(!events2.isEmpty()) {
            writer.write(events2.poll() + "\n");
        }
        for (Customer c: customers) {
            averageWaitTime += c.getWaitTime();
        }
        averageWaitTime = averageWaitTime / customers.size();
        writer.write(averageWaitTime + "");
        writer.close();
    }
}