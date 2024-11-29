package net.teaho.demo.ai.spring.rag.demo.domain;

import lombok.Data;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;

import java.util.List;

/**
 * @author teaho2015<at>gmail.com
 * @date 2024-09
 */
@Data
public class RagDTO {


    private List<DocmentDTO> documents;
    private String result;
    private ChatResponse chatResponse;


}
