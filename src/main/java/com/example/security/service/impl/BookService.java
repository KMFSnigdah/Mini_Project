package com.example.security.service.impl;

import com.example.security.DTO.request.CreateBookDTO;
import com.example.security.DTO.request.UpdateBookDTO;
import com.example.security.DTO.response.BookResponseDTO;
import com.example.security.entity.Book;
import com.example.security.exception.CustomeException;
import com.example.security.exception.ResourceNotFoundException;
import com.example.security.repository.BookRepository;
import com.example.security.repository.ReviewRepository;
import com.example.security.response.CustomResponse;
import com.example.security.response.PaginationResponse;
import com.example.security.service.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {


    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper mapper;

    public BookService(BookRepository bookRepository, ReviewRepository reviewRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
    }

    @Override
    public BookResponseDTO createBook(CreateBookDTO bookDto) {
        // Convert DTO to Entity
        Book book = mapper.map(bookDto, Book.class);
        // Save Entity in database
        bookRepository.save(book);
        // Convert Entity to DTO and return
        return mapper.map(book, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO updateBook(long id, UpdateBookDTO bookDto) {
        // Check is book available or not
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book", "id", id));
        // Check if Delete or not already
        if (book.isDeleted()) throw new ResourceNotFoundException("Book", "id", id);
        // Convert DTO to Entity
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setAvailable(bookDto.isAvailable());

        // Save Entity in database
        Book response = bookRepository.save(book);
        // Convert Entity to DTO and return
        return mapper.map(response, BookResponseDTO.class);
    }

    @Override
    public CustomResponse getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Book> books = bookRepository.findByIsDeletedFalse(pageable);

        //get content for page object
        List<Book> listofbooks = books.getContent();

        List<BookResponseDTO> content =listofbooks.stream().map(this::mapToDto).toList();

        CustomResponse<BookResponseDTO> response = new CustomResponse<>();
        response.setContent(content);

        // Set Pagination in here
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setPageNumber(books.getNumber());
        paginationResponse.setPageSize(books.getSize());
        paginationResponse.setTotalElements(books.getTotalElements());
        paginationResponse.setTotalPages(books.getTotalPages());
        paginationResponse.setHasNext(!books.isLast());
        paginationResponse.setHasPrevious(books.getNumber() > 0);

        response.setPagination(paginationResponse);

        return response;
    }

    @Override
    public void deleteBook(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        // Check if Delete or not already
        if (book.isDeleted()) throw new ResourceNotFoundException("Book", "id", id);
        // Check is borrow or not
        if(!book.isAvailable()) throw new CustomeException(HttpStatus.BAD_REQUEST, "You can't delete a borrowed book");
        // Set delete true
        book.setDeleted(true);
        // Save in database
        bookRepository.save(book);
        // Delete reviews
       // reviewRepository.deleteReviewsForByBookId(id);
    }

    // Convert Entity to DTO
    private BookResponseDTO mapToDto(Book book) {
        return mapper.map(book, BookResponseDTO.class);
    }

}
