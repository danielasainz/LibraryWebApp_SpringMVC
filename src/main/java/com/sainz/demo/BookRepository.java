package com.sainz.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface BookRepository extends CrudRepository<Book,Long> {

    ArrayList<Book> findAllByAvailable(boolean available);
    // SELECT * FROM Book
    // WHERE available = TRUE/FALSE

}
