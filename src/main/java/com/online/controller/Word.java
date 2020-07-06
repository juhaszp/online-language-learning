package com.online.controller;

public class Word {
	String name;
	String word;
	
	public Word(String name, String word) {
		super();
		this.name = name;
		this.word = word;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
}