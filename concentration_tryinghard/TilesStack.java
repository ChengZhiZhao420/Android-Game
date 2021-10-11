package com.example.concentration_tryinghard;

import java.util.EmptyStackException;

public class TilesStack<T> {
    private T[] stack;
    private static int initialCapacity = 40;
    private int topIndex;

    public TilesStack(){
        @SuppressWarnings("unchecked")
        T[] tempStack = (T[]) new Object[initialCapacity];
        stack = tempStack;
        topIndex = -1;
    }

    /**
     * Push item into the stack
     * @param component DisplayComponent
     */
    public void push(T component){
        topIndex++;
        stack[topIndex] = component;
    }

    /**
     * Pop item from the stack
     * @return  DisplayComponent
     */
    public T pop(){
        T temp = null;
        if(isEmpty()){
            throw new EmptyStackException();
        }
        else{
            temp = stack[topIndex];
            stack[topIndex] = null;
            topIndex--;
        }
        return temp;
    }

    /**
     * Check the item from the top of the stack
     * @return  the top item of the stack
     */
    public T peek(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        else{
            return stack[topIndex];
        }
    }

    /**
     * Clear the stack
     */
    public void clear(){
        stack = null;
    }

    /**
     * Check whether or not the stack is empty
     * @return  true if the stack is empty, otherwise false
     */
    public boolean isEmpty() {
        return topIndex == -1;
    }

    /**
     * Get the size of the stack
     * @return size
     */
    public int getSize(){
        int size = topIndex + 1;

        return size;
    }



}
