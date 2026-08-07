package ar.com.splitmate.dto.responseDTO;

import ar.com.splitmate.Group;
import ar.com.splitmate.User;

import java.util.List;

public record DashboardResponse(
        UserDashboardDTO userDashboardDTO,
        List<GroupDashboardDTO> groups
) {
}
