package banking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class UserInterface {
    Printer printer;
    BufferedReader reader;
    SQLInterface sqlInterface;
    Integer loggedCardId = null;

    public UserInterface(SQLInterface sqlInterface, BufferedReader reader, BufferedWriter writer) {
        this.sqlInterface = sqlInterface;
        this.reader = reader;
        this.printer = new Printer(writer);
    }

    public Condition runNotLogged() throws IOException {
        printer.printMenuNotLogged();
        int input;

        try {
            input = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            printer.printWrongInput();
            return Condition.NOT_LOGGED;
        }

        switch (input) {
            case 1: { //Create an account
                String number = MathOps.generateCardNumber();
                String pin = MathOps.generatePIN();
                printer.printCardCreated(number, pin);
                sqlInterface.addCard(number, pin);
                return Condition.NOT_LOGGED;
            } case 2: { //Log into account
                printer.printEnterYourCardNumber();
                String number = reader.readLine();
                printer.printEnterPIN();
                String pin = reader.readLine();
                Integer id = sqlInterface.getIdIfPinIsCorrect(number, pin);

                if (id != null) {
                    loggedCardId = id;
                    printer.printLogInSuccess();
                    return Condition.LOGGED;
                } else {
                    printer.printLogInWrong();
                    return Condition.NOT_LOGGED;
                }
            } case 0: { //Exit
                printer.printBye();
                return Condition.EXITING;
            } default: {
                printer.printWrongInput();
                return Condition.NOT_LOGGED;
            }
        }
    }

    public Condition runLogged() throws IOException {
        printer.printMenuLogged();
        int input;

        try {
            input = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            printer.printWrongInput();
            return Condition.LOGGED;
        }

        switch (input) {
            case 1: { //Balance
                try {
                    printer.printBalance(sqlInterface.getBalance(loggedCardId));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return Condition.LOGGED;
            } case 2: { //Add income
                printer.printAddIncome();
                int income = Integer.parseInt(reader.readLine());
                sqlInterface.addIncome(loggedCardId, income);
                printer.printIncomeAdded();
                return Condition.LOGGED;
            } case 3: { //Do Transfer
                printer.printTransfer();
                printer.printEnterGetterCardNumber();
                String numberTo = reader.readLine();
                TransferStatus status = sqlInterface.transferCheck(loggedCardId, numberTo);
                if (status != TransferStatus.SUCCESS) {
                    printer.printMessage(status);
                    return Condition.LOGGED;
                }

                int idTo = sqlInterface.getId(numberTo);
                printer.printMoneyToTransfer();
                int money = Integer.parseInt(reader.readLine());
                status = sqlInterface.doTransfer(loggedCardId, idTo, money);
                printer.printMessage(status);
                return Condition.LOGGED;
            } case 4: { //Close Account
                printer.printAccountClosed();
                sqlInterface.closeAccount(loggedCardId);
                return Condition.NOT_LOGGED;
            } case 5: { //Log out
                printer.printLogOutSuccess();
                loggedCardId = null;
                return Condition.NOT_LOGGED;
            } case 0: {
                printer.printBye();
                return Condition.EXITING;
            } default: {
                printer.printWrongInput();
                return Condition.NOT_LOGGED;
            }
        }
    }

    public void run(Condition condition) throws IOException {
        while (condition != Condition.EXITING) {
            switch (condition) {
                case NOT_LOGGED:
                    condition = runNotLogged();
                    break;
                case LOGGED:
                    condition = runLogged();
                    break;
            }
        }
    }
}
