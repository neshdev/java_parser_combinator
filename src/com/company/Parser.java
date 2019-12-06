package com.company;

import java.util.ArrayList;
import java.util.function.Function;
import static com.company.Tuple.*;

class ParseResults<A> extends ArrayList<Tuple<A,String>> {
    public static <A> ParseResults<A> result(Tuple<A,String> t){
        ParseResults<A> pr = new ParseResults<A>();
        pr.add(t);
        return pr;
    }

    public static <A> ParseResults<A> empty(){
        ParseResults<A> pr = new ParseResults<A>();
        return pr;
    }
}

class Parser<A> {
    private final Function<String,ParseResults<A>> f;

    public Parser(Function<String,ParseResults<A>> f){
        this.f =f;
    }

    public Parser<A> alt(Parser<A> a){
        return new Parser<>(s->{
            ParseResults<A> r = parse(this).apply(s);
            if (r.size() > 0){
                return r;
            } else {
                return parse(a).apply(s);
            }
        });
    }

    public <B> Parser<B> bind(Function<A,Parser<B>> f){
        return new Parser<B>(s-> {
           ParseResults<A> results = parse(this).apply(s);

           ParseResults<B> prb = ParseResults.empty();
           for (Tuple<A,String> r : results){
               Parser<B> pb = f.apply(r.getA());
               ParseResults<B> bResults = parse(pb).apply(r.getB());
               for (Tuple<B,String> br : bResults){
                   prb.add(br);
               }
           }
           return prb;
        });
    }

    public static <A> Parser<A> empty(){
        return new Parser<A>(s -> {
            return new ParseResults<A>();
        });
    }

    public static <B> Parser<B> pure(B b){
        return new Parser<B>(s -> {
            ParseResults<B> list = new ParseResults<B>();
            list.add(t(b,s));
            return list;
        });
    }

    public static <A> Function<String,ParseResults<A>> parse(Parser<A> p){
        return p.f;
    }

}