package com.example.bookspring.controller;

import com.example.bookspring.dao.DaoFactory;
import com.example.bookspring.dao.IDao;
import com.example.bookspring.dao.TypeDao;
import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.daos.MySqlBookDao;
import com.example.bookspring.mysql.daos.MySqlLibraryDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    IDao<Book> bookRepository;
    MySqlLibraryDao libraryRepository;

    public BookController() {
        this.bookRepository = DaoFactory.getDAOInstance(TypeDao.MY_SQL);
        this.libraryRepository = new MySqlLibraryDao();
    }

    @GetMapping("/books")
    public String books(Model model){
        List<Book> allBooks = bookRepository.findAll();
        model.addAttribute("books",allBooks);
        return "books";
    }

//    @PostMapping("/add_book")
//    public String addBook(@RequestParam String title,
//                          @RequestParam String author,
//                          @RequestParam int pages,
//                          @RequestParam int library){
//        Optional<Library> libraryFound = Optional.ofNullable(libraryRepository.findById(library));
//        if(libraryFound.isPresent()) {
//            Book book = new Book();
//            book.setTitle(title);
//            book.setAuthor(author);
//            book.setPages(pages);
//            book.setLibrary(libraryFound.get());
//            bookRepository.insert(book);
//        }
//        return "redirect:/books";
//    }

    @GetMapping("/book_library/{id}")
    public String libraryByBook(@PathVariable(name="id") int id, Model model){
        Optional<Library> libraryFound = Optional.ofNullable(libraryRepository.findById(id));
        if(libraryFound.isEmpty()){
            return "redirect:/libraries";
        }
        model.addAttribute("library", libraryFound.get());
        return "library_by_book";
    }

    @GetMapping("/edit_book")
    public String editBook(@RequestParam int id, Model model){
        Optional<Book> bookFound = Optional.ofNullable(bookRepository.findById(id));
        if(bookFound.isEmpty()){
            return "redirect:/books";
        }
        model.addAttribute("book",bookFound.get());
        List<Library> libraries = libraryRepository.findAll();
        model.addAttribute("libraries", libraries);
        return "edit_book";
    }

    @PostMapping("/update_book")
    public String updateBook(@RequestParam("id") int id, @RequestParam("title") String title,
                              @RequestParam("pages") int pages, @RequestParam("year") Integer year,
                             @RequestParam("library") int library){
        Optional<Book> bookFound = Optional.ofNullable(bookRepository.findById(id));
        if(bookFound.isEmpty()){
            return "redirect:/books";
        }
        Book book = bookFound.get();
        book.setLibrary(libraryRepository.findById(library));
        book.setTitle(title);
        book.setPages(pages);
        book.setYear(year);
        bookRepository.update(book);
        return "redirect:/books";
    }

    public List<Book> searchByName(String param){
        String parameter = "%";
        String parameterParam = parameter.concat(param);
        String parameterFinal = parameterParam.concat("%");
        return bookRepository.findAllByTitle(parameterFinal);
    }

    @GetMapping("/search")
    public String search(@RequestParam("param") String param, Model model){
        List<Book> booksFounded = searchByName(param);
        model.addAttribute("parameter", param);
        if(!booksFounded.isEmpty()){
            model.addAttribute("booksFounded", booksFounded);
            return "/books_founded";
        }else {
            return "redirect:/books";
        }
    }

    @GetMapping("/delete_book")
    public String deleteBook(@RequestParam("id")int id){
        bookRepository.delete(id);
        return "redirect:/books";
    }
}
