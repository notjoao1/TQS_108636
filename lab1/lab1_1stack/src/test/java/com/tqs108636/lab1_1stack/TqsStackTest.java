package com.tqs108636.lab1_1stack;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TqsStackTest {
    TqsStack<Integer> stack;

    // Runs always before every test, to make sure we have an empty stack
    // before running tests
    @BeforeEach
    void setup() {
        stack = new TqsStack<Integer>();
    }

    @DisplayName("Stack is empty and has size 0 after being created")
    @Test
    void testEmptyOnConstruction() {
        // assess
        Assertions.assertTrue(stack.size() == 0);
        Assertions.assertTrue(stack.isEmpty() == true);
    }

    @DisplayName("After 'n' pushes, stack isn't empty and has size 'n'")
    @Test
    void testSizeAfterPushes() {
        // setup
        int n = 10;

        // exercise
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        // assess
        Assertions.assertTrue(stack.isEmpty() == false);
        Assertions.assertTrue(stack.size() == n);
    }

    // If you push a value to the stack, popping a value will return the same value
    @DisplayName("After pushing a value, the subsequent pop will return the same value")
    @Test
    void testPopAfterPush() {
        // setup
        Integer integer = 123;

        // exercise
        stack.push(123);
        Integer expectedInteger = stack.pop();

        // assess
        Assertions.assertTrue(integer == expectedInteger);
    }

    @DisplayName("After a value is pushed, the subsequent peek will return the same value")
    @Test
    void testPeekAfterPush() {
        // setup
        Integer integer = 123;

        // exercise
        stack.push(integer);
        int originalSize = stack.size();
        Integer expectedInteger = stack.peek();

        // assess
        Assertions.assertEquals(originalSize, stack.size()); // size didn't change on peek
        Assertions.assertTrue(integer == expectedInteger);
    }

    @DisplayName("A pop on an empty stack throws a NoSuchElementException exception")
    @Test
    void testEmptyPop() {
        // exercise and assert
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        }, "NoSuchElementException was expected, no elements left to pop");
    }

    @DisplayName("Peeking into an empty stack throws a NoSuchElementException exception")
    @Test
    void testEmptyPeek() {
        // exercise and assert
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        }, "NoSuchElementException was expected, no elements to peek");
    }

    @DisplayName("For bounded stacks, pushing onto a full stack throws IllegalStateException")
    @Test
    void testFullStackPush() {
        // setup
        stack = new TqsStack<Integer>(1);

        // exercise
        stack.push(10);

        // exercise + assert
        Assertions.assertThrows(IllegalStateException.class, () -> {
            stack.push(1);
        }, "Stack was full and a value was pushed, so IllegalStateException was thrown");
    }

}
