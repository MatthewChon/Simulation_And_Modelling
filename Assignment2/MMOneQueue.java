import java.util.ArrayList;

public class MMOneQueue {
    public static int max_customer = 10000;
    public static int max_debug = 20;

    private double clock;
    private ArrayList<Event> fel;
    private ArrayList<Event> del;

    private int customer_served = 0;
    
    private double idle_time = 0.0;
    private double queue_length = 0.0;
    private int simulated_time = 0;

    public MMOneQueue() {
        clock = 0.0;
        fel = new ArrayList<>();
        del = new ArrayList<>();
    }


    public void addEvent(int id) {
        simulation(new Event(id));
    }

    /**
     * Returns whether the simulation ended
     * @return  true if the simulation ended : false otherwise
     */
    public boolean end() {
        return customer_served == max_customer;
    }

    /**
     * Adds the size to the queue length
     * @param customer  size of queue
     */
    public void update_queue_length(double customer) {
        queue_length += customer;
    }
    /**
     * Updates the total interarrival time
     * @param time the current interarrival time
     */
    public void update_idle_time(double time) {
        idle_time += time;
    }
    /**
     * Simulates the mm1 queue
     * @param new_event
     */
    public void simulation(Event new_event) {
        if (new_event == null) {    // if no event occurred
            // Is the server currently 
            if (!fel.isEmpty()) {

                if (!del.isEmpty())
                    update_idle_time(-fel.get(0).arrival);

                clock = fel.get(0).end_of_service;
                fel.remove(0);
                customer_served++;

                if (!del.isEmpty() && clock >= del.get(0).arrival) {
                    update_idle_time(clock);
                    fel.add(del.remove(0));
                    fel.get(0).arrival = clock;
                    fel.get(0).end_of_service = fel.get(0).exponential_distribution(clock, Event.mu);
                }
            }
            else {
                    clock = del.get(0).arrival;
                    update_idle_time(clock);
                    fel.add(del.remove(0));
            }
        }
        else {
            if (clock == 0.0) { // if the simulation just started
                clock = new_event.arrival;
                fel.add(new_event);
                update_idle_time(clock);
            }
            else {
                if (!fel.isEmpty()) {
                    double early_time = 99.9;
                    for (Event event: del) {
                        if (clock < event.arrival && event.arrival < early_time) {
                            early_time = event.arrival;
                        }
                    }
                    if (early_time < fel.get(0).end_of_service) {
                        clock = early_time;
                        del.add(new_event);
                    }
                    else {
                        update_idle_time(-fel.get(0).arrival);
                        clock = fel.get(0).end_of_service;
                        fel.remove(0);
                        customer_served++;

                        if (!del.isEmpty() && clock >= del.get(0).arrival) {
                            update_idle_time(clock);
                            fel.add(del.remove(0));
                            fel.get(0).arrival = clock;
                            fel.get(0).end_of_service = fel.get(0).exponential_distribution(clock, Event.mu);
                            del.add(new_event);
                        }
                        else if (clock >= new_event.arrival) {
                            update_idle_time(clock);
                            fel.add(new_event);
                            fel.get(0).arrival = clock;
                            fel.get(0).end_of_service = fel.get(0).exponential_distribution(clock, Event.mu);

                        }
                        else {
                            del.add(new_event);
                        }
                    }
                }
                else {
                    if (fel.isEmpty() && del.isEmpty()) {   // there is an idle time
                        clock = new_event.arrival;
                        update_idle_time(clock);

                        fel.add(new_event);

                    }
                    else {
                        if (fel.isEmpty() && !del.isEmpty()) {  // if a service has ended
                            clock = del.get(0).arrival;
                            update_idle_time(clock);
                            fel.add(del.remove(0));
                            del.add(new_event);
                        }
                        else {
                            del.add(new_event);
                        }
                    }
                }
            }
        }
        simulated_time++;
        update_queue_length(del.size());
    }
    public void current_state() {
        System.out.println("===================================================================");
        System.out.println("Clock :" + clock);
        System.out.println("===================================================================");

        System.out.println("Future Event List :");
        if (fel.isEmpty()) {
            System.out.println("\tEMPTY");
        }
        else {
            for (Event event: fel) {
                System.out.println("[ " + event.name + " will end at " + event.end_of_service + " ]");
            }
        }
        System.out.println("Delayed Event List :");
        if (del.isEmpty()) {
            System.out.println("\tEMPTY");
        }
        else {
            for (Event event: del) {
                System.out.println("[ " + event.name + " arrived at " + event.arrival + " ]");
            }
        }
    }

    /**
     * Computes the average queue length
     * @return average queue length
     */
    public double avg_queue_length() {
        return queue_length / (simulated_time*1000);
    }
    /**
     * Computes the average idle time
     * @return average idle time
     */
    public double avg_idle_time() {
        return idle_time / customer_served;
    }
    public static void main(String[] args) {
        MMOneQueue mm1 = new MMOneQueue();
        int output = 0;
        for (int i = 0; i < max_customer; ++i) {
            mm1.addEvent(i);
            if (output++ < MMOneQueue.max_debug)
                mm1.current_state();
        }
        while (!mm1.end()) {
            mm1.simulation(null);
            if (output++ < MMOneQueue.max_debug)
                mm1.current_state();
        }
        System.out.println("\nThe average queue length of the queue is " + mm1.avg_queue_length());
        System.out.println("The average idle time of the queue is " + mm1.avg_idle_time());
    }
}
