package com.online.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online.service.MemoryCardService;

@Controller
public class MemoryCardController extends BaseController {
	
	private MemoryCardService memoryCardService;  
	
	@RequestMapping("/memory_card")
	public String memoryCard(Model model) {
		
		ArrayList<Word> words = getMemoryCardService().getWords(2907, (byte)1, (byte)2);
		
		model.addAttribute("page", "memory_card");
		model.addAttribute("words", words);
		
		addJSFileAttributeToModel(model);
		
		return "memory_card";
	}

	public MemoryCardService getMemoryCardService() {
		return memoryCardService;
	}

	@Autowired
	public void setMemoryCardService(MemoryCardService memoryCardService) {
		this.memoryCardService = memoryCardService;
	}
}