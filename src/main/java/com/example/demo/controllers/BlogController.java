package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController  {
    @Autowired
    private PostRepository postRepository;


    @GetMapping("/blog")
    public String blogMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog/blog-main";
    }

   @GetMapping("/blog/add")
    public String blogAdd(Model model)
    {
        return "blog/blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog/blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> result = postRepository.findByTitleContains(title);
        // List<Post> result = postRepository.findLikeTitle(title);
        model.addAttribute("result", result);
        return "blog/blog-filter";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        return "blog/blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model) {

        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog/blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogBlogDelete(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

    //Контроллер для Книг
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @GetMapping("/book")
//    public String booksMain(Model model) {
//       Iterable<Book> books = bookRepository.findAll();
//       model.addAttribute("books", books);
//        return "book/book-main";
//    }
//
//    @GetMapping("/book/add")
//    public String bookAdd(Model model)
//    {
//        return "book/book-add";
//    }
//
//    @PostMapping("/book/add")
//    public String bookAdd(@RequestParam String name,
//                          @RequestParam String author,
//                          @RequestParam int publication,
//                          @RequestParam double price,
//                          @RequestParam byte edition, Model model)
//    {
//        Book book = new Book(name, author, publication, price, edition);
//        bookRepository.save(book);
//        return "redirect:/book";
//    }
//
//    @GetMapping("/book/filter")
//    public String bookFilter(Model model)
//    {
//        return "book/book-filter";
//    }
//
//    @PostMapping("/book/filter/result")
//    public String bookResult(@RequestParam String name, Model model)
//    {
//        List<Book> result = bookRepository.findByNameContains(name);
//        model.addAttribute("result", result);
//        return "book/book-filter";
//    }
//
//    @PostMapping("/book/filter/allResult")
//    public String bookAllFilterResult(
//            @RequestParam String name,
//            Model model
//    ) {
//        List<Book> result = bookRepository.findByName(name);
//        model.addAttribute("result", result);
//        return "book/book-filter";
//    }


    //Контроллер для авторов

//    @Autowired
//    private AuthorRepository authorRepository;
//
//    @GetMapping("/author")
//    public String authorsMain(Model model) {
//        Iterable<Author> authors = authorRepository.findAll();
//        model.addAttribute("authors", authors);
//        return "author/author-main";
//    }
//
//    @GetMapping("/author/add")
//    public String authorAdd(Model model)
//    {
//        return "author/author-add";
//    }
//
//    @PostMapping("/author/add")
//    public String authorAdd(@RequestParam String name,
//                          @RequestParam String patronymic,
//                            @RequestParam String surname,
//                          @RequestParam byte age,
//                          @RequestParam short numberOfWorks, Model model)
//    {
//        Author author = new Author(name,patronymic,surname,age,numberOfWorks);
//        authorRepository.save(author);
//        return "redirect:/author";
//    }
//
//    @GetMapping("/author/filter")
//    public String authorFilter(Model model)
//    {
//        return "author/author-filter";
//    }
//
//    @PostMapping("/author/filter/allResult")
//    public String authorAllFilterResult(
//            @RequestParam String surname,
//            Model model)
//    {
//        List<Author> result = authorRepository.findBySurname(surname);
//        model.addAttribute("result", result);
//        return "author/author-filter";
//    }
}
