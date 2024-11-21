package net.teaho.demo.ai.spring.rag.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.document.Document;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author teaho2015<at>gmail.com
 * @date 2024-11
 */
@RestController
@RequestMapping("/rag")
@Tag(name = "RAG demo")
public class RagController {

	@Operation(summary = "upload doc")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity upload(@RequestPart("file") MultipartFile file) {
		return ResponseEntity.ok("success");
	}

	@Operation(summary = "search vector doc")
	@GetMapping("/search")
	public List<Document> searchDoc(@RequestParam String keyword) {
		return new ArrayList<>();
	}

	@Operation(summary = "chat with llm")
	@GetMapping("/chat")
	public Object chat(@RequestParam String message) {
		return "abc";
	}

	@Operation(summary = "chat with rag")
	@GetMapping("/chatWithRag")
	public Object chatWithRag(@RequestParam String message) {
		return "abc";
	}


}
