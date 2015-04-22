package com.bookreads.goodreads;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.XML;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import static com.bookreads.goodreads.constants.Constants.*;

/**
 * Fetch detail from
 * https://www.goodreads.com/book/show.xml?key=auth_key&id=1162575
 * 
 * @author Ganesh_V
 *
 */
public class DetailReview {


	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static Map<String, Map<String, Map<String, Object>>> getDetailReview(String id) {
		return getDetailReview(id, GOODREAD_AUTH_KEY);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Map<String, Map<String, Object>>> getDetailReview(String id, String goodReadsAuthKey) {

		Client client = Client.create();
		Map<String, Map<String, Map<String, Object>>> result = new HashMap<>();

		WebResource webResource = client.resource(READ_DETAIL_URL).queryParam(KEY, goodReadsAuthKey).queryParam(ID, id);
		String response = webResource.type(APPLICATION_JSON).get(String.class);

		try {
			result = OBJECT_MAPPER.readValue(XML.toJSONObject(response).toString(), HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println(getDetailReview("1162575", GOODREAD_AUTH_KEY));
	}

}
