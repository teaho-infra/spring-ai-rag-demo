package net.teaho.demo.ai.spring.rag.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@SpringBootTest
@Slf4j
public class ApplicationTester {

    @Autowired
    OllamaChatModel ollamaChatModel;

    @Test
    public void testOllamaChat() {
        log.info("chat result: {}", ollamaChatModel.call("hello"));
    }





}
