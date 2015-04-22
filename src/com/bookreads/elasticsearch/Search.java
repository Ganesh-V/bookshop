package com.bookreads.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;

public class Search {

	public static SearchHits searchData() {
		Client client = ElasticSearch.INSTANCE.getClient();

		// MatchAll on the whole cluster with all default options
		SearchResponse esResult = client.prepareSearch("twitter").execute().actionGet();

		return esResult.getHits();
	}

	public static void main(String[] args) throws IOException {

	}

}
