package com.company;

class Tuple<A,B>{
    private final A a;
    private final B b;

    public Tuple(A a, B b){
        this.a = a;
        this.b = b;
    }

    public B getB() {
        return b;
    }

    public A getA() {
        return a;
    }

    @Override
    public String toString() {
        return "(" + getA().toString() + "," + getB().toString()  +")";
    }

    public static <A,B> Tuple<A,B> t(A a, B b){
        return new Tuple<>(a,b);
    }

    public static <A,B> A fst(Tuple<A,B> t){
        return t.getA();
    }

    public static <A,B> B snd(Tuple<A,B> t){
        return t.getB();
    }
}
