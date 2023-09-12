package com.example.security.service.impl;

import com.example.security.DTO.response.ResponseBook;
import com.example.security.DTO.response.UserResponseDTO;
import com.example.security.entity.BorrowBook;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.entity.UserHistory;
import com.example.security.exception.CustomeException;
import com.example.security.exception.ResourceNotFoundException;
import com.example.security.repository.BorrowBookRepository;
import com.example.security.repository.UserHistoryRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserHistoryRepository userHistoryRepository;
    private final BorrowBookRepository borrowBookRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, UserHistoryRepository userHistoryRepository, BorrowBookRepository borrowBookRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userHistoryRepository = userHistoryRepository;
        this.borrowBookRepository = borrowBookRepository;
    }

    @Override
    public UserResponseDTO getUserById(long userId) {
        // Check is User available or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        // Map the User entity to a UserResponseDTO
        UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);
        response.setId(user.getId());

        return response;
    }

    @Override
    public Set<ResponseBook> getListofBorrowedBook(User authUser, long userId) {
        // Check if authUser has the "user" role
        if (authUser.getRole() == Role.USER && authUser.getId() != userId) {
            throw new CustomeException(HttpStatus.FORBIDDEN, "User can't able to other people Information Access denied");
        }
        // Check is User available or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));
        // Map UserHistory to ResponseBook
        return mapUserHistoryToResponseBooks(user.getUserHistory());
    }

    @Override
    public List<ResponseBook> getListCurrentlyBorrowBookByUserId(User authUser, long userId) {
        // Check if authUser has the "user" role
        if (authUser.getRole() == Role.USER && authUser.getId() != userId) {
            throw new CustomeException(HttpStatus.FORBIDDEN, "User can't able to other people Information Access denied");
        }
        // Check is User available or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        return mapUserCurrentlyBorrowToResponseBooks(user.getBorrows());
    }

    private List<ResponseBook> mapUserCurrentlyBorrowToResponseBooks(Set<BorrowBook> userHistory) {
        return userHistory.stream()
                .map(this::mapCUrrentBorrowToResponseBook)
                .collect(Collectors.toList());
    }
    private ResponseBook mapCUrrentBorrowToResponseBook(BorrowBook userHistory) {
        ResponseBook responseBook = new ResponseBook();
        responseBook.setBookTitle(userHistory.getBook().getTitle());
        return responseBook;
    }

    private Set<ResponseBook> mapUserHistoryToResponseBooks(Set<UserHistory> userHistory) {
        return userHistory.stream()
                .map(this::mapUserHistoryToResponseBook)
                .collect(Collectors.toSet());
    }

    private ResponseBook mapUserHistoryToResponseBook(UserHistory userHistory) {
        ResponseBook responseBook = new ResponseBook();
        responseBook.setBookTitle(userHistory.getBook().getTitle());
        return responseBook;
    }
}
