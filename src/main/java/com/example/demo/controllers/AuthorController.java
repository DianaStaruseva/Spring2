package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class AuthorController  {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/author")
    public String authorsMain(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author/author-main";
    }

    @GetMapping("/author/add")
    public String authorAdd(Model model)
    {
        return "author/author-add";
    }

    @PostMapping("/author/add")
    public String authorAdd(@RequestParam String name,
                            @RequestParam String patronymic,
                            @RequestParam String surname,
                            @RequestParam byte age,
                            @RequestParam short number_of_works, Model model)
    {
        Author author = new Author(name,patronymic,surname,age,number_of_works);
        authorRepository.save(author);
        return "redirect:/author";
    }

    @GetMapping("/author/filter")
    public String authorFilter(Model model)
    {
        return "author/author-filter";
    }

    @PostMapping("/author/filter/allResult")
    public String authorAllFilterResult(
            @RequestParam String surname,
            Model model)
    {
        List<Author> result = authorRepository.findBySurname(surname);
        model.addAttribute("result", result);
        return "author/author-filter";
    }

    @GetMapping("/author/{id}")
    public String authorDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author", res);
        if (!authorRepository.existsById(id)) {
            return "redirect:/author";
        }
        return "author/author-details";
    }

    @GetMapping("/author/{id}/edit")
    public String authorEdit(@PathVariable("id") long id, Model model) {

        if (!authorRepository.existsById(id)) {
            return "redirect:/author";
        }
        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author",res);
        return "author/author-edit";
    }

    @PostMapping("/author/{id}/edit")
    public String authorAuthorUpdate(@PathVariable("id") long id,
                                     @RequestParam String name,
                                     @RequestParam String patronymic,
                                     @RequestParam String surname,
                                     @RequestParam byte age,
                                     @RequestParam short number_of_works, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setName(name);
        author.setPatronymic(patronymic);
        author.setSurname(surname);
        author.setAge(age);
        author.setNumber_of_works(number_of_works);
        authorRepository.save(author);
        return "redirect:/author";
    }

    @PostMapping("/author/{id}/remove")
    public String authorAuthorDelete(@PathVariable("id") long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        authorRepository.delete(author);
        return "redirect:/author";
    }

}
