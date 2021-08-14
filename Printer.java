package banking;

import java.io.BufferedWriter;
import java.io.IOException;

public class Printer {
    BufferedWriter writer;

    public Printer(BufferedWriter writer) {
        this.writer = writer;
    }

    public void printAccountClosed() throws IOException {
        writer.write("\n");
        writer.write("The account has been closed!");
        writer.flush();
    }

    public void printAddIncome() throws IOException {
        writer.write("\n");
        writer.write("Enter income:\n");
        writer.flush();
    }

    public void printBalance(int balance) throws IOException {
        writer.write("\n");
        writer.write("Balance: " + balance + "\n");
        writer.flush();
    }

    public void printBye() throws IOException {
        writer.write("\n");
        writer.write("Bye!");
        writer.flush();
    }

    public void printCardCreated(String number, String PIN) throws IOException {
        writer.write("\n");
        writer.write("Your card has been created\n");
        writer.write("Your card number:\n");
        writer.write(number + "\n");
        writer.write("Your card PIN:\n");
        writer.write(PIN + "\n");
        writer.flush();
    }

    public void printEnterGetterCardNumber() throws IOException {
        writer.write("\n");
        writer.write("Enter card number:\n");
        writer.flush();
    }

    public void printEnterYourCardNumber() throws IOException {
        writer.write("\n");
        writer.write("Enter your card number:\n");
        writer.flush();
    }

    public void printEnterPIN() throws IOException {
        writer.write("\n");
        writer.write("Enter your PIN:\n");
        writer.flush();
    }

    public void printIncomeAdded() throws IOException {
        writer.write("\n");
        writer.write("Income was added!\n");
        writer.flush();
    }

    public void printLogOutSuccess() throws IOException {
        writer.write("\n");
        writer.write("You have successfully logged out!\n");
        writer.flush();
    }

    public void printLogInSuccess() throws IOException {
        writer.write("\n");
        writer.write("You have successfully logged in!\n");
        writer.flush();
    }

    public void printLogInWrong() throws IOException {
        writer.write("\n");
        writer.write("Wrong card number or PIN!\n");
        writer.flush();
    }

    public void printMenuLogged() throws IOException {
        writer.write("\n");
        writer.write("1. Balance\n");
        writer.write("2. Add income\n");
        writer.write("3. Do transfer\n");
        writer.write("4. Close account\n");
        writer.write("5. Log out\n");
        writer.write("0. Exit\n");
        writer.flush();
    }

    public void printMenuNotLogged() throws IOException {
        writer.write("\n");
        writer.write("1. Create an account\n");
        writer.write("2. Log into account\n");
        writer.write("0. Exit\n");
        writer.flush();
    }

    public void printMoneyToTransfer() throws IOException {
        writer.write("\n");
        writer.write("Enter how much money you want to transfer:\n");
        writer.flush();
    }

    public void printTransfer() throws IOException {
        writer.write("\n");
        writer.write("Transfer\n");
        writer.flush();
    }

    public void printMessage(TransferStatus status) throws IOException {
        writer.write("\n");
        writer.write(status.getMessage());
        writer.flush();
    }

    public void printWrongInput() throws IOException {
        writer.write("\n");
        writer.write("Wrong input, please try again!");
        writer.flush();
    }
}
