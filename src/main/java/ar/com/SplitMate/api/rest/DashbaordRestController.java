package ar.com.splitmate.api.rest;


import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.User;
import ar.com.splitmate.dto.responseDTO.GroupDashboardDTO;
import ar.com.splitmate.dto.responseDTO.GroupMemberDto;
import ar.com.splitmate.dto.responseDTO.UserDashboardDTO;
import ar.com.splitmate.servicios.GroupService;
import ar.com.splitmate.dto.responseDTO.DashboardResponse;
import ar.com.splitmate.servicios.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashbaordRestController {

    private final GroupService groupService;
    private final UserService userService;

    public DashbaordRestController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }


    @GetMapping("/")
    public DashboardResponse dashboard(){
        User usuario = userService.getUsuarioLogueado();

        List<Group> gruposATransformar = groupService.buscarGruposDeUsuario(usuario.getId());
        List<GroupDashboardDTO> grupos = new ArrayList<>();

        for(Group grupo : gruposATransformar){
            List<GroupMemberDto> members = new ArrayList<>();
            for (GroupMember member : grupo.getMembers()){
                members.add(new GroupMemberDto(member.getId(), member.getUser().getUsername(), member.getRole()));
            }
            grupos.add(new GroupDashboardDTO(grupo.getId(), grupo.getName(), members));
        }



        return new DashboardResponse(new UserDashboardDTO(usuario.getId(), usuario.getUsername()), grupos);
    };
}
