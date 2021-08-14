package banking;

public enum Queries {
    ADD_CARD("INSERT INTO card (number, pin) VALUES (?, ?)"),
    ADD_INCOME("UPDATE card SET balance = balance + ? WHERE id = ?"),
    CLOSE_ACCOUNT("DELETE FROM card WHERE id = ?"),
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS card(" +
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "number VARCHAR(16) NOT NULL," +
            "pin VARCHAR(4) NOT NULL," +
            "balance INTEGER DEFAULT 0" +
            ")"),
    GET_BALANCE("SELECT * FROM card WHERE id = ?"),
    GET_ID_AND_PIN("SELECT id, pin FROM card WHERE number = ?"),
    TRANSFER_FROM("UPDATE card SET balance = balance - ? WHERE id = ?"),
    TRANSFER_TO("UPDATE card SET balance = balance + ? WHERE id = ?");

    protected String query;

    Queries(String query) {
        this.query = query;
    }

    public String getQuery(){
        return this.query;
    }
}
