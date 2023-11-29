package com.example.bookspring.controller;

import com.example.bookspring.dao.DaoFactory;
import com.example.bookspring.dao.IDao;
import com.example.bookspring.dao.TypeDao;
import com.example.bookspring.entity.Author;
import com.example.bookspring.entity.Book;
import com.example.bookspring.entity.Library;
import com.example.bookspring.mysql.daos.MySqlAuthorDao;
import com.example.bookspring.mysql.daos.MySqlLibraryDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    IDao<Book> bookRepository;
    MySqlLibraryDao libraryRepository;

    MySqlAuthorDao authorRepository;

    public BookController() {
        this.bookRepository = DaoFactory.getDAOInstance(TypeDao.MY_SQL);
        this.libraryRepository = new MySqlLibraryDao();
        this.authorRepository = new MySqlAuthorDao();
    }

    @GetMapping("/books")
    public String books(Model model){
        List<Book> allBooks = bookRepository.findAll();
        model.addAttribute("books",allBooks);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        List<Library> libraries = libraryRepository.findAll();
        model.addAttribute("libraries", libraries);
        return "books";
    }

    @PostMapping("/add_book")
    public String addBook(@RequestParam String title,
                          @RequestParam int author,
                          @RequestParam int pages,
                          @RequestParam int year,
                          @RequestParam int library){
        Optional<Library> libraryFound = Optional.ofNullable(libraryRepository.findById(library));
        if(libraryFound.isPresent()) {
            Optional<Author> authorFounded = Optional.ofNullable(authorRepository.findById(author));
            if(authorFounded.isPresent()) {
                Book book = new Book.Builder()
                        .addTitle(title)
                        .addLibrary(libraryFound.get())
                        .addPages(pages)
                        .addYear(year)
                        .addAuthor(authorFounded.get())
                        .build();

                bookRepository.insert(book);
                return "redirect:/books";
            }
            return "redirect:/books";
        }
        return "redirect:/books";
    }

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
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
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



//    @GetMapping("/edit_book_author")
//    public String editBookAuthor(@RequestParam("id") int authorId, Model model){
//        Optional<Author> authorFound = Optional.ofNullable(authorRepository.findById(authorId));
//        if(authorFound.isEmpty()){
//            return "redirect:/books";
//        }
//        model.addAttribute("author",authorFound.get());
//        List<Book> books = bookRepository.findAll();
//        model.addAttribute("books", books);
//        return "/edit_author";
//
//    }
//
//    @PostMapping("/update_book_author")
//    public String updateBookAuthor(@RequestParam("id") int id,
//                               @RequestParam("fullName") String fullName,
//                               @RequestParam("country") String country){
//        Optional<Author> authorFound = Optional.ofNullable(authorRepository.findById(id));
//        if(authorFound.isEmpty()){
//            return "redirect:/books";
//        }
//        Author author = authorFound.get();
//        author.setFullName(fullName);
//        author.setCountry(country);
//        authorRepository.update(author);
//        return "redirect:/books";
//    }

    @GetMapping("/delete_from_book_author")
    public String deleteFromBookAuthor(@RequestParam("authorId") int authorId,
                                       @RequestParam("bookId") int bookId){
        authorRepository.deleteAuthorFromBook(bookId, authorId);
        return "redirect:/books";
    }

    @PostMapping("/add_author_to_book")
    public String addAuthorToBook(@RequestParam("bookId") int bookId,
                                  @RequestParam("authorId") int authorId){
        authorRepository.addAuthorToBook(bookId, authorId);
        return "redirect:/books";
    }

}
