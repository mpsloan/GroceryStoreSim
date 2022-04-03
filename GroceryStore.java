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
        //ArrayList<Customer> customers = customers("GroceryStoreData/arrival simple.txt");
        ArrayList<Customer> customers = customers("GroceryStoreData/arrival medium.txt");
        ArrayList<CheckoutLane> lanes = new ArrayList<>();

        for (Customer c: customers) {
            Arrival arrival = new Arrival(c.getArrivalTime(), c);
            events.add(arrival);
            events2.add(arrival);
        }

        // for (int i = 0; i < 5; i++) {
        //     if (i < 4) {
        //         CheckoutLane checkLane = new CheckoutLane(i);
        //         lanes.add(checkLane);
        //     }
        //     else {
        //         ExpressLane eLane = new ExpressLane(i);
        //         lanes.add(eLane);
        //     }

        // }

        CheckoutLane checkLane1 = new CheckoutLane(1);
        CheckoutLane checkLane2 = new CheckoutLane(2);
        CheckoutLane checkLane3 = new CheckoutLane(3);
        CheckoutLane checkLane4 = new CheckoutLane(4);
        CheckoutLane checkLane5 = new CheckoutLane(5);
        CheckoutLane checkLane6 = new CheckoutLane(6);
        CheckoutLane checkLane7 = new CheckoutLane(7);
        CheckoutLane checkLane8 = new CheckoutLane(8);
        CheckoutLane checkLane9 = new CheckoutLane(9);
        CheckoutLane checkLane10 = new CheckoutLane(10);
        CheckoutLane checkLane11 = new CheckoutLane(11);
        CheckoutLane checkLane12 = new CheckoutLane(12);

        ExpressLane eLane = new ExpressLane(99);

        lanes.add(checkLane1);
        lanes.add(checkLane2);
        lanes.add(checkLane3);
        lanes.add(checkLane4);
        lanes.add(checkLane5);
        lanes.add(checkLane6);
        lanes.add(checkLane7);
        lanes.add(checkLane8);
        lanes.add(checkLane9);
        lanes.add(checkLane10);
        lanes.add(checkLane11);
        lanes.add(checkLane12);

        lanes.add(eLane);
        // ExpressLane eLane1 = new ExpressLane(0);
        // lanes.add(eLane1);
        // ExpressLane eLane2 = new ExpressLane(1);
        // lanes.add(eLane2);
        // CheckoutLane only = new CheckoutLane(2);
        // lanes.add(only);

        while (!events.isEmpty()) {
            Event event = events.poll();
            Collections.sort(lanes);
            if (event instanceof Arrival) {
                simClock = event.endShoppingTime();
                EndShopping eShopping = new EndShopping(simClock, event.getCustomer());
                events.offer(eShopping);
                events2.offer(eShopping);
            }
            else if(event instanceof EndShopping) {
                simClock = event.endShoppingTime();
                
                /*
                    Checkout process:
                    1)  Find the shortest lane
                    2)  Add event.getCustomer() to shortestlane
                    ..  suggestion is to stop here ..
                */

                CheckoutLane l = pickALane(event.getCustomer(), lanes);
                l.add(event.getCustomer());
                l.setProcessCount(l.getProcessCount()+1);
                event.getCustomer().setLaneID(l.getLaneID());
                simClock = event.endCheckoutTime(l) + simClock;
                EndCheckout eCheckout = new EndCheckout(simClock, event.getCustomer(), l);
                events.offer(eCheckout);
                events2.offer(eCheckout);
                //tempWait = 0;

                
            }
            else {
                tempWait = 0.0;
                CheckoutLane currentLane = null;

                for(CheckoutLane l : lanes){
                    if(l.getLaneID() == event.getCustomer().getLaneID()){
                        currentLane = l;
                        break;
                    }
                }
                
                if(currentLane.size() == 1 || currentLane.get(0) == event.getCustomer()){
                    //  I'm the only customer
                    tempWait = 0.0;

                } else {
                    //  Find me in line
                    int positionInLine = 0;
                    for (Customer customer : currentLane) {
                        if(customer == event.getCustomer()){
                            break;
                        }
                        positionInLine++;
                    }                
                    //  Look at previous guy
                    double prevCheckoutTime = currentLane.get(positionInLine-1).getWaitTime()+ currentLane.get(positionInLine-1).checkoutReadyTime() + currentLane.checkoutTime(currentLane.get(positionInLine-1));
                    double readyCheckOutTime = event.getCustomer().checkoutReadyTime();
                    //  Prev guy and I for tempwait
                    tempWait = Math.abs(prevCheckoutTime - readyCheckOutTime);
                    currentLane.setTotalWaitTime(tempWait);
                }
                event.getCustomer().setWaitTime(tempWait);
                simClock = event.endCheckoutTime(currentLane);
                event.setTime(simClock);
                currentLane.remove(event.getCustomer());
            }
        }

        while(!events2.isEmpty()) {
            writer.write(events2.poll() + "\n");
        }
        for (Customer c: customers) {
            averageWaitTime += c.getWaitTime();
        }
        averageWaitTime = averageWaitTime / customers.size();
        writer.write("Average wait time: " +averageWaitTime);
        writer.close();

        Collections.sort(lanes);
        for (CheckoutLane lane : lanes) {
            System.out.println("Lane #" + lane.getLaneID() + ": Processed " + lane.getProcessCount() + " "
            + lane.getTotalWaitTime()/lane.getProcessCount() 
            );
        }
    }

    private static CheckoutLane pickALane(Customer customer, ArrayList<CheckoutLane> lanes) {
        CheckoutLane l = null;
        ArrayList<CheckoutLane> regular = new ArrayList<>();
        ArrayList<CheckoutLane> express = new ArrayList<>();
        for (CheckoutLane currLane : lanes) {
            if(currLane instanceof ExpressLane){
                express.add(currLane);
            }else{
                regular.add(currLane);
            }
        }

        if(customer.expressEligible()){
            Collections.sort(express);
            l = express.get(0);
        }else{
            Collections.sort(regular);
            l = regular.get(0);
        }

        return l;
    }
}