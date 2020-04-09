package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.StaticInfo;
import run.serverstatus.app.entities.properties.Account;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.schedule.sendMail.SendBootInformation;
import run.serverstatus.app.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    private final PropertiesRepository pRepository;
    private final SendBootInformation sendBootInformation;
    private final StaticInfo staticInfo;

    public IndexServiceImpl(PropertiesRepository pRepository,
                            SendBootInformation sendBootInformation,
                            StaticInfo staticInfo) {
        this.pRepository = pRepository;
        this.sendBootInformation = sendBootInformation;
        this.staticInfo = staticInfo;
    }

    /**
     * Is this the first time to sign in?
     *
     * @return boolean
     */
    @Override
    public boolean isFirstTimeSignIn() {
        int anyAccount = pRepository.findAnyAccount();
        return !(anyAccount > 0);
    }

    /**
     * Is this the first time to sign up?
     *
     * @return boolean
     */
    @Override
    public boolean isFirstTimeSignUp() {
        return isFirstTimeSignIn();
    }

    /**
     * Find the Account by username
     *
     * @param username password username
     * @return Account
     */
    @Override
    public Account findAccountByUsernameAndPassword(String username, String password) {
        Account account;
        try {
            account = pRepository.findAccountByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return account;
    }

    /**
     * Insert an account to database
     *
     * @param account a
     */
    @Override
    public void insertAccount(Account account) {
        pRepository.insertAccount(account);
    }

    /**
     * Update mailSettings from sign-up webPage
     *
     * @param recipient settings
     */
    @Override
    public void updateMailSettingsTo(String recipient) {
        pRepository.updateMailSettingsTo(recipient);
    }

    /**
     * Send a boot info when first time sign-up
     *
     * @return boolean
     */
    @Override
    public boolean sendBootInfo() {
        /*Send boot Information*/
        log.info("Send boot information.");
        return sendBootInformation.sendBootInfoByEmail(staticInfo);
    }

}
