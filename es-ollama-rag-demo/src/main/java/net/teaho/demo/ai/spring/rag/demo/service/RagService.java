package net.teaho.demo.ai.spring.rag.demo.service;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.teaho.demo.ai.spring.rag.demo.domain.DocmentDTO;
import net.teaho.demo.ai.spring.rag.demo.domain.RagDTO;
import net.teaho.demo.ai.spring.rag.demo.processor.RssProcessor;
import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RagService {

    private final VectorStore vectorStore;
    private final OllamaChatModel ollamaChatModel;
    private final RssProcessor rssProcessor;

    public void parsingDocToFile(MultipartFile file) {
        Resource resource = file.getResource();
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        List<Document> docs = reader.get();
        ContentFormatTransformer contentFormatTransformer = new ContentFormatTransformer(DefaultContentFormatter.defaultConfig());
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        docs = tokenTextSplitter.split(contentFormatTransformer.transform(docs));
        log.info("parsing doc:{} to into {} pieces.",file.getName(), docs.size());
        if (!CollectionUtils.isEmpty(docs)) {
            vectorStore.add(docs);
        }
    }

    public void loadRss() throws Exception {

        {
            String rss36kr = "https://36kr.com/feed-newsflash";
            List<SyndEntry> rssFeedContent = rssProcessor.fetchRssFeed(rss36kr);
            List<Document> docs = new ArrayList<>();

            for (SyndEntry syndEntry : rssFeedContent) {
                Document document = Document.builder().withId(UUID.randomUUID().toString())
                    .withContent(syndEntry.getTitle() + "\n" + syndEntry.getDescription().getValue())
                    .withMetadata("uri", syndEntry.getUri())
                    .withMetadata("source", StringUtils.hasText(syndEntry.getAuthor()) ? syndEntry.getAuthor() : syndEntry.getSource().getTitle())
                    .build();

                docs.add(document);
            }
            transformAndSaveVector(docs);
        }

        {
            String rss36kr = "https://rss.huxiu.com/";
            List<SyndEntry> rssFeedContent = rssProcessor.fetchRssFeed(rss36kr);
            List<Document> docs = new ArrayList<>();

            for (SyndEntry syndEntry : rssFeedContent) {
                Document document = Document.builder().withId(UUID.randomUUID().toString())
                    .withContent(syndEntry.getTitle() + "\n" + syndEntry.getDescription().getValue())
                    .withMetadata("uri", syndEntry.getUri())
                    .withMetadata("source", StringUtils.hasText(syndEntry.getAuthor()) ? syndEntry.getAuthor() : syndEntry.getSource().getTitle())
                    .build();

                docs.add(document);
            }
            transformAndSaveVector(docs);
        }


    }

    private void transformAndSaveVector(List<Document> docs) {
        ContentFormatTransformer contentFormatTransformer = new ContentFormatTransformer(DefaultContentFormatter.defaultConfig());
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        docs = tokenTextSplitter.split(contentFormatTransformer.transform(docs));
        if (!CollectionUtils.isEmpty(docs)) {
            vectorStore.add(docs);
        }
    }

    public List<Document> searchVectorDB(String query, int topK) {
        return vectorStore.similaritySearch(SearchRequest.query(query).withTopK(topK));
    }

    public String easyChat(String message) {
        String result = ollamaChatModel.call(message);
        return result;
    }

    public RagDTO easyRag(String query) {
        List<Document> documents = searchVectorDB(query, 4);
        RagDTO ragDTO = new RagDTO();
        List<DocmentDTO> docmentResp = new ArrayList<>();
        StringBuilder similarDoc = new StringBuilder();
        for (Document document : documents) {
            similarDoc.append(document.getContent()).append("\n");
            DocmentDTO dto = new DocmentDTO();
            dto.setContent(document.getContent());
            dto.setId(document.getId());
            dto.setMetadata(document.getMetadata());
            docmentResp.add(dto);
        }
        ragDTO.setDocuments(docmentResp);

        String prompt = """
            请基于下面的内容，回答这个问题"%s":
            %s
            """;
        String reply = ollamaChatModel.call(String.format(prompt, query, similarDoc.toString()));

        ragDTO.setResult(reply);
        return ragDTO;
    }




}
