package am.task.controllers;

import am.task.model.ResponseModel;
import am.task.model.dto.book.AddBookDto;
import am.task.model.dto.book.AdminAddBookDto;
import am.task.model.dto.book.EditBookDto;
import am.task.services.book.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("book/")
@Api(value = "book")
public class BookController extends BaseController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @ApiOperation(value = "API for user to add book")
    @PostMapping("add")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseModel addBook(@Valid @RequestBody AddBookDto addBookDto) {
        try {
            return createResult(bookService.add(addBookDto), "Book is created successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @ApiOperation(value = "API for admin to add book")
    @PostMapping("addAdmin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseModel addBook(@Valid @RequestBody AdminAddBookDto adminAddBookDto) {
        try {
            return createResult(bookService.add(adminAddBookDto), "Book is created successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @ApiOperation(value = "API for user and admin to edit book")
    @PostMapping("edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseModel editBook(@Valid @RequestBody EditBookDto editBookDto) {
        try {
            return createResult(bookService.edit(editBookDto), "Book is edited successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @ApiOperation(value = "API for user and admin to get book")
    @GetMapping("get")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseModel getBook(@RequestParam long bookId) {
        try {
            return createResult(bookService.getBook(bookId), "Book is retrieved successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @ApiOperation(value = "API for admin to get book by id")
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseModel deleteBook(@RequestParam long bookId) {
        try {
            return createResult(bookService.deleteBook(bookId), "Book is deleted successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

}