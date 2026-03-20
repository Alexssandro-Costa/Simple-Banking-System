package DTO;

public record ClientDTO(String name, String accountNumber, String balance) {

    @Override
    public String toString() {
        return String.format("Titular: %s %nNumero da conta: %s %nSaldo: R$:%s%n", name, accountNumber, balance);
    }
}
