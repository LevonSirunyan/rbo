package am.task.services.book;

import am.task.exceptions.EntityNotFoundException;
import am.task.exceptions.MessageException;
import am.task.model.dto.book.*;
import org.springframework.data.domain.Page;

public interface BookService {
    Long add(AddBookDto addBookDto) throws EntityNotFoundException, MessageException;

    Long add(AdminAddBookDto adminAddBookDto) throws EntityNotFoundException, MessageException;

    Long edit(EditBookDto editBookDto) throws EntityNotFoundException, MessageException;

    BookPreviewDto getBook(long bookId) throws EntityNotFoundException, MessageException;

    boolean deleteBook(long bookId) throws EntityNotFoundException;

    Page<BookListPreviewDto> getAllBooks(int page, int size) throws EntityNotFoundException;

    Page<BookListPreviewDto> getAllBooksAdmin(int page, int size, Long userId) throws EntityNotFoundException;
}
