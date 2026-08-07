package ar.com.splitmate.dto.responseDTO;

import java.util.List;

public record GroupDashboardDTO(
        Long id,
        String name,
        List<GroupMemberDto> members
) {

}
