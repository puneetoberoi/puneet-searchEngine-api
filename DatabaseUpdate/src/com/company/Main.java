package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	    new FirstDBUpdate(); //This is the first step if starting from scratch.
        new SecondDBUpdate();

//        Document document;
//        try {
//            //Get Document object after parsing the html from given url.
//            document = Jsoup.connect(
//                    "https://www.w3schools.com/howto/howto_css_searchbar.asp")
//                    .get();
//
//            //Get description from document object.
//            String description =
//                    document.select("meta[name=description]").get(0)
//                            .attr("content");
//            //Print description.
//            System.out.println("Meta Description: " + description);
//
//            //Get keywords from document object.
//            String keywords =
//                    document.select("meta[name=keywords]").first()
//                            .attr("content");
//            //Print keywords.
//            System.out.println("Meta Keyword : " + keywords);
//            Elements hTags = document.select("h1, h2, h3, h4, h5, h6");
//            Elements h1Tags = hTags.select("h1");
//            System.out.println(h1Tags.text() + " wmk");
//
//        } catch (final Exception | Error ignored){
//        }
    }
}
