package com.sainz.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


@Controller
public class HomeController {

    //Autowiring allows you to connect to your book repository. Have to create a local instance of my BookRepository which is bookRepo
    @Autowired
    BookRepository bookRepo;

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/add")
    public String addBook(Model model) {

        //aBook is a pointer to a space
        model.addAttribute("aBook", new Book());
        // Need an object to connect the controller and the view

        return "addBook";
    }

    @PostMapping("/save")
    //Book aBook is another storage space - from the reference "aBook."  Need to be able to save an object to your repository.
    public String saveBook(@ModelAttribute("aBook") Book bookToSave, Model model) {
        //above now giving an actual name to my Book
        bookRepo.save(bookToSave);
        //model.addAttribute("message", "Book has been saved successfully.");
        //return "saveBook";
        // add -> process -> list
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String listBook(Model model) {
        //creating a new reference, "bookslist" - making a reference - referring to a bunch of things inside repository
        //the purpose of this function is to show all the books in my repository. The output is going to be a list of things
        model.addAttribute("bookslist", bookRepo.findAll());
        return "listBook";
    }

    @RequestMapping("/changestatus/{id}")
    //path variable comes from the path - for all the combined ids that are available, generating all at once
    public String BorrowReturn(@PathVariable("id") long id) {
        Book thisBook = bookRepo.findById(id).get();

        //comparison operator ==  --> compares whether the operator is true or false
        if (thisBook.isAvailable() == true) {
            thisBook.setAvailable(false);
        } else {
            thisBook.setAvailable(true);
        }
        bookRepo.save(thisBook);
        return "redirect:/list";
    }
    @RequestMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("aBook", bookRepo.findById(id).get());
        return "addBook";
    }
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id){
        bookRepo.deleteById(id);
        return "redirect:/list";

    }
}
