package com.company;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
                String rest = s.substring(1);
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
        StringBuilder s = new StringBuilder();
        Parser<Character> p = chr(str.charAt(0));
        for (Character chr : str.substring(1).toCharArray()){
            p = p.bind(c -> {
                s.append(c);
                return chr(chr);
            });
        }
        return p.bind(c -> pure(s.append(c).toString()));
    }

    public static <A> Parser<List<A>> _many(Parser<A> p){
        return _many1(p).alt(pure(new ArrayList<A>()));
    }

    public static <A> Parser<List<A>> _many1(Parser<A> p){
        List<A> list = new ArrayList<>();
        Parser<List<A>> r = p.bind(a -> {
            list.add(a);
            return _many(p);
        });
        return r.bind(a-> {
            list.addAll(a);
            return  pure(list);
        });
    }

    public static <A> Parser<List<A>> many(Parser<A> p){
        return new Parser<List<A>>(
                s-> {
                    String cache = s;
                    ArrayList<A> list = new ArrayList<A>();
                    while (true){
                        ParseResults<A> r = parse(p.alt(Parser.empty())).apply(cache);
                        if (r.size() == 0)
                            break;
                        list.add(r.get(0).getA());
                        cache = r.get(0).getB();
                    }
                    return result(t(list,cache));
                });
    }

    public static <A> Parser<List<A>> many1(Parser<A> p){
        return new Parser<List<A>>(
                s-> {
                    String cache = s;
                    ArrayList<A> list = new ArrayList<A>();
                    ParseResults<A> r0 = parse(p).apply(cache);
                    if (r0.size() > 0){
                        list.add(r0.get(0).getA());
                        cache = r0.get(0).getB();
                        while (true){
                            ParseResults<A> r = parse(p.alt(Parser.empty())).apply(cache);
                            if (r.size() == 0)
                                break;
                            list.add(r.get(0).getA());
                            cache = r.get(0).getB();
                        }
                        return result(t(list,cache));
                    } else
                        return ParseResults.empty();
                });
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
