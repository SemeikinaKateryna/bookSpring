package com.example.bookspring.controller;

import com.example.bookspring.dao.interfaces.ILibraryDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.daos.MySqlAuthorDao;
import com.example.bookspring.mysql.daos.MySqlLibraryDao;
import com.example.bookspring.observer.Observer;
import com.example.bookspring.observer.display.LoggingDisplayAuthor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class LibraryController {
    ILibraryDao libraryRepository;

    public LibraryController() {
        this.libraryRepository = new MySqlLibraryDao();
    }

    @GetMapping("/libraries")
    public String libraries(Model model){
        List<Library> allLibraries = libraryRepository.findAll();
        model.addAttribute("libraries", allLibraries);
        return "libraries";
    }

    @PostMapping("/add_library")
    public String addLibrary(@RequestParam("address") String address){
        Library library = new Library.Builder()
                .addAddress(address)
                .build();
        libraryRepository.insert(library);
        return "redirect:/libraries";
    }

    @GetMapping("/delete_library")
    public String deleteLibrary(@RequestParam("id") int id){
        libraryRepository.delete(id);
        return "redirect:/libraries";
    }
}
