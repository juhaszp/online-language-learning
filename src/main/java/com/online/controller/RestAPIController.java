package com.online.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.online.service.MemoryCardService;

@RestController
public class RestAPIController {
	private MemoryCardService memoryCardService;
	
	@GetMapping("/rest")
	public @ResponseBody String rest() {
		
		ArrayList<Word> words = getMemoryCardService().getWords(2907, (byte)1, (byte)2);
		
		List<String> wordList = words.stream()
			.map(word -> word.getWord())
			.collect(Collectors.toList());
		
		JSONArray jsonArray = new JSONArray(wordList);
		
		return jsonArray.toString();
	}
	
	public MemoryCardService getMemoryCardService() {
		return memoryCardService;
	}

	@Autowired
	public void setMemoryCardService(MemoryCardService memoryCardService) {
		this.memoryCardService = memoryCardService;
	}
}