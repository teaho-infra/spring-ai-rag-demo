package net.teaho.demo.ai.spring.rag.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.teaho.demo.ai.spring.rag.demo.service.RagService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@RestController
@RequestMapping("/rag")
@Tag(name = "RAG demo")
public class RagController {

	@Autowired
	private RagService ragService;

	@Operation(summary = "upload doc")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Object upload(@RequestPart("file") MultipartFile file) {

		ragService.parsingDocToFile(file);
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		return result;
	}

	@Operation(summary = "rss upload")
	@GetMapping("/rss/load")
	public Object loadRss() throws Exception {
		ragService.load();
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		return result;
	}

	@Operation(summary = "search vector doc")
	@GetMapping("/search")
	public List<Document> searchDoc(@RequestParam String query, @RequestParam(defaultValue = "3") int topK) {
		return ragService.searchVectorDB(query, topK);
	}

	@Operation(summary = "chat with llm")
	@GetMapping("/chat")
	public Object chat(@RequestParam String message) {
		return ragService.easyChat(message);
	}

	@Operation(summary = "chat with rag")
	@GetMapping("/chatWithRag")
	public Object chatWithRag(@RequestParam String message) {
		return ragService.easyRag(message);
	}



}
