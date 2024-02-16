package com.tqs108636.lab1_1stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> l;
    private int maxSize = -1;

    // bounded stack
    TqsStack(int maxSize) {
        this.maxSize = maxSize;
        l = new LinkedList<T>();
    }

    // unbounded stack
    TqsStack() {
        l = new LinkedList<T>();
    }

    T pop() {
        if (size() == 0)
            throw new NoSuchElementException("Cannot pop from an empty stack");
        return l.removeFirst();
    }

    int size() {
        return l.size();
    }

    T peek() {
        if (size() == 0) {
            throw new NoSuchElementException("Cannot peek into an empty stack");
        }
        return l.peek();
    }

    void push(T t) {
        if (size() == maxSize)
            throw new IllegalStateException("Cannot push into a full stack");
        l.addFirst(t);
    }

    boolean isEmpty() {
        return size() == 0;
    }
}
