package com.bookreads.goodreads.constants;

import java.util.function.Function;

import org.codehaus.jackson.map.ObjectMapper;

public class Constants {
	public static final String ID = "id";
	public static final String KEY = "key";
	public static final String ISBNS = "isbns";
	public static final String APPLICATION_JSON = "application/json";
	public static final String GOODREAD_AUTH_KEY = "I5ewTSjPENHV6nWtLF1MOg";
	
	public static final String INITIAL_REVIEW_URL = "https://www.goodreads.com/book/review_counts.json";
	public static final String READ_DETAIL_URL = "https://www.goodreads.com/book/show.xml";

	
	public static final String BOOKS = "books";
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static final String FILE_NAME = "fileName";
	public static final String GOODREADS = "goodreads";
	public static final String GOODREADS_RESPONSE = "GoodreadsResponse";
	
	public static final String BOOK_INDEX = "book";
	public static final String NOT_IN_GOODREADS_TYPE = "not_in_goodreads";
	public static final String GOODREADS_TYPE =  "goodreads";
	
	public static Function<String, String> STR_TRIM = value -> value.trim();
	
}

