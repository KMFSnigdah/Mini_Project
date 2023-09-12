package com.example.security.service.impl;

import com.example.security.DTO.response.HistoryResponseDTO;
import com.example.security.DTO.response.UserResponseDTO;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.entity.UserHistory;
import com.example.security.exception.CustomeException;
import com.example.security.exception.ResourceNotFoundException;
import com.example.security.repository.UserHistoryRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.IUserHistory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHistoryService implements IUserHistory {

    private final UserHistoryRepository userHistoryRepository;
    private final UserRepository userRepository;

    public UserHistoryService(UserHistoryRepository userHistoryRepository, UserRepository userRepository) {
        this.userHistoryRepository = userHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<HistoryResponseDTO> getHistoryByUserId(User authUser, long userId) {
        // Check if authUser has the "user" role and if authUser.id is not equal to userId
        if (authUser.getRole() == Role.USER && authUser.getId() != userId) {
            throw new CustomeException(HttpStatus.FORBIDDEN, "User can't able to others information. Access denied");
        }
        // Check if the user exists, or throw a ResourceNotFoundException
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));
        // Fetch the user's history
        List<UserHistory> history = userHistoryRepository.findByUserId(userId);
        // Check if history is empty and handle it with a custom exception
        if (history.isEmpty()) {
            throw new CustomeException(HttpStatus.NOT_FOUND, "No user history found");
        }
        // Map the UserHistory objects to DTOs and return the result
        return mapUserHistoryToDTOs(history);
    }

    // Extracted mapping logic into a separate method for modularity
    private List<HistoryResponseDTO> mapUserHistoryToDTOs(List<UserHistory> history) {
        return history.stream()
                .map(this::mapToHistoryResponseDTO)
                .toList();
    }

    // Maps a UserHistory object to a HistoryResponseDTO object
    private HistoryResponseDTO mapToHistoryResponseDTO(UserHistory userHistory) {
        HistoryResponseDTO historyResponse = new HistoryResponseDTO();
        historyResponse.setBookName(userHistory.getBook().getTitle());
        historyResponse.setBorrowDate(userHistory.getBorrowDate());
        historyResponse.setDueDate(userHistory.getDueDate());
        historyResponse.setReturnDate(userHistory.getReturnedDate());
        return historyResponse;
    }
}
