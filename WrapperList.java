// ==============================================================================
// IMPORTS

import java.lang.IllegalStateException;
import java.util.LinkedList;
// ==============================================================================



// ==============================================================================
/**
 * Implement a simplified list using a standard <code>LinkedList</code>.
 *
 * @author Scott Kaplan -- sfkaplan@amherst.edu
 * @date   Mar 2021
 */
public class WrapperList <E> implements AmhList <E> {
// ==============================================================================


    
    // ==========================================================================
    /** The linked list in which the elements will be stored. */
    private LinkedList<E> _storage;
    // ==========================================================================


    
    // ==========================================================================
    /**
     * Constructor.  Create an empty list.
     */
    public WrapperList () {

	_storage = new LinkedList<E>();

    } // WrapperList ()
    // ==========================================================================


    
    // ==========================================================================
    /**
     * Add an element to this list at the given, indexed position.  The index
     * must be in range, or must be immediately after the last valid index, thus
     * appending the value to the list.
     *
     * @param index   The position at which to insert the element.
     * @param element The element to add.
     * @throws IndexOutOfBoundsException if {@code index < 0 || size < index}
     * @throws IllegalStateException  if the list cannot be expanded to
     *                                accomodate the additional element.
     */
    public void add (int index, E element) throws IndexOutOfBoundsException,
						  IllegalStateException {

        _storage.add(index, element);
        
    } // add ()
    // ==========================================================================

    

    // ==========================================================================
    /**
     * Retrieve the element at the given, indexed position.
     *
     * @param index The position from which to retrieve the element.
     * @return the value at the given position.
     * @throws IndexOutOfBoundsException if {@code index < 0 && size <= index}
     */
    public E get (int index) throws IndexOutOfBoundsException {

        return _storage.get(index);
        
    } // get ()
    // ==========================================================================



    // ==========================================================================
    /**
     * Remove an element from the given, indexed position.  The list size is
     * decremented, with the elements in higher position sliding down to fill in
     * the gap.
     *
     * @param index The position at which to remove an element.
     * @return the removed element.
     * @throws IndexOutOfBoundsException if {@code index < 0 && size <= index}
     */
    public E remove (int index) throws IndexOutOfBoundsException {

        return _storage.remove(index);
        
    } // remove ()
    // ==========================================================================

    

    // ==========================================================================
    /**
     * Replace the element at the given index with another, given element.
     *
     * @param index   The position at which to replace an element.
     * @param element The new element to place at the given position.
     * @return the removed element previously at the given position.
     * @throws IndexOutOfBoundsException if {@code index < 0 && size <= index}
     */
    public E set (int index, E element) throws IndexOutOfBoundsException {

        return _storage.set(index, element);
        
    } // set ()
    // ==========================================================================

    

    // ==========================================================================
    /**
     * Provide the number of elements in the list.
     *
     * @return the length of the list.
     */
    public int size () {

        return _storage.size();
        
    } // size ()
    // ==========================================================================



// ==============================================================================
} // class WrapperList
// ==============================================================================
