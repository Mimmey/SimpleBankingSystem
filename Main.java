package banking;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        SQLInterface sqlInterface = new SQLInterface(args[1]);
        sqlInterface.createCardTable();

        UserInterface userInterface = new UserInterface(sqlInterface, reader, writer);
        userInterface.run(Condition.NOT_LOGGED);
    }
}