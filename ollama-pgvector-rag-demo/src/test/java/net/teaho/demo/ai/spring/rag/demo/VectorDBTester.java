package net.teaho.demo.ai.spring.rag.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@SpringBootTest
@RequiredArgsConstructor
@Slf4j
public class VectorDBTester {

    @Autowired
    private VectorStore vectorStore;

    @Test
    public void testVectorStore() {
        TextReader reader = new TextReader("classpath:kuangrenriji.txt");
        List<Document> docs = reader.get();
        log.info("=====docs:{}", docs.toString());

        ContentFormatTransformer contentFormatTransformer = new ContentFormatTransformer(DefaultContentFormatter.defaultConfig());
        log.info("=====transform content:{}", contentFormatTransformer.transform(docs));

        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        docs = tokenTextSplitter.split(docs);
        log.info("=====split docs:{}", docs.toString());
        vectorStore.add(docs);

    }

    @Test
    public void searchFromVectorStore() {
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query("鲁迅").withTopK(2));

        for (Document result : results) {
            System.out.println(result);
        }


    }



}
