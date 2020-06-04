package com.company.test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

    class UtilsTest {
        private UtilsTest(){
            throw new IllegalStateException("Utility class should not be instantiated as it contains static members"); //java adds an implicit public const
        }

    public static String removeDuplicates(String input) {
        StringBuilder last = new StringBuilder();
        input = input.toLowerCase();
        String stop = " a an the i in me my myself we be by for to the our ours ourselves you your yours yourself he him himself she her hers it its itself they them their themselves what which who whom this that these those am is are was were be been being have has had having do does did doing and but if or because as until while of at by for with about against between into through during before after above below to from up down in out on off over under again further then once here where there when why how all any both each few more most other some such no not nor only own same so than too very can will just don should now ";
        String[] stopping = stop.split(" ");
        String[] finalString = input.split(" ");
        HashSet<String> uniqueWords = new HashSet<>(Arrays.asList(finalString));
//        for(String p : finalString){
//            uniqueWords.add(p);
//        }

        for (String word : stopping) {
            if(uniqueWords.contains(word)){
                uniqueWords.remove(word);
            }
        }
        Iterator<String> it = uniqueWords.iterator();
        while(it.hasNext()){
            String toChange = it.next();
            last = last.append(" " +toChange);
        }
        String forReplacement = last.toString();
        String inputToLast = forReplacement.replaceAll("\\.", "") + " output";
        String toPerformOn = inputToLast.replaceAll(",", "") + " output";


        String[] finalToArray = toPerformOn.split(" ");
        HashSet<String> lastUnique = new HashSet<>(Arrays.asList(finalToArray));
        String t = "";
        Iterator<String> iterator = lastUnique.iterator();
        while(iterator.hasNext()){
            String toChange = (String)iterator.next(); //no need to cast as provided type to Iterator(String) not necessary
            t+=" " + toChange;
        }
        return t;

    }
}
//Strings are immutable objects, so concatenation doesn't simply add the new String to the end of the existing string. Instead, in each loop iteration, the first String is converted to an intermediate object type, the second string is appended, and then the intermediate object is converted back to a String. Further, performance of these intermediate operations degrades as the String gets longer. Therefore, the use of StringBuilder is preferred.