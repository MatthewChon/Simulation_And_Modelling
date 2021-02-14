package Assignment1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class DistributionLinkedList {
    private final double TEN_PROB_BOUND = .3,
                         HUND_PROB_BOUND = .9,
                         THOUS_PROB_BOUND = 1.0;

    //---------------- Node class --------------------
    private class Node {
        private double data;
        private Node next;

        public Node(double d, Node n) {
            data = d;
            next = n;
        }
        public Double data() { return data; }
        public Node next() { return next; }
        public void setNext(Node newnode) { next = newnode; }
        public String toString() {
            StringBuilder node = new StringBuilder();
            node.append("( " + data + " ) -> ");
            return node.toString();
        }
    } //----------------------------------------------
    private Node head;
    private int size;
    private double ten = 0.0,
                   hundred = 0.0,
                   thousand = 0.0;

    public DistributionLinkedList() {
        head = new Node(-1.0, null);
        size = 0;
    }
    public int size() { return size; }
    public boolean empty() { return size == 0; }
    private void insert(Node node) { 
        Node place = head;

        while (place.next() != null && place.next().data() < node.data()) {
            place = place.next();
        }
        node.setNext(place.next());
        place.setNext(node);
        size++;
    }
    public void generate_node() {
        double probability = Math.random();
        double number = Math.random();

        if (probability <= TEN_PROB_BOUND) {
            number *= 10;
            insert(new Node(number, null));
        }
        else if (probability <= HUND_PROB_BOUND) {
            number *= 100;
            insert(new Node(number, null));
        }
        else if (probability <= THOUS_PROB_BOUND) {
            number *= 1000;
            insert(new Node(number, null));
        }
        increment_count(number);
    }
    public void increment_count(Double number) {
        if (number <= 10) {
            ten++;
        }
        else if (number <= 100) {
            hundred++;
        }
        else if (number <= 1000) {
            thousand++;
        }
    }
    public double generate_percent(Double occurence) {
        return (occurence / size)* 100;
    }
    public void printList(File out) {
        try (FileWriter output = new FileWriter(out)) {
            Node ptr = head.next();
            while(ptr != null) {
                output.append(ptr.toString());
                ptr = ptr.next();
            }
            System.out.println("The frequency of values 0 <= x <= 10 is : " + generate_percent(ten) + "%.");
            System.out.println("The frequency of values 10 < x <= 100 is : " + generate_percent(hundred) + "%.");
            System.out.println("The frequency of values 100 < x <= 1000 is : " + generate_percent(thousand) + "%.");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
public class MatthewChonA1 {
    public static void main(String [] args){
        int limit = 50000;
        String filename = "MatthewChon.txt";
        File outFile = new File(filename);
        DistributionLinkedList list = new DistributionLinkedList();
        for (int i = 0; i < limit; ++i) {
            list.generate_node();
        }
        list.printList(outFile);
    }
}