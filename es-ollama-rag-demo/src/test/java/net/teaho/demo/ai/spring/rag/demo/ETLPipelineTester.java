package net.teaho.demo.ai.spring.rag.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

/**
 * doc: https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html
 *
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
public class ETLPipelineTester {

    @Value("classpath:bikes.json")
    private Resource jsonResource;

    @Value("classpath:text_source.txt")
    private Resource textResource;

    @Value("classpath:sample1.pdf")
    private Resource pdfResource;

    @Value("classpath:word-sample.docx")
    private Resource wordResource;



}
