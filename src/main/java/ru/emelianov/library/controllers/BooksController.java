package ru.emelianov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.emelianov.library.dao.BookDAO;
import ru.emelianov.library.dao.PersonDAO;
import ru.emelianov.library.models.Book;
import ru.emelianov.library.models.Person;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO BookDAO, PersonDAO personDAO) {
        this.bookDAO = BookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllBook(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("{id}")
    public String getBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book",bookDAO.show(id));
        Optional<Person> owner= bookDAO.getBookOwner(id);

        if(owner.isPresent())
            model.addAttribute("owner",owner.get());
        else
            model.addAttribute("people",personDAO.index());
        return "books/show";
    }


    @GetMapping("new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,
                             @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@ModelAttribute("person") Person person,@PathVariable("id") int id){
        bookDAO.assign(person,id);
        return "redirect:/books/"+id;
    }
}
