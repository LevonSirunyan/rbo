package am.task.convert;

import am.task.model.dto.book.BookListPreviewDto;
import am.task.model.dto.book.BookPreviewDto;
import am.task.model.entity.Book;
import org.springframework.data.domain.Page;

public class BookConvert {
    public static BookPreviewDto bookPreviewDtoFromBook(Book book) {
        return BookPreviewDto.builder().foolName(book.getFoolName()).userId(book.getUser().getId()).build();
    }

    public static Page<BookListPreviewDto> listBookListPreviewDtoFromBooks(Page<Book> books) {
        return books.map(book -> bookListPreviewDtoFromBook(book));
    }

    private static BookListPreviewDto bookListPreviewDtoFromBook(Book book) {
        return BookListPreviewDto.builder().id(book.getId()).foolName(book.getFoolName()).userId(book.getUser().getId()).build();
    }
}
