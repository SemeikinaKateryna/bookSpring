package com.example.bookspring.controller;

import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import com.example.bookspring.repository.BookRepository;
import com.example.bookspring.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BookController {

    BookRepository bookRepository;
    LibraryRepository libraryRepository;
    @GetMapping("/books")
    public String books(Model model){
        List<Book> allBooks = bookRepository.findAll();
        model.addAttribute("books",allBooks);
        return "books";
    }

    @PostMapping("/add_book")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam int pages,
                          @RequestParam int library){
        Optional<Library> libraryFinded = libraryRepository.findById(library);
        if(libraryFinded.isPresent()) {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setNumberOfPages(pages);
            book.setLibrary(libraryFinded.get());
            bookRepository.save(book);
        }
        return "redirect:/books";
    }

    @GetMapping("/book_library/{id}")
    public String libraryByBook(@PathVariable(name="id") int id, Model model){
        Optional<Library> libraryFinded = libraryRepository.findById(id);
        if(libraryFinded.isEmpty()){
            return "redirect:/libraries";
        }
        model.addAttribute("library", libraryFinded.get());
        return "library_by_book";
    }
}
