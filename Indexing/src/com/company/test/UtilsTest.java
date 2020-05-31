package com.company.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class UtilsTest {
    public static String StringUtilsTest(String input) {
        String last = "";
        input = input.toLowerCase();
        System.out.println(input);
        String stop = " a an the i in me my myself we be by for to the our ours ourselves you your yours yourself he him himself she her hers it its itself they them their themselves what which who whom this that these those am is are was were be been being have has had having do does did doing and but if or because as until while of at by for with about against between into through during before after above below to from up down in out on off over under again further then once here where there when why how all any both each few more most other some such no not nor only own same so than too very can will just don should now ";
        String stops = ". ,";
        HashSet<String> uniqueWords = new HashSet<>();
        String[] stopping = stop.split(" ");
        String[] finalString = input.split(" ");
        for(String p : finalString){
            uniqueWords.add(p);
        }
        //System.out.println("Hashset unique words " + uniqueWords);
        for (String word : stopping) {
            if(uniqueWords.contains(word)){
                uniqueWords.remove(word);
            }
            //System.out.println(word);
            //input   = input.replaceAll(".", "");
        }
        System.out.println("Hashset unique words after 2" + uniqueWords);
        Iterator it = uniqueWords.iterator();
        while(it.hasNext()){
            String toChange = (String)it.next();
            last+=" " + toChange;
        }
        //System.out.println(last);
        String inputToLast = last.replaceAll("\\.", "") + " output";
        //System.out.println(inputToLast);
        String toPerformOn = inputToLast.replaceAll(",", "") + " output";
        //System.out.println(toPerformOn);
        //System.out.println("Hashset unique words after " + uniqueWords);

        //System.out.println(input + " input");
//        input = input.replaceAll("[\\d\\W]", " ");
//        //second attempt to remove duplicate words
//        String[] words = input.split(" ");

//        HashSet<String> unique = new HashSet<>();
//        String out="";
//        for(String u: words){
//            unique.add(u);
//            out += " " + u;
//        }
        HashSet<String> lastUnique = new HashSet<>();

        //return unique.toString();
        //return out.trim();
        return toPerformOn;

    }
}
