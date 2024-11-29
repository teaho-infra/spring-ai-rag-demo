package net.teaho.demo.ai.spring.rag.demo.domain;

import lombok.Data;

import java.util.Map;

/**
 * @author teaho2015<at>gmail.com
 * @date 2024-09
 */
@Data
public class DocmentDTO {

    private String id;
    private Map<String, Object> metadata;
    private String content;

}
