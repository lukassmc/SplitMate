package ar.com.SplitMate;
import java.util.Scanner;

public class SplitMate {

    
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);

        String registeredUsername = "";
        String registeredPassword = "";

        System.out.println("=== Bienvenido a SplitMate ===");

    
        System.out.println("\n--- Registro de usuario ---");
        System.out.print("Ingrese nombre de usuario: ");
        registeredUsername = scanner.nextLine();

        System.out.print("Ingrese contraseña: ");
        registeredPassword = scanner.nextLine();

        User user1 = new User(registeredUsername, registeredPassword);
        
        System.out.println("Usuario registrado con éxito.\n");

   
        System.out.println("--- Login ---");

        System.out.print("Usuario: ");
        String loginUser = scanner.nextLine();

        System.out.print("Contrasena: ");
        String loginPassword = scanner.nextLine();

        while (user1.login(loginUser, loginPassword) == false) {
            System.out.println("\n Credenciales incorrectas. Intente nuevamente.");
            System.out.print("Contrasena: ");
            loginPassword = scanner.nextLine();
        }

        if (user1.login(loginUser, loginPassword) == true) {

            System.out.println("\nLogin exitoso. Bienvenido " + loginUser);

         
            System.out.print("\nIngrese nombre del grupo: ");
            String groupName = scanner.nextLine();

            Group grupo1 = new Group(groupName, 123);
           
            
            System.out.println("Grupo '" + groupName + "' creado.");
            GroupMember miembro1 = new GroupMember(user1, grupo1);
            grupo1.addMember(miembro1);
            
            // Registrar gasto
            System.out.print("\nIngrese descripción del gasto: ");
            String expenseDescription = scanner.nextLine();

            System.out.print("Ingrese monto del gasto: ");
            double amount = scanner.nextDouble();
            
            Expense gasto1 = new Expense(1, expenseDescription, amount, user1, grupo1);

            grupo1.addExpense(gasto1);

        } else {
            System.out.println("\nUsuario o contraseña incorrectos.");
        }

        scanner.close();
    }
}