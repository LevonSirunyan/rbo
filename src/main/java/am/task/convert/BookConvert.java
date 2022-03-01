package am.task.convert;

import am.task.model.dto.book.BookPreviewDto;
import am.task.model.entity.Book;

public class BookConvert {
    public static BookPreviewDto bookPreviewDtoFromBook(Book book){
        return BookPreviewDto.builder().foolName(book.getFoolName()).userId(book.getUser().getId()).build();
    }
}
