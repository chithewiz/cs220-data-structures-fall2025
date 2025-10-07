import java.lang.reflect.Array;
import java.util.Random;
import java.util.Stack;

public class SkipList<E extends Comparable<E>> implements AmhSortedSet<E> {

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
        int curLevel = 0;
        while(preds.size() > 0 && curLevel <= newHeight) {
            Node leveliPred = preds.pop();
            newNode.nextNodes[curLevel] = leveliPred.nextNodes[curLevel];
            leveliPred.nextNodes[curLevel] = newNode;
            curLevel++;
        }

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
        Node<E> toRemove = pred0.nextNodes[0];
        int h = toRemove.getHeight();
        int curLevel = 0;
        while(preds.size() > 0 && curLevel <= h) {
            Node leveliPred = preds.pop();
            leveliPred.nextNodes[curLevel] = toRemove.nextNodes[curLevel];
            curLevel++;
        }

	// now we have one fewer element stored
        numElts--;
        return toRemove.data;
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

    // this method returns a stack containing the predecessor nodes of element x on each level,
    // with the predecessor on level 0 at the top of the stack and the predecessor at the highest
    // level on the bottom of the stack
    protected Stack<Node<E>> findAllPreds(E x) {
        countOps = 0;
        Stack<Node<E>> preds = new Stack<Node<E>>();
        Node<E> curNode = head;
        int curLevel = height;

	// walk down the levels
        while(curLevel >= 0) {
	    // walk across the current level until we reach the predecessor of x
            while(curNode.nextNodes[curLevel] != null && curNode.nextNodes[curLevel].data.compareTo(x) < 0) {
                curNode = curNode.nextNodes[curLevel];
                countOps++;
            }
            preds.push(curNode); // before we go down a level, add the predecessor on this level to our list
            curLevel--;
            countOps++;
        }
        return preds;
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

    public SkipList() {
        head = new Node<E>(null, 0);
        height = 0;
        p = 0.5;
        rand = new Random();
    }

    public SkipList(int seed) {
        head = new Node<E>(null, 0);
        height = 0;
        p = 0.5;
        rand = new Random(seed);
    }

    public SkipList(double prob) {
        head = new Node<E>(null,0);
        height = 0;
        p = prob;
        rand = new Random();
    }

    public SkipList(double prob, int seed) {
        head = new Node<E>(null,0);
        height = 0;
        p = prob;
        rand = new Random(seed);
    }

}
