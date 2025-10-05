
// ==============================================================================
// IMPORTS
import java.lang.IllegalStateException;
// ==============================================================================

// Singly/doubly link node (kept name Link<E> to match template)
class Link<E> {
    public Link<E> next;
    public Link<E> prev;
    public E       value;

    public Link(E v) {
        this.value = v;
    }
}

// ==============================================================================
public class AmhLinkedList<E> implements AmhList<E> {
// ==============================================================================

    // Pointers and size
    private Link<E> _head;
    private Link<E> _tail;
    private int     _size;

    public AmhLinkedList() {
        _head = null;
        _tail = null;
        _size = 0;
    }

    // Walk to the node at position index in [0, _size)
    private Link<E> walk(int index) {
        // Defensive check
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException(index);
        }
        // Small optimization: walk from nearest end
        if (index < (_size / 2)) {
            Link<E> cur = _head;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur;
        } else {
            Link<E> cur = _tail;
            for (int i = _size - 1; i > index; i--) {
                cur = cur.prev;
            }
            return cur;
        }
    }

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException, IllegalStateException {
        if (index < 0 || index > _size) {
            throw new IndexOutOfBoundsException(index);
        }
        Link<E> node = new Link<>(element);

        if (_size == 0) {
            // empty list
            _head = _tail = node;
        } else if (index == 0) {
            // insert at head
            node.next = _head;
            _head.prev = node;
            _head = node;
        } else if (index == _size) {
            // insert at tail
            _tail.next = node;
            node.prev = _tail;
            _tail = node;
        } else {
            // insert in middle before current at index
            Link<E> cur = walk(index);
            Link<E> prev = cur.prev;
            prev.next = node;
            node.prev = prev;
            node.next = cur;
            cur.prev = node;
        }
        _size += 1;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        return walk(index).value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) throws IndexOutOfBoundsException {
        Link<E> cur = walk(index);
        E old = cur.value;
        cur.value = element;
        return old;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) throws IndexOutOfBoundsException {
        Link<E> cur = walk(index);
        E old = cur.value;

        if (cur.prev != null) {
            cur.prev.next = cur.next;
        } else {
            _head = cur.next;
        }
        if (cur.next != null) {
            cur.next.prev = cur.prev;
        } else {
            _tail = cur.prev;
        }
        _size -= 1;
        return old;
    }
}
// ==============================================================================