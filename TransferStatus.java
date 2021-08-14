package banking;

public enum TransferStatus {
    NOT_ENOUGH_MONEY("Not enough money!"),
    SAME_ACCOUNT("You can't transfer money to the same account!"),
    LUHN_MISTAKE("Probably you made a mistake in the card number. Please try again!"),
    DOES_NOT_EXIST("Such a card does not exist."),
    SQL_ERROR("Error!"),
    SUCCESS("Success!");

    protected String message;
    TransferStatus(String message) {
        this.message = message;
    }

    public String getMessage(){
         return this.message;
    }
}
