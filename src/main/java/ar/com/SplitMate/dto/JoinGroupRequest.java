package ar.com.splitmate.dto;

public record JoinGroupRequest(
        String inviteCode,
        Long userId
) {
}
