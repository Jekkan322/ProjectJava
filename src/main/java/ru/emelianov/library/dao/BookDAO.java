package ru.emelianov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.emelianov.library.models.Book;
import ru.emelianov.library.models.Person;

import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book",new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK where id=?",new BeanPropertyRowMapper<>(Book.class),id)
                .stream().findAny().orElse(null);
    }
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, age) VALUES(?, ?, ?)", book.getName(), book.getAuthor(),
                book.getAge());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, age=? WHERE id=?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getAge(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?",id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Person JOIN Book on Person.id=Book.person_id " +
                "WHERE Book.id=?",new BeanPropertyRowMapper<>(Person.class),id).stream().findAny();
    }

    public void release(int id){
        jdbcTemplate.update("UPDATE BOOK SET person_id=null WHERE id=?",id);
    }

    public void assign(Person person, int id){
        jdbcTemplate.update("UPDATE BOOK SET person_id=? WHERE id=?",person.getId(),id);
    }
}
