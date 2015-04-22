package com.bookreads.goodreads;

import static com.bookreads.goodreads.constants.ISBN.ISBN10;
import static com.bookreads.goodreads.constants.ISBN.ISBN13;
import static com.bookreads.goodreads.constants.Constants.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.elasticsearch.common.collect.Sets;

import com.bookreads.elasticsearch.Insert;
import com.bookreads.goodreads.constants.ISBN;

public class FetchGoodReads {

	public static void processingISBN(Map<String, String> fileDetail) throws IOException {

		String fileName = fileDetail.get(FILE_NAME);

		Set<String> inputISBNSet = Sets.newHashSet(Files.readAllLines(Paths.get(fileName)));

		Set<String> notFountSet = Sets.newHashSet();
		notFountSet.addAll(getISBNDetails(inputISBNSet, ISBN13, fileDetail));
		notFountSet.addAll(getISBNDetails(inputISBNSet, ISBN10, fileDetail));

		Map<String, Object> notFoundMap = new HashMap<>();
		notFoundMap.put(fileName, notFountSet);

		Insert.pushData(notFoundMap, BOOK_INDEX, NOT_IN_GOODREADS_TYPE);

	}

	private static Set<String> getISBNDetails(Set<String> isbnSet, ISBN isbn, Map<String, String> additionalAttr) {
		final Set<String> inputISBNSet = isbnSet.stream().filter(isbn.getPredicate()).collect(Collectors.toSet());
		Map<String, List<Map<String, String>>> initalReview = InitialReview.getInitalReview(inputISBNSet);

		// To capture it in exception
		String isbn10 = new String();
		String isbn13 = new String();
		Object id = null;

		Set<String> outputISBNSet = new HashSet<>();

		for (Map<String, String> book : initalReview.get(BOOKS)) {

			id = book.get(ID);
			isbn10 = book.get(ISBN10.getName());
			isbn13 = book.get(ISBN13.getName());

			Map<String, Object> bookDetail = new HashMap<>();
			bookDetail.put(ISBN10.getName(), isbn10);
			bookDetail.put(ISBN13.getName(), isbn13);

			for (Map.Entry<String, String> attr : additionalAttr.entrySet()) {
				bookDetail.put(attr.getKey(), attr.getValue());
			}

			try {
				Map<String, Map<String, Map<String, Object>>> detail = DetailReview.getDetailReview(id.toString());
				bookDetail.put(GOODREADS, detail.get(GOODREADS_RESPONSE));

				Insert.pushData(bookDetail, BOOK_INDEX, GOODREADS_TYPE);
				outputISBNSet.add(book.get(isbn.getName()));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.format("GoodReads detailed page failed to show : id=%s, isbn=%s, isbn13=%s", id, isbn10, isbn13);
			}

		}
		inputISBNSet.removeAll(outputISBNSet);
		return inputISBNSet;
	}
}
