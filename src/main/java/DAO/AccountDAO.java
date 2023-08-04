package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.List;

import java.util.ArrayList;

public class AccountDAO {
    /** 
     * Gets all the accounts from the Account table.
     * @return all Accounts.
     */
    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    /** 
     * Finds if a username exists in the Account table
     * @param user The name to check.
     * @return If the username exists in the table.
     */
    public Boolean userExists(String user) {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account WHERE username=(?);";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return (accounts.size()<1);
    }

    /** 
     * Insert an account into the Account table. (no account_id)
     * @param account The account to add.
     * @return The Account added in the table.
     */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int generated_account_id = (int) rs.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /** 
     * Gets the Account that matches the credentials provided.
     * @param account holds the credentials to check.
     * @return Account with matching credentials.
     */
    public Account matchLogin(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username=(?) AND password = (?);";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return acc;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
