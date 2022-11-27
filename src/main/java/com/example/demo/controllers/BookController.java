package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.Post;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public String booksMain(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/book-main";
    }

    @GetMapping("/book/add")
    public String bookAdd(Model model)
    {
        return "book/book-add";
    }

    @PostMapping("/book/add")
    public String bookAdd(@RequestParam String name,
                          @RequestParam String author,
                          @RequestParam int publication,
                          @RequestParam double price,
                          @RequestParam byte edition, Model model)
    {
        Book book = new Book(name, author, publication, price, edition);
        bookRepository.save(book);
        return "redirect:/book";
    }

    @GetMapping("/book/filter")
    public String bookFilter(Model model)
    {
        return "book/book-filter";
    }

    @PostMapping("/book/filter/result")
    public String bookResult(@RequestParam String name, Model model)
    {
        List<Book> result = bookRepository.findByNameContains(name);
        model.addAttribute("result", result);
        return "book/book-filter";
    }

    @PostMapping("/book/filter/allResult")
    public String bookAllFilterResult(
            @RequestParam String name,
            Model model
    ) {
        List<Book> result = bookRepository.findByName(name);
        model.addAttribute("result", result);
        return "book/book-filter";
    }

    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        if (!bookRepository.existsById(id)) {
            return "redirect:/book";
        }
        return "book/book-details";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable("id") long id, Model model) {

        if (!bookRepository.existsById(id)) {
            return "redirect:/book";
        }
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book",res);
        return "book/book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookBookUpdate(@PathVariable("id") long id,
                                 @RequestParam String name,
                                 @RequestParam String author,
                                 @RequestParam int publication,
                                 @RequestParam double price,
                                 @RequestParam byte edition, Model mode
    ) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(author);
        book.setPublication(publication);
        book.setPrice(price);
        book.setEdition(edition);
        bookRepository.save(book);
        return "redirect:/book";
    }

    @PostMapping("/book/{id}/remove")
    public String bookBookDelete(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return "redirect:/book";
    }
}
