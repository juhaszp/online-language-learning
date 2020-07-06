package com.online.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.online.controller.Word;

@Service
public class MemoryCardService {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	public ArrayList<Word> getWords(int wordGroupId, byte fromLang, byte toLang) {
		
		final String uri = "https://online-szotanulas.hu/word_copulation_as3/get_words/{wordgroupid}/{fromlangid}/{tolangid}/{difficultylevel}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("wordgroupid", Integer.toString(wordGroupId));
	    params.put("fromlangid", Byte.toString(fromLang));
	    params.put("tolangid", Byte.toString(toLang));
	    params.put("difficultylevel", "_");
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class, params);
	    
	    ArrayList<Word> al = new ArrayList<Word>();
	    
	    for (String row: result.split(">>"))
	    {
	    	String[] wordWithAt = row.split("@@");
	    	al.add(new Word(wordWithAt[0], wordWithAt[0]));
	    	al.add(new Word(wordWithAt[0], wordWithAt[1]));
	    }
	    
	    Collections.shuffle(al);
	    
		return al;
	}
}
