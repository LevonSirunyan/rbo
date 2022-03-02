package am.task.repositories;

import am.task.model.entity.Book;
import am.task.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CommonRepository<Book> {

    List<Book> findAllByFoolName(String foolName);

    List<Book> findAllByFoolNameAndUser(String foolName, User user);

    Page<Book> findAllByUser(User user, Pageable pageable);
}