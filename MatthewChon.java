import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class DistributionLinkedList {
    private final Double TEN_PROB_BOUND = .3,
                         HUND_PROB_BOUND = .9,
                         THOUS_PROB_BOUND = 1.0;

    //---------------- Node class --------------------
    private class Node {
        private Float data;
        private Node next;

        public Node(Float d, Node n) {
            data = d;
            next = n;
        }
        public Float data() { return data; }
        public Node next() { return next; }
        public void setData(Float newdata) { data = newdata; }
        public void setNext(Node newnode) { next = newnode; }
        public void printNode(File out) throws IOException {
            StringBuilder node = new StringBuilder();
            node.append("( " + data + " ) -> ");
            try {
                FileWriter write = new FileWriter(out);
                write.append(node);
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } //----------------------------------------------
    private Node head;
    private int size;

    public DistributionLinkedList() {
        head = null;
        size = 0;
    }
    public int size() { return size; }
    public boolean empty() { return size == 0; }
    private void insert(Node node) {
        if (empty()) {
            head = node;
        }
        else {   
            Node place = head;

            while (place.next() != null && place.next().data() > node.data()) {
                place = place.next();
            }
            node.setNext(place.next());
            place.setNext(node);
        }
        size++;
    }
    public void generate_node() {
        Random rand = new Random();
        Random num_rand = new Random();

        Double probability = rand.nextDouble();
        Float number = num_rand.nextFloat();

        /**
         * TO BE IMPLEMENTED
         */
    }
    public Float generate_percent(Float occurence) {
        return occurence / size * 100;
    }
    public void printList(File out) {
        try (FileWriter output = new FileWriter(out)) {
            System.out.println("The frequency of values 0 <= x <= 10 is :");
            System.out.println("The frequency of values 10 < x <= 100 is :");
            System.out.println("The frequency of values 100 < x <= 1000 is :");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
public class MatthewChon {
    public static void main(String [] args){
        String filename = "MatthewChon.txt";
        File outFile = new File(filename);
        DistributionLinkedList list = new DistributionLinkedList();
        list.generate_node();
        list.printList(outFile);
    }
}