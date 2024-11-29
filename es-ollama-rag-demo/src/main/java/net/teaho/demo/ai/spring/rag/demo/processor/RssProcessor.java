package net.teaho.demo.ai.spring.rag.demo.processor;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

/**
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@Component
public class RssProcessor {

    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception {
        URL feedUrl = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));
        return feed.getEntries();
    }


}
