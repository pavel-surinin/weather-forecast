package lt.soup;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Pavel on 2017-04-20.
 */
public class SoupUtils {
    static Logger logger = Logger.getLogger(SoupUtils.class);

    /**
     * Downloading html via Jsoup library for link passed in params
     *
     * @param link  link to scrap
     * @return      returns Document, Jsoup parsed objext
     */
    public static Document getPage(String link) {
        String linkCorrect = parseLink(link);
        try {
            return Jsoup.connect(linkCorrect.toString()).userAgent("Mozilla").get();
        } catch (IOException e) {
            logger.warn("Failed to load resource " + link);
        }
        return null;
    }

    /**
     * Parses link to link with correct slashes (http://a//b//c///) -> (http://a/b/c)
     *
     * @param link  http link
     * @return      link with // after http and / for each argument except last (http://a/b/c)
     */
    public static String parseLink(String link) {
        StringBuilder linkWithCorrectSlashes = new StringBuilder();
        String[] linkParts = link.split("/");
        for (int i = 0; i < linkParts.length; i++) {
            if (!linkParts[i].equals("")) {
                linkWithCorrectSlashes.append(linkParts[i]);
                if (i == 0) {
                    linkWithCorrectSlashes.append("//");
                } else if (i != linkParts.length - 1) {
                    linkWithCorrectSlashes.append("/");
                }
            }
        }
        return linkWithCorrectSlashes.toString();
    }
}
