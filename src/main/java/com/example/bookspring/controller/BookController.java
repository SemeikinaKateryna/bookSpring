package com.example.bookspring.controller;

import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import com.example.bookspring.repository.BookRepository;
import com.example.bookspring.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public String searchByTitle (@RequestParam String title, Model model){
        List<Book> booksFinded = bookRepository.findBooksByTitleOrderByAuthorAsc(title);
        if(booksFinded.isEmpty()){
            return "redirect:/books";
        }
        model.addAttribute("books", booksFinded);
        return "books_by_title";
    }

    @GetMapping("/edit_book")
    public String editBook(@RequestParam int id, Model model){
        Optional<Book> bookFinded = bookRepository.findById(id);
        if(bookFinded.isEmpty()){
            return "redirect:/books";
        }
        model.addAttribute("book",bookFinded.get());
        return "edit_book";
    }

    @PostMapping("/update_book")
    public String updateBook(@RequestParam("id") int id, @RequestParam("title") String title,
                             @RequestParam("author") String author, @RequestParam("pages") int pages,
                             @RequestParam("library") int library){
        Optional<Book> bookFinded = bookRepository.findById(id);
        if(bookFinded.isEmpty()){
            return "redirect:/books";
        }
        Book book = bookFinded.get();
        book.setTitle(title);
        book.setAuthor(author);
        book.setNumberOfPages(pages);
        book.setLibrary(libraryRepository.findById(library).get());
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete_book")
    public String deleteBook(@RequestParam("id")int id){
        bookRepository.deleteById(id);
        return "redirect:/books";
    }
}
