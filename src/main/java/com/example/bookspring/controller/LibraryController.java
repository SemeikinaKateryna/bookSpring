//package com.example.bookspring.controller;
//
//import com.example.bookspring.entity.Library;
//import com.example.bookspring.mysql.daos.MySqlLibraryDao;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@AllArgsConstructor
//public class LibraryController {
//    MySqlLibraryDao libraryRepository;
//
//    @GetMapping("/libraries")
//    public String libraries(Model model){
//        List<Library> allLibraries = libraryRepository.findAll();
//        model.addAttribute("libraries", allLibraries);
//        return "libraries";
//    }
//
//    @PostMapping("/add_library")
//    public String addLibrary(@RequestParam String address){
//        Library library = new Library();
//        library.setAddress(address);
//        libraryRepository.save(library);
//        return "redirect:/libraries";
//    }
//
//    @GetMapping("/library_books/{id}")
//    public String booksByLibrary(@PathVariable("id") int id, Model model){
//        Optional<Library> libraryFinded = libraryRepository.findById(id);
//        if(libraryFinded.isEmpty()){
//            return "redirect:/libraries";
//        }
//        model.addAttribute("library", libraryFinded.get());
//        model.addAttribute("booksByLibrary",libraryFinded.get().getBooks());
//        return "books_by_library";
//    }
//}
