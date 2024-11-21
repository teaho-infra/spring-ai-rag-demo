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

@RestController
@RequestMapping("/rag")
@Tag(name = "RAG demo")
public class RagController {

	@Operation(summary = "上传文档")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity upload(@RequestPart("file") MultipartFile file) {
		return ResponseEntity.ok("success");
	}

	@Operation(summary = "搜索文档")
	@GetMapping("/search")
	public List<Document> searchDoc(@RequestParam String keyword) {
		return new ArrayList<>();
	}

	@Operation(summary = "问答文档")
	@GetMapping("/chat")
	public Object chat(@RequestParam String message) {
		return "abc";
	}


}
