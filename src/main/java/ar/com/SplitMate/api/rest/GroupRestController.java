package ar.com.splitmate.api.rest;

import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.enums.Role;
import ar.com.splitmate.User;
import ar.com.splitmate.dto.GroupRequest;
import ar.com.splitmate.dto.JoinGroupRequest;
import ar.com.splitmate.servicios.GroupMemberService;
import ar.com.splitmate.servicios.GroupService;
import ar.com.splitmate.servicios.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupRestController {

    private final GroupService groupService;
    private final GroupMemberService memberService;
    private final UserService userService;

    public GroupRestController(GroupService groupService,
                               GroupMemberService memberService,
                               UserService userService) {
        this.groupService = groupService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @PostMapping("create")
    public Group crearGrupo(@RequestBody GroupRequest req) {

        User user = userService.buscarPorId(req.userId());

        Group group = new Group(req.name());
        groupService.guardarGrupo(group);

        GroupMember admin = new GroupMember(user, group, Role.ADMIN);
        groupService.agregarMiembro(admin);

        return group;
    }

    @PostMapping("/join")
    public String join(@RequestBody JoinGroupRequest req) {

        Group group = groupService.buscarPorCodigo(req.inviteCode());
        User user = userService.buscarPorId(req.userId());

        if (group == null) {
            throw new RuntimeException("Código inválido");
        }

        GroupMember existente = memberService.obtenerMiembro(user.getId(), group.getId());
        if (existente != null) {
            throw new RuntimeException("Ya sos miembro");
        }

        GroupMember nuevo = new GroupMember(user, group, Role.MEMBER);
        groupService.agregarMiembro(nuevo);

        return "OK";
    }


    @GetMapping("list")
    public ResponseEntity<List<Group>> list(){
        List<Group> groups = this.groupService.listAll();

        return ResponseEntity.ok(groups);


    }
}
