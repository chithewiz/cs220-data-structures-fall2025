import java.lang.reflect.Array;

public class Node<E> {

    protected E data;
    protected Node<E>[] nextNodes;


    public int getHeight() {
        return nextNodes.length - 1;
    }

    public Node(E elt, int height){
        data = elt;
        nextNodes = (Node<E>[]) Array.newInstance(Node.class, height+1);
    }

}
