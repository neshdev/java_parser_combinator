package com.company;
import java.util.List;
import java.util.function.Function;

import static com.company.Tuple.*;
import static com.company.Parser.*;
import static com.company.ParseResults.*;

public class Utils {
    public static Parser<Character> item(){
        return new Parser<Character>(s ->{
            if (s.length() > 0){
                Character c = s.charAt(0);
                String rest = s.substring(1); //this is slow!
                return result(t(c,rest));
            } else {
                return ParseResults.empty();
            }
        });
    }

    public static Parser<Character> satisfy(Function<Character,Boolean> predicate){
        return new Parser<Character>(s -> {
            ParseResults<Character> r = parse(item()).apply(s);
            if (r.size() > 0){
                Tuple<Character,String> t = r.get(0);
                if (predicate.apply(t.getA())){
                    return r;
                } else {
                    return ParseResults.empty();
                }
            } else {
                return  ParseResults.empty();
            }
        });
    }

    public static Parser<Character> chr(Character chr){
        return satisfy(c-> c==chr);
    }

    public static Parser<Character> oneOf(List<Character> list){
        return satisfy(list::contains);
    }

    public static Parser<Character> noneOf(List<Character> list){
        return satisfy(c-> !list.contains(c));
    }

    public static Parser<String> str(String str){
        if (str.length() == 0){
            return pure(str);
        }
        return chr(str.charAt(0))       .bind(c ->
                str(str.substring(1))    .bind(cs ->
                        pure(c + cs)));

    }

    public static Parser<String> _str(String str){
        return str_fast(str).bind(cs -> pure(cs.reverse().toString()));
    }

    private static Parser<StringBuilder> str_fast(String str){
        if (str.length() == 0){
            return pure(new StringBuilder());
        }
        return chr(str.charAt(0))       .bind(c ->
                str_fast(str.substring(1))    .bind(cs ->
                        pure(cs.append(c))));

    }

    public static <A> Parser<FList<A>> many(Parser<A> p){
        return many1(p).alt(pure(new Empty<A>()));
    }

    public static <A> Parser many1(Parser<A> p){
        return  p           .bind(x ->
                many(p)     .bind(xs ->
                pure(new Cons(x,xs))        ));
    }



    public static <A> Either<ParseError,ParseResults<A>> test(String s, Parser<A> p){
        ParseResults<A> results = parse(p).apply(s);
        if (results.size() > 0){
            return new Right(results);
        } else{
            ParseError err = new ParseError(null);
            return new Left(err);
        }
    }
}
