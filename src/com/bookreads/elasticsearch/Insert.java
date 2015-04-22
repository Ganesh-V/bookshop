package com.bookreads.elasticsearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

public class Insert {
	public static String pushData(Map<String, Object> content, String index, String type) {
		Client client = ElasticSearch.INSTANCE.getClient();
		
		IndexResponse response = client.prepareIndex(index, type)
		        .setSource(content)
		        .execute()
		        .actionGet();
		
		String _id = response.getId();
		
		return _id;
	}
	
	@SuppressWarnings("unchecked")
	public static String pushData(String content, String index, String type) throws JsonParseException, JsonMappingException, IOException {
		
		Map<String, Object> result = new ObjectMapper().readValue(content, HashMap.class);
		return pushData(result, index, type);
	}
	
	public static void main(String[] args) throws IOException {
		new ObjectMapper();
		String content = new String(Files.readAllBytes(Paths.get("sample.txt")));
		pushData(content, "sample", "test");
	}
	
}

