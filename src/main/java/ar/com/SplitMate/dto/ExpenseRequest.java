package ar.com.splitmate.dto;

public record ExpenseRequest(
        String description,
        Double amount,
        Long userId,
        Long groupId
) {}
