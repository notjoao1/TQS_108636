/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
        setC = BoundedSetOfNaturals.fromArray(new int[] { 50, 60 });
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @DisplayName("Adding a natural number should increase size by 1 and the set should contains() that value")
    @Test
    public void testAddElement() {
        setA = new BoundedSetOfNaturals(2);

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setA.add(11);
        assertTrue(setA.contains(11), "add: added element not found in set.");
        assertEquals(2, setA.size(), "add: elements count not as expected.");
    }

    @DisplayName("Adding a number that already exists throws IllegalArgumentException")
    @Test
    public void testAddExistingNumber() {
        setA = new BoundedSetOfNaturals(2);
        setA.add(10);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setA.add(10);
        }, "add: adding existing number did not throw IllegalArgumentException");
    }

    @DisplayName("Adding a number to a full set throws IllegalArgumentException")
    @Test
    public void testAddFullSet() {
        setA = new BoundedSetOfNaturals(1);
        setA.add(10); // set becomes full

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setA.add(10);
        }, "add: should throw IllegalArgumentException if adding to a full set");
    }

    @DisplayName("Adding a non-natural number should throw an IllegalArgumentException")
    @Test
    public void testAddInvalidNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setA.add(-10);
        }, "IllegalArgumentException expected when adding an invalid number to the set");
    }

    @DisplayName("Creating a set from an array with invalid numbers should throw an IllegalArgumentException error")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[] { 10, -20, -30 };

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @DisplayName("Adding to an array that would exceed maximum size of set throws IllegalArgumentException")
    @Test
    public void testAddFromArrayOverflowSize() {
        int[] elems = new int[] { 10, 20, 30 };
        setA.add(5); // becomes full (maxSize == 1)

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setA.add(elems);
        }, "add (array): adding an array that would overflow set didn't throw IllegalArgumentException");

    }

    @DisplayName("Two sets with different values return false for intersects()")
    @Test
    public void testIntersectsDifferentSets() {
        setA.add(1);
        // setB already setup with {10, 20, 30, 40, 50, 60}, check setUp() in this file

        Assertions.assertFalse(setA.intersects(setB),
                "intersects: two fully different sets returned true for intersects");
    }

    @DisplayName("Two sets with a common value return true for intersects()")
    @Test
    public void testIntersectsIntersectingSets() {
        // setB already setup with {10, 20, 30, 40, 50, 60}, check setUp() in this file
        // setC -> {50, 60} @ setUp()

        Assertions.assertTrue(setB.intersects(setC),
                "intersects: two sets with common values returned false for intersects");
    }

}
