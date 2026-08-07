package ar.com.splitmate.dto.responseDTO;

import ar.com.splitmate.enums.Role;

public record GroupMemberDto(
        Long id,
        String username,
        Role role
) {
}
