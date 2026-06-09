package ar.com.splitmate.dto;

public class BalanceDTO {
    private String username;
    private double totalPaid;    // total que puso este miembro
    private double totalOwed;    // total que le corresponde pagar (sus splits)
    private double balance;      // totalPaid - totalOwed

    public BalanceDTO(String username, double totalPaid, double totalOwed) {
        this.username = username;
        this.totalPaid = totalPaid;
        this.totalOwed = totalOwed;
        this.balance = totalPaid - totalOwed;
    }

    public String getUsername()   { return username; }
    public double getTotalPaid()  { return totalPaid; }
    public double getTotalOwed()  { return totalOwed; }
    public double getBalance()    { return balance; }

    // Para el template: texto legible del estado
    public String getStatusLabel() {
        if (balance > 0.01)  return "te deben";
        if (balance < -0.01) return "debés";
        return "equilibrado";
    }


}
