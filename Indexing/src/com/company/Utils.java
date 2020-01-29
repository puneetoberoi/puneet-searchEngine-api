package com.company;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String StringUtils(String toReduce) throws IOException {
        toReduce = toReduce.toLowerCase().trim();
        String stop = "a an the i in me my myself we by for our ours ourselves you your yours yourself he him himself she her hers it its itself they them their themselves what which who whom this that these those am is are was were be been being have has had having do does did doing and but if or because as until while of at by for with about against between into through during before after above below to from up down in out on off over under again further then once here where there when why how all any both each few more most other some such no not nor only own same so than too very can will just don should now";

        String[] stopping = stop.split(" ");
        for (String word : stopping) {
            toReduce   = toReduce.toLowerCase().replaceAll(word.toLowerCase() + " ", "");
        }
        toReduce = toReduce.replaceAll("[^a-zA-Z0-9\\s]", " ");
        //second attempt to remove duplicate words
        String[] words = toReduce.split(" ");

        Map<String, Integer> map = new HashMap<>();
        int i=0;
        for(String w : words){
            map.put(w,++i);
        }
        String out="";
        for(Map.Entry m:map.entrySet()){
            out=out+" "+m.getKey();
        }

        return out.trim();

    }
}
