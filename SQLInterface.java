package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLInterface {

    String url;

    public SQLInterface(String fileName) {
        url = "jdbc:sqlite:" + fileName;
    }

    public void addCard(String number, String pin) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.ADD_CARD.getQuery())){
                preparedStatement.setString(1, number);
                preparedStatement.setString(2, pin);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIncome(int id, int income) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.ADD_INCOME.getQuery())){
                preparedStatement.setInt(1, income);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCardTable() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.CREATE_TABLE.getQuery())){
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(int id) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.CLOSE_ACCOUNT.getQuery())){
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TransferStatus doTransfer(int idFrom, int idTo, int money) {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl(url);

        try (Connection con = sqLiteDataSource.getConnection()){
            con.setAutoCommit(false);

            try (PreparedStatement transferFrom = con.prepareStatement(Queries.TRANSFER_FROM.getQuery())) {
                Savepoint savepoint = con.setSavepoint();
                transferFrom.setInt(1, money);
                transferFrom.setInt(2, idFrom);
                transferFrom.executeUpdate();

                if (getBalance(idFrom) < money) {
                    con.rollback(savepoint);
                    return TransferStatus.NOT_ENOUGH_MONEY;
                }

                PreparedStatement transferTo = con.prepareStatement(Queries.TRANSFER_TO.getQuery());
                transferTo.setInt(1, money);
                transferTo.setInt(2, idTo);
                transferTo.executeUpdate();

                con.commit();
                return TransferStatus.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return TransferStatus.SQL_ERROR;
    }

    public Integer getBalance(int id) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.GET_BALANCE.getQuery())){
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.getInt("balance");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getId(String number) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.GET_ID_AND_PIN.getQuery())){
                preparedStatement.setString(1, number);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.getInt("id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getIdIfPinIsCorrect(String number, String pin) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = con.prepareStatement(Queries.GET_ID_AND_PIN.getQuery())){
                preparedStatement.setString(1, number);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getString("pin").equals(pin)) {
                       return resultSet.getInt("id");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TransferStatus transferCheck(int idFrom, String numberTo) {
        Integer idTo;

        if (MathOps.luhnAlgoCheck(numberTo)) {
            idTo = getId(numberTo);
            if (idTo == null) {
                return TransferStatus.DOES_NOT_EXIST;
            }
        } else {
            return TransferStatus.LUHN_MISTAKE;
        }

        if (idTo == idFrom) {
            return TransferStatus.SAME_ACCOUNT;
        }

        return TransferStatus.SUCCESS;
    }
}
