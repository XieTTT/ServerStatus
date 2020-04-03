package run.serverstatus.app.service;

import run.serverstatus.app.entities.properties.Account;

public interface IndexService {
    /**
     * Is this the first time to sign in?
     *
     * @return boolean
     */
    boolean isFirstTimeSignIn();

    /**
     * Find the Account by username
     *
     * @param username password
     * @return Account
     */
    Account findAccountByUsernameAndPassword(String username, String password);


    /**
     * Insert an account to database
     *
     * @param account account
     */
    void insertAccount(Account account);

    /**
     * Update mailSettings from sign-up webPage
     *
     * @param recipient settings
     */
    void updateMailSettingsTo(String  recipient);

    /**
     * Send a boot info when first time sign-up
     * @return boolean
     */
    boolean sendBootInfo();

}
