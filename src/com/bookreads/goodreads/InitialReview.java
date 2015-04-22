package com.bookreads.goodreads;

import static com.bookreads.goodreads.constants.Constants.APPLICATION_JSON;
import static com.bookreads.goodreads.constants.Constants.BOOKS;
import static com.bookreads.goodreads.constants.Constants.GOODREAD_AUTH_KEY;
import static com.bookreads.goodreads.constants.Constants.INITIAL_REVIEW_URL;
import static com.bookreads.goodreads.constants.Constants.ISBNS;
import static com.bookreads.goodreads.constants.Constants.KEY;
import static com.bookreads.goodreads.constants.Constants.OBJECT_MAPPER;
import static com.bookreads.goodreads.constants.Constants.STR_TRIM;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.collect.Sets;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @author Ganesh_V Read data using
 *         https://www.goodreads.com/book/review_counts.
 *         json?key=auth_key&isbns=9780747265009
 *
 */
public class InitialReview {
	
	
	
	/**
	 * To pass set of isbn and get its initial requests with goodreads _id for
	 * all isbn in the set.
	 * 
	 * @param isbnSet
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> getInitalReview(Set<String> isbnSet) {
		
		String isbns = isbnSet.stream().map(STR_TRIM).collect(Collectors.joining(","));
		return getInitalReview(isbns, GOODREAD_AUTH_KEY);
	}

	/**
	 * To pass single isbn and get its initial request which give goodreads _id.
	 * 
	 * @param isbn
	 * @return
	 */
	public static Map<String, String> getInitalReview(String isbn) {
		Map<String, List<Map<String, String>>> initalReview = getInitalReview(isbn, GOODREAD_AUTH_KEY);
		List<Map<String, String>> listOfBooks = initalReview.get(BOOKS);
		return (listOfBooks == null || listOfBooks.isEmpty()) ? listOfBooks.get(0) : Maps.newHashMap();

	}

	@SuppressWarnings("unchecked")
	private static Map<String, List<Map<String, String>>> getInitalReview(String isbn, String goodReadsAuthKey) {

		Map<String, List<Map<String, String>>> result = new HashMap<>();

		Client client = Client.create();
		WebResource webResource = client.resource(INITIAL_REVIEW_URL).queryParam(KEY, goodReadsAuthKey).queryParam(ISBNS, isbn);
		String response = webResource.type(APPLICATION_JSON).get(String.class);

		try {
			result = OBJECT_MAPPER.readValue(response, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static void main(String[] args) {

		Set<String> inputISBNSet = Sets.newHashSet("9780747265009", "2343243243232", "9780747265009", "9780708849330");
		Map<String, List<Map<String, String>>> initalReview = getInitalReview(inputISBNSet);
		System.out.print(initalReview);
	}


}
