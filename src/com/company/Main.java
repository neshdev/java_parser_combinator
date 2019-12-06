package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import static com.company.Tuple.*;
import static com.company.Utils.*;
import static com.company.Parser.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        print(t(1,2));
        String s = "baaab";

        Parser<String> p =                    satisfy(c->c=='{')            .bind(
                                        c1 -> satisfy(c->c=='t')            .bind(
                                        c2 -> satisfy(c->c=='t')            .bind(
                                        c3 -> pure( (new StringBuilder()
                                                            .append(c1)
                                                            .append(c2)
                                                            .append(c3)
                                                            .toString()))
                                        )));

        Parser<String> p1 =  str("abb");
        Parser<String> p2 =  str("abc");
        Parser<String> p3 = p1.alt(p2);

        Parser<Character> p0 = chr('a');

        Parser<List<Character>> p4 = _many(p0);

        Either<ParseError,ParseResults<List<Character>>> results = test(s,p4);
        print(results.toString());
        print("Finished...");
    }

    static <T> void print(T statements){
        System.out.println(statements);
    }
}


