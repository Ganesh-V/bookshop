package com.bookreads.goodreads.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.bookreads.elasticsearch.ElasticSearch;
import com.bookreads.goodreads.FetchGoodReads;

public class Main {

	private static final String INPUT_JSON = "input.json";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		List<Map<String, String>> inputConfig = new ObjectMapper().readValue(new String(Files.readAllBytes(Paths.get(INPUT_JSON))), List.class);
		for (Map<String, String> fileMap : inputConfig) {
			FetchGoodReads.processingISBN(fileMap);
		}
		ElasticSearch.INSTANCE.close();
	}
}
