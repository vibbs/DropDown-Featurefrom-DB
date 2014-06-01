package com.luckypants.command;

import java.util.ArrayList;

import com.luckypants.model.Book;
import com.luckypants.mongo.ConnectionProvider;
import com.luckypants.mongo.BooksConnectionProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ListAllBooksCommand {

	public ArrayList<Book> execute() {
		BooksConnectionProvider booksConn = new BooksConnectionProvider();
		DBCollection booksCollection = booksConn.getCollection();

		DBCursor cursor = booksCollection.find();

		ArrayList<Book> books = new ArrayList<Book>();
		GetBookCommand getBook = new GetBookCommand();
		try {
			while (cursor.hasNext()) {
				Book b = getBook.execute("_id",
						cursor.next().get("_id").toString());
				books.add(b);
			}
		} finally {
			cursor.close();
		}
		return books;

	}
	
	
	public ArrayList<DBObject> execute2() {
		ConnectionProvider authorsConn = new ConnectionProvider();
		DBCollection authorsCollection = authorsConn.getCollection("authors");

		DBCursor cursor = authorsCollection.find();

		ArrayList<DBObject> authors = new ArrayList<DBObject>();
		try {
		   while(cursor.hasNext()) {
			   authors.add(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		return authors;

	}

	public static void main(String[] args) {
		ListAllBooksCommand listBooks = new ListAllBooksCommand();
		ArrayList<Book> list = listBooks.execute();
		System.out.println(list);

	}
}
