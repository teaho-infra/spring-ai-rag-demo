package net.teaho.demo.ai.spring.rag.demo;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import net.teaho.demo.ai.spring.rag.demo.processor.RssProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@SpringBootTest
@Slf4j
public class RssTester {

    @Autowired
    private RssProcessor rssProcessor;

    @Test
    public void testRss() throws Exception {

        List<SyndEntry> rssFeed = rssProcessor.fetchRssFeed("https://36kr.com/feed-newsflash");
//        List<SyndEntry> rssFeed = rssProcessor.fetchRssFeed("https://36kr.com/feed");

        List<Document> docs = new ArrayList<>();

        for (SyndEntry syndEntry : rssFeed) {
            Document document = Document.builder().withId(UUID.randomUUID().toString())
                .withContent(syndEntry.getDescription())

        }


        log.info(rssFeed.toString());
    }

}
