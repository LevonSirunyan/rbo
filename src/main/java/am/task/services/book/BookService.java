package am.task.services.book;

import am.task.exceptions.EntityNotFoundException;
import am.task.exceptions.MessageException;
import am.task.model.dto.book.AddBookDto;
import am.task.model.dto.book.AdminAddBookDto;
import am.task.model.dto.book.BookPreviewDto;
import am.task.model.dto.book.EditBookDto;

public interface BookService {
    Long add(AddBookDto addBookDto) throws EntityNotFoundException, MessageException;

    Long add(AdminAddBookDto adminAddBookDto) throws EntityNotFoundException, MessageException;

    Long edit(EditBookDto editBookDto) throws EntityNotFoundException, MessageException;

    BookPreviewDto getBook(long bookId) throws EntityNotFoundException, MessageException;

    boolean deleteBook(long bookId) throws EntityNotFoundException;
}
