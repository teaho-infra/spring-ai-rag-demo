package net.teaho.demo.ai.spring.rag.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * doc: https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html
 *
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@SpringBootTest
@Slf4j
public class ETLPipelineTester {

    @Value("classpath:bikes.json")
    private Resource jsonResource;

    @Value("classpath:text_source.txt")
    private Resource textResource;

    @Value("classpath:sample1.pdf")
    private Resource pdfResource;

    @Value("classpath:word-sample.docx")
    private Resource wordResource;

    @Test
    public void testTxtReader() {
        TextReader reader = new TextReader("classpath:kuangrenriji.txt");
        List<Document> docs = reader.get();
        log.info("=====docs:{}", docs.toString());

        ContentFormatTransformer contentFormatTransformer = new ContentFormatTransformer(DefaultContentFormatter.defaultConfig());
        log.info("=====transform content:{}", contentFormatTransformer.transform(docs));

        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        docs = tokenTextSplitter.split(docs);
        log.info("=====split docs:{}", docs.toString());

    }

    @Test
    public void testTikaReader() {
        {
            TikaDocumentReader reader = new TikaDocumentReader("classpath:kuangrenriji.docx");

            List<Document> docs = reader.get();
            log.info("=====docs:{}", docs.toString());

            ContentFormatTransformer contentFormatTransformer = new ContentFormatTransformer(DefaultContentFormatter.defaultConfig());
            log.info("=====transform content:{}", contentFormatTransformer.transform(docs));

            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
            docs = tokenTextSplitter.split(docs);
            log.info("=====split docs:{}", docs.toString());
        }

        {
            TikaDocumentReader pdfReader = new TikaDocumentReader("classpath:kuangrenriji.pdf");

            List<Document> docs = pdfReader.get();
            log.info("=====pdf docs:{}", docs.toString());

            ContentFormatTransformer contentFormatTransformer = new ContentFormatTransformer(DefaultContentFormatter.defaultConfig());
            log.info("=====transform pdf content:{}", contentFormatTransformer.transform(docs));

            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
            docs = tokenTextSplitter.split(docs);
            log.info("=====split pdf docs:{}", docs.toString());
        }

    }


}
