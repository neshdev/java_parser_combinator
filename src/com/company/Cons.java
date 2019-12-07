package com.company;

class FList<T>{

}

public class Cons<T> extends FList<T> {
    public T getHead() {
        return head;
    }

    public FList<T> getTail() {
        return tail;
    }

    private final T head;
    private final FList<T> tail;

    public Cons(T head, FList<T> tail){
        this.head = head;
        this.tail = tail;
    }

    @Override
    public String toString() {
        if (tail instanceof Empty){
            return head.toString();
        } else{
            return head.toString() + ":" +tail.toString();
        }

    }
}

class Empty<A> extends FList<A>
{
    static <A> Empty<A> empty(){
        return new Empty<A>();
    }

    @Override
    public String toString() {
        return "";
    }
}