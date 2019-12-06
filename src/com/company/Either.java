package com.company;

public abstract class Either<L,R> {
}

class Left<L> extends Either<L,Object>{
    private L l;

    public Left(L l){

        this.l = l;
    }

    public L getL() {
        return l;
    }

    @Override
    public String toString() {
        return "error";
    }
}

class Right<R> extends Either<Object,R>{
    private R r;

    public Right(R r){

        this.r = r;
    }

    public R getR() {
        return r;
    }

    @Override
    public String toString() {
        return  r.toString();
    }
}
