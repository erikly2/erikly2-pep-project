package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    // new user registration
    private AccountDAO accountDAO;

    // No arg constructor
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    // Constructor with arg
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO=accountDAO;
    }

    /** 
     * list of accounts 
     * 
     * @return all accounts
    */
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
    /** 
     * inserts an account into the Account table. Does not contain an id
     * 
     * @param acc an account object
     * @return The added account (if successful)
    */
    public Account insertAccount(Account acc) {
        return accountDAO.insertAccount(acc);
    }
    /** 
     * checks if a username is already in use
     * 
     * @param username username to check
     * @return if the username exists
    */
    public Boolean accountExist(String username) {
        return accountDAO.userExists(username);
    }
}
