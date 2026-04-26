package ar.com.splitmate;

import ar.com.splitmate.excepciones.UsuarioNoEncontradoException;
import ar.com.splitmate.servicios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Scanner;

@SpringBootApplication
public class SplitMate implements CommandLineRunner {

    private final UserService userService;
    private final GroupService groupService;
    private final ExpenseService expenseService;
    private final GroupMemberService memberService;
    

    public SplitMate(UserService userService, GroupService groupService, ExpenseService expenseService, GroupMemberService memberService) {
        this.userService = userService;
        this.groupService = groupService;
        this.expenseService = expenseService;
        this.memberService = memberService;
  
    }

    public static void main(String[] args) {
        SpringApplication.run(SplitMate.class, args);
    }

    @Override
    public void run(String... args) {
        menu();
    }

    private void menu() {
        Scanner scanner = new Scanner(System.in);
        Integer opcion = null;

        do {
            try {
                System.out.println("VERSION NUEVA DEL MENU");
                System.out.println("\n=== SPLITMATE ===");
                System.out.println("1. Crear usuario");
                System.out.println("2. Crear grupo");
                System.out.println("3. Agregar usuario a grupo");
                System.out.println("4. Crear gasto");
                System.out.println("0. Salir");

                opcion = leerOpcion(scanner);

                switch (opcion) {
                    case 1 -> crearUsuario(scanner);
                    case 2 -> crearGrupo(scanner);
                    case 3 -> agregarMiembro(scanner);
                    case 4 -> crearGasto(scanner);
                }

            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Error inesperado: " + e.getMessage());
            }

        } while (opcion != 0);
    }



    private User crearUsuario(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        while (username == null || username == "" ){
            System.out.print("Ingrese un usuario porfavor: ");
            username = scanner.nextLine();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        while (password == null || password == ""){
        System.out.print("Ingrese una contraseña porfavor: ");
        password = scanner.nextLine();
        }

        User user = new User(username, password);
        userService.guardarUsuario(user);

        System.out.println("Usuario creado!");
        
        return user;
    }

    private Group crearGrupo(Scanner scanner) {
        System.out.print("Nombre del grupo: ");
        String name = scanner.nextLine();
        while (name == null || name == ""){
            System.out.print("Ingrese un nombre porfavor: ");
            name = scanner.nextLine();
        }

        Group group = new Group(name);
        groupService.guardarGrupo(group);

        System.out.println(group.getId());
        System.out.println("Grupo creado!");
        
        return group;
    }

    private void agregarMiembro(Scanner scanner) {

        System.out.print("ID Usuario: ");
        Long userId = scanner.nextLong();

        System.out.print("ID Grupo: ");
        Long groupId = scanner.nextLong();
        scanner.nextLine();

        User user = userService.buscarPorId(userId);
        Group group = groupService.buscarPorId(groupId);

        if (user == null || group == null) {
            System.out.println("Usuario o grupo no encontrado");
            return;
        }

        GroupMember miembro = new GroupMember(user, group);

        groupService.agregarMiembro(miembro);

        System.out.println("Miembro agregado correctamente");
}

private void crearGasto(Scanner scanner) throws UsuarioNoEncontradoException{

        System.out.print("Descripción: ");
        String desc = scanner.nextLine();

        double amount = leerDouble(scanner, "Monto: ");

        User user = obtenerUsuarioValido(scanner);
        Group group = obtenerGrupoValido(scanner);

        GroupMember miembro = memberService.obtenerMiembro(user.getId(), group.getId());

        if (miembro == null) {
            throw new UsuarioNoEncontradoException("Usuario No Encontrado");
        }

        Expense expense = new Expense(desc, amount, miembro, group);
        expenseService.guardarGasto(expense);

        System.out.println("✅ Gasto creado correctamente");
}
    
    private User obtenerUsuarioValido(Scanner scanner) {
    while (true) {
        Long userId = leerLong(scanner, "ID Usuario: ");
        User user = userService.buscarPorId(userId);

        if (user != null) return user;

        System.out.println("❌ Usuario no encontrado. Intentá de nuevo.");
    }
}
    
    private Group obtenerGrupoValido(Scanner scanner) {
    while (true) {
        Long groupId = leerLong(scanner, "ID Grupo: ");
        Group grupo = groupService.buscarPorId(groupId);

        if (grupo != null) return grupo;

        System.out.println("❌ Usuario no encontrado. Intentá de nuevo.");
    }
}
    private Long leerLong(Scanner scanner, String mensaje) {
    while (true) {
        try {
            System.out.print(mensaje);
            Long value = Long.parseLong(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("❌ Ingresá un número válido.");
        }
    }
}
    
   private double leerDouble(Scanner scanner, String mensaje) {
    while (true) {
        try {
            System.out.print(mensaje);
            double value = Double.parseDouble(scanner.nextLine());
            if (value <= 0) {
                System.out.println("❌ El monto debe ser mayor a 0.");
                continue;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("❌ Ingresá un número válido.");
        }
    }
}
   
   private int leerOpcion(Scanner scanner) {
    while (true) {
        try {
            int op = Integer.parseInt(scanner.nextLine());
            if (op >= 0 && op <= 4) return op;
        } catch (Exception ignored) {}

        System.out.println("❌ Opción inválida. Intentá de nuevo.");
    }
}
}