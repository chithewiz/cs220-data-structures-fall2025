// ==============================================================================
/**
 * AmhList
 *
 * A simplified <code>List</code> interface for linear, indexed containers.
 *
 * @author Scott Kaplan -- sfkaplan@amherst.edu
 * @date   Feb 2021
 */

public interface AmhList <E> {
// ==============================================================================


    
    // ==========================================================================
    /**
     * Add an element to this list at the given, indexed position.
     * The index must be in range, or must be immediately after the
     * last valid index, thus appending the value to the list.
     *
     * @param index   The position at which to insert the element.
     * @param element The element to add.
     * @throws IndexOutOfBoundsException if {@code index < 0 || size < index}
     * @throws IllegalStateException  if the list cannot be expanded to
     *                                accomodate the additional element.
     */
    public void add (int index, E element) throws IndexOutOfBoundsException,
						  IllegalStateException;
    // ==========================================================================

    

    // ==========================================================================
    /**
     * Retrieve the element at the given, indexed position.
     *
     * @param index The position from which to retrieve the element.
     * @return the value at the given position.
     * @throws IndexOutOfBoundsException if {@code index < 0 || size <= index}
     */
    public E get (int index) throws IndexOutOfBoundsException;
    // ==========================================================================



    // ==========================================================================
    /**
     * Remove an element from the given, indexed position.  The list size is
     * decremented, with the elements in higher position sliding down to fill in
     * the gap.
     *
     * @param index The position at which to remove an element.
     * @return the removed element.
     * @throws IndexOutOfBoundsException if {@code index < 0 || size <= index}
     */
    public E remove (int index) throws IndexOutOfBoundsException;
    // ==========================================================================

    

    // ==========================================================================
    /**
     * Replace the element at the given index with another, given element.
     *
     * @param index   The position at which to replace an element.
     * @param element The new element to place at the given position.
     * @return the removed element previously at the given position.
     * @throws IndexOutOfBoundsException if {@code index < 0 || size <= index}
     */
    public E set (int index, E element) throws IndexOutOfBoundsException;
    // ==========================================================================

    

    // ==========================================================================
    /**
     * Provide the number of elements in the list.
     *
     * @return the length of the list.
     */
    public int size ();
    // ==========================================================================


    
// ==============================================================================
} // interface AmhList
// ==============================================================================
