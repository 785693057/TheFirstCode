package com.example.databasetest;

public class Book {
	private String name;
	private String author;
	private int pages;
	private double price;
	
	public Book(String name, String author, int pages, double price){
		this.name = name;
		this.author = author;
		this.pages = pages;
		this.price = price;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
}
