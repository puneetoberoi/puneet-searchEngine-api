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
        String stop = " a an the i in me my myself we be by for to the our ours ourselves you your yours yourself he him himself she her hers it its itself they them their themselves what which who whom this that these those am is are was were be been being have has had having do does did doing and but if or because as until while of at by for with about against between into through during before after above below to from up down in out on off over under again further then once here where there when why how all any both each few more most other some such no not nor only own same so than too very can will just don should now ";
        HashSet<String> uniqueWords = new HashSet<>();
        String[] stopping = stop.split(" ");
        String[] finalString = input.split(" ");
        for(String p : finalString){
            uniqueWords.add(p);
        }
        for (String word : stopping) {
            if(uniqueWords.contains(word)){
                uniqueWords.remove(word);
            }
        }
        Iterator it = uniqueWords.iterator();
        while(it.hasNext()){
            String toChange = (String)it.next();
            last+=" " + toChange;
        }
        String inputToLast = last.replaceAll("\\.", "") + " output";
        //System.out.println(inputToLast);
        String toPerformOn = inputToLast.replaceAll(",", "") + " output";

        HashSet<String> lastUnique = new HashSet<>();
        String finalToArray[] = toPerformOn.split(" ");
        for(String x : finalToArray){
            //System.out.println(x + " one string");
            lastUnique.add(x);
        }
        //System.out.println(lastUnique + "set");
        String t = "";
        Iterator iterator = lastUnique.iterator();
        while(iterator.hasNext()){
            String toChange = (String)iterator.next();
            t+=" " + toChange;
        }
        return t;

    }
}
