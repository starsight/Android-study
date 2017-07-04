// BookManager.aidl
package com.wenjiehe.android_study.restart;

// Declare any non-default types here with import statements
import com.wenjiehe.android_study.restart.Book;


interface BookManager {
    List<Book> getBooks();
    Book getBook(String bookName);
    int getBookCount();

    void addBook(in Book book);
}
