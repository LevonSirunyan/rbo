package am.task.services.book;

import am.task.enums.UserRoleEnum;
import am.task.enums.UserStatusEnum;
import am.task.exceptions.EntityNotFoundException;
import am.task.exceptions.MessageException;
import am.task.model.dto.book.*;
import am.task.model.entity.Book;
import am.task.model.entity.User;
import am.task.repositories.BookRepository;
import am.task.services.utils.FindUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static am.task.convert.BookConvert.bookPreviewDtoFromBook;
import static am.task.convert.BookConvert.listBookListPreviewDtoFromBooks;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Lazy
    private final BookRepository bookRepository;
    @Lazy
    private final FindUser findUser;

    public BookServiceImpl(BookRepository bookRepository, FindUser findUser) {
        this.bookRepository = bookRepository;
        this.findUser = findUser;
    }

    @Override
    public Long add(AddBookDto addBookDto) throws EntityNotFoundException, MessageException {
        User user = findUser.findUserByToken();
        if (UserStatusEnum.BLOCK.equals(user.getUserStatusEnum())) {
            throw new MessageException("User is blocked!!!");
        }
        checkExist(user, addBookDto.getFoolName());
        return addBook(addBookDto.getFoolName(), user);
    }

    @Override
    public Long add(AdminAddBookDto adminAddBookDto) throws EntityNotFoundException, MessageException {
        User user = adminAddBookDto.getUserId() == null ? findUser.findUserByToken() : findUser.findUserById(adminAddBookDto.getUserId());
        checkExist(user, adminAddBookDto.getFoolName());
        return addBook(adminAddBookDto.getFoolName(), user);
    }

    @Override
    public Long edit(EditBookDto editBookDto) throws EntityNotFoundException, MessageException {
        Book book = bookRepository.findById(editBookDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(Book.class, "id", String.valueOf(editBookDto.getId())));
        checkUser(book.getUser());
        checkExist(book.getUser(), editBookDto.getFoolName());
        book.setFoolName(editBookDto.getFoolName());
        return book.getId();
    }

    @Override
    public BookPreviewDto getBook(long bookId) throws EntityNotFoundException, MessageException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(Book.class, "id", String.valueOf(bookId)));
        checkUser(book.getUser());
        return bookPreviewDtoFromBook(book);
    }

    @Override
    public boolean deleteBook(long bookId) throws EntityNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(Book.class, "id", String.valueOf(bookId)));
        bookRepository.delete(book);
        return true;
    }

    @Override
    public Page<BookListPreviewDto> getAllBooks(int page, int size) throws EntityNotFoundException {
        User user = findUser.findUserByToken();
        return getBooks(page, size, user);
    }

    @Override
    public Page<BookListPreviewDto> getAllBooksAdmin(int page, int size, Long userId) throws EntityNotFoundException {
        if (userId == null) {
            Page<Book> books = bookRepository.findAll(PageRequest.of(page, size));
            return listBookListPreviewDtoFromBooks(books);
        } else {
            return getBooks(page, size, findUser.findUserById(userId));
        }
    }

    private Page<BookListPreviewDto> getBooks(int page, int size, User user) {
        Page<Book> books = bookRepository.findAllByUser(user, PageRequest.of(page, size));
        return listBookListPreviewDtoFromBooks(books);
    }

    private void checkExist(User user, String foolName) throws MessageException {
        if (bookRepository.findAllByFoolNameAndUser(foolName, user).size() > 0) {
            throw new MessageException("This book is already exist!!!");
        }
    }

    private void checkUser(User user) throws EntityNotFoundException, MessageException {
        User currentUser = findUser.findUserByToken();
        if (UserRoleEnum.ROLE_USER.equals(currentUser.getRole())) {
            if (UserStatusEnum.BLOCK.equals(user.getUserStatusEnum())) {
                throw new MessageException("User is blocked!!!");
            }
            if (!currentUser.equals(user)) {
                throw new MessageException("Current and book's user is different!!!");
            }
        }
    }


    private Long addBook(String foolName, User user) {
        return bookRepository.save(
                Book.builder()
                        .foolName(foolName)
                        .user(user)
                        .build()
        ).getId();
    }

}
