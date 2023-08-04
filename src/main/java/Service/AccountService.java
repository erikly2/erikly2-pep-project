package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
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
     * Gets all the accounts from the Account table.
     * @return all Accounts.
     */
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    /** 
     * Insert an account into the Account table. (no account_id)
     * @param account The account to add.
     * @return The Account added in the table.
     */
    public Account insertAccount(Account acc) {
        return accountDAO.insertAccount(acc);
    }

    /** 
     * Finds if a username exists in the Account table
     * @param username The name to check.
     * @return If the username exists in the table.
     */
    public Boolean accountExist(String username) {
        return accountDAO.userExists(username);
    }

    /** 
     * Gets the Account that matches the credentials provided.
     * @param account holds the credentials to check.
     * @return Account with matching credentials.
     */
    public Account matchLogin(Account account) {
        return accountDAO.matchLogin(account);
    }
    
}
