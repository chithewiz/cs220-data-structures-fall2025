import java.lang.reflect.Array;
import java.util.Random;
import java.util.Stack;

public class SkipListIncomplete<E extends Comparable<E>> implements AmhSortedSet<E> {

    private Node<E> head;
    private int height;
    private int numElts = 0;
    private Random rand;
    private double p;

    private int countOps = 0;

    public boolean add(E x) {
        // find the predecessor nodes on each level of the list
        Stack<Node<E>> preds = findAllPreds(x);
        Node<E> pred0 = preds.peek();
        if(pred0.nextNodes[0] != null && pred0.nextNodes[0].data.equals(x)) return false; // elt x was already present

        // create a new node for elt x, and generate its height
        Node<E> newNode = new Node<E>(x, chooseHeight());
        int newHeight = newNode.getHeight();

        // increase the height of the head node, if needed
        if (newHeight > height) {
            if (newHeight > head.nextNodes.length-1) {
                Node<E>[] temp = (Node<E>[]) Array.newInstance(Node.class, newHeight+1);
                for(int i = 0; i < head.nextNodes.length; i++) {
                    temp[i] = head.nextNodes[i];
                }
                head.nextNodes = temp;
            }
            for(int i = newHeight; i > height; i--) {
                head.nextNodes[i] = newNode;
            }
            height = newHeight;
        }

        // add x after its predecessor on each level within x's height
        // YOU FILL IN THIS PART



        // now we have one more element stored
        numElts++;
        return true;
    }

    public E remove(E x) {
        // find the predecessor nodes on each level of the list
        Stack<Node<E>> preds = findAllPreds(x);

        // if the element wasn't present, nothing to return
        Node<E> pred0 = preds.peek();
        if(pred0.nextNodes[0] == null) return null; // empty list
        if(pred0.nextNodes[0] != null && !pred0.nextNodes[0].data.equals(x)) return null; // non-empty list, elt x wasn't present

        // if the elment was present, now we need to remove it from each level on which it appears
        // YOU FILL IN THIS PART



        // now we have one fewer element stored
        numElts--;
        return null; // placeholder, CHANGE THIS to return the correct thing
    }

    public E find(E x) {
        // find the predecessor nodes on each level of the list
        Stack<Node<E>> preds = findAllPreds(x);
        Node<E> level0 = preds.pop(); // the top of the stack is the predecessor on level 0
        if (level0.nextNodes[0] == null) return null;
        else return level0.nextNodes[0].data;
    }

    public int size() {
        return numElts;
    }

    protected Stack<Node<E>> findAllPreds(E x) {
        // this method should return a stack containing the predecessor nodes of element x on each level,
        // with the predecessor on level 0 at the top of the stack and the predecessor at the highest
        // level on the bottom of the stack
        // YOU FILL IN THIS PART




        return null; // placeholder, CHANGE THIS to return the correct thing
    }

    public void print() {
        for(int i = 0; i <= height; i++) {
            Node<E> y = head;
            while(y.nextNodes[i] != null) {
                y = y.nextNodes[i];
                System.out.print(y.data + " ");
            }
            System.out.println();
        }
    }


    public int getOps() {
        return countOps;
    }

    private int chooseHeight() {
        int level = 0;
        double flip = rand.nextDouble();
        while(flip < p) {
            level++;
            flip = rand.nextDouble();
        }
        return level;
    }

    public SkipListIncomplete() {
        head = new Node<E>(null, 0);
        height = 0;
        p = 0.5;
        rand = new Random();
    }

    public SkipListIncomplete(int seed) {
        head = new Node<E>(null, 0);
        height = 0;
        p = 0.5;
        rand = new Random(seed);
    }

    public SkipListIncomplete(double prob) {
        head = new Node<E>(null,0);
        height = 0;
        p = prob;
        rand = new Random();
    }

    public SkipListIncomplete(double prob, int seed) {
        head = new Node<E>(null,0);
        height = 0;
        p = prob;
        rand = new Random(seed);
    }

}
