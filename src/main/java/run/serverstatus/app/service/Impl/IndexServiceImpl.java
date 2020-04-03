package run.serverstatus.app.service.Impl;

import run.serverstatus.app.entities.info.BootInfo;
import run.serverstatus.app.entities.properties.Account;
import run.serverstatus.app.repository.PropertiesRepository;
import run.serverstatus.app.repository.info.BootInfoRepository;
import run.serverstatus.app.schedule.sendMail.SendBootInformation;
import run.serverstatus.app.service.IndexService;
import run.serverstatus.app.utils.infoUtils.BootInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    private final PropertiesRepository pRepository;
    private final BootInfoUtil bootInfoUtil;
    private final BootInfoRepository bootInfoRep;
    private final SendBootInformation sendBootInformation;

    public IndexServiceImpl(PropertiesRepository pRepository,
                            BootInfoUtil bootInfoUtil,
                            BootInfoRepository bootInfoRep,
                            SendBootInformation sendBootInformation) {
        this.pRepository = pRepository;
        this.bootInfoUtil = bootInfoUtil;
        this.bootInfoRep = bootInfoRep;
        this.sendBootInformation = sendBootInformation;
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
        log.info("Send boot information .");
        BootInfo bootInfo = bootInfoUtil.collectBootInfo();
        bootInfoRep.insertBaseInfo(bootInfo);
        return sendBootInformation.sendBootInfoByEmail(bootInfo);
    }

}
