package com.bookreads.goodreads.constants;

import java.util.function.Predicate;

public enum ISBN {
	ISBN10("isbn", a -> a.length() == 10), ISBN13("isbn13", a -> a.length() == 13);

	private final String name;
	private final Predicate<String> predicate;

	ISBN(String name, Predicate<String> predicate) {
		this.name = name;
		this.predicate = predicate;
	}

	public String getName() {
		return name;
	}

	public Predicate<String> getPredicate() {
		return predicate;
	}

}
