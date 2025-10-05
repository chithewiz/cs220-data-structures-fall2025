
public class AmhArrayList<E> implements AmhList<E> {

    private Object[] _storage;
    private int _size;

    public AmhArrayList() {
        // BUGFIX #1: start with non-zero capacity so expandCapacity() can grow correctly.
        _storage = new Object[1];
        _size    = 0;
    }

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException, IllegalStateException {
        if (index < 0 || index > _size) {
            throw new IndexOutOfBoundsException(index);
        }
        // Ensure there is room
        if (_size == _storage.length) {
            expandCapacity();
        }
        // Shift elements to the right to make room at index
        for (int i = _size; i > index; i--) {
            _storage[i] = _storage[i - 1];
        }
        _storage[index] = element;
        _size += 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException(index);
        }
        return (E)_storage[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException(index);
        }
        E removed = (E)_storage[index];
        // Shift left to fill the gap
        for (int i = index; i < _size - 1; i++) {
            _storage[i] = _storage[i + 1];
        }
        _storage[_size - 1] = null; // avoid loitering
        _size -= 1;
        return removed;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException(index);
        }
        E old = (E)_storage[index];
        _storage[index] = element;
        return old;
    }

    @Override
    public int size() {
        return _size;
    }

    private void expandCapacity() throws IllegalStateException {
        // BUGFIX #2: when capacity is 0, doubling keeps it at 0; use max(1, len*2)
        int newCapacity = Math.max(1, _storage.length * 2);
        Object[] newStorage = new Object[newCapacity];
        for (int i = 0; i < _size; i++) {
            newStorage[i] = _storage[i];
        }
        _storage = newStorage;
    }
}