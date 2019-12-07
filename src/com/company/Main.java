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

        Parser<String> p0 = _str("aac");
        Parser<FList<String>> p4 = many(p0);
        String s = "aacaacaacaacaacaacaacaad";
        Either<ParseError,ParseResults<FList<String>>> results = test(s,p4);
        print(results.toString());
        print("Finished...");
    }

    static <T> void print(T statements){
        System.out.println(statements);
    }
}


