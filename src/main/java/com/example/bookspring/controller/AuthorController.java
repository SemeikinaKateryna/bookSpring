package com.example.bookspring.controller;

import com.example.bookspring.entity.Author;
import com.example.bookspring.mysql.daos.MySqlAuthorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {
    MySqlAuthorDao authorRepository;

    public AuthorController() {
        this.authorRepository = new MySqlAuthorDao();
    }

    @GetMapping("/authors")
    public String authors(Model model){
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @PostMapping("/add_author")
    public String addAuthor(@RequestParam("fullName") String fullName,
                          @RequestParam("country") String country) {
        Author author = new Author.Builder()
                .addFullName(fullName)
                .addCountry(country)
                .build();
        authorRepository.insert(author);

        return "redirect:/authors";
    }

    @GetMapping("/delete_author")
    public String deleteAuthor(@RequestParam("id")int id){
        authorRepository.delete(id);
        return "redirect:/authors";
    }

    @GetMapping("/edit_author")
    public String editAuthor(@RequestParam int id, Model model){
        Optional<Author> authorFound = Optional.ofNullable(authorRepository.findById(id));
        if(authorFound.isEmpty()){
            return "redirect:/authors";
        }
        model.addAttribute("author",authorFound.get());
        return "edit_author";
    }

    @PostMapping("/update_author")
    public String updateAuthor(@RequestParam("id") int id,
                               @RequestParam("fullName") String fullName,
                                @RequestParam("country") String country){
        Optional<Author> authorFound = Optional.ofNullable(authorRepository.findById(id));
        if(authorFound.isEmpty()){
            return "redirect:/authors";
        }
        Author author = authorFound.get();
        author.setFullName(fullName);
        author.setCountry(country);
        authorRepository.update(author);
        return "redirect:/authors";
    }
}
