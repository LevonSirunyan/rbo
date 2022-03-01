package am.task.repositories;

import am.task.model.entity.Book;
import am.task.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CommonRepository<Book> {

    List<Book> findAllByFoolName(String foolName);

    List<Book> findAllByFoolNameAndUser(String foolName, User user);
}