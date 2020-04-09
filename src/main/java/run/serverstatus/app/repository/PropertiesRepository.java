package run.serverstatus.app.repository;

import run.serverstatus.app.entities.properties.Account;
import run.serverstatus.app.entities.properties.MailSettings;
import run.serverstatus.app.entities.properties.AppSettings;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepository {
    /**
     * insert settings to database
     *
     * @param appSettings settings
     */
    @Insert("insert into Settings values(null, #{language}, #{serverName}, #{processNum}, #{mark})")
    void insertSettings(AppSettings appSettings);

    /**
     * Update the only settings
     *
     * @param appSettings s
     * @return if success
     */
    @Update("update Settings set serverName = #{serverName}, language = #{language}, processNum = #{processNum}," +
            " mark = #{mark} where id = 1")
    int updateSettings(AppSettings appSettings);

    /**
     * Find settings in database
     *
     * @return Settings
     */
    @Select("select * from Settings ORDER BY id DESC LIMIT 1")
    AppSettings findSettings();

    /**
     * Insert account to table account
     *
     * @param account Account
     */
    @Insert("insert into account values(null, #{username}, #{password})")
    void insertAccount(Account account);

    @Select("select * from account where username = #{username}")
    Account findAccountByUsername(String username);

    @Select("select count(id) from account ")
    int findAnyAccount();

    /**
     * MailSettings
     *
     * @param mailSettings MailSettings
     */
    @Insert("insert into MailSettings values(null,#{host},#{username},#{password},#{mailSender},#{to})")
    void insertMail(MailSettings mailSettings);

    @Select(("select * from MailSettings ORDER BY id DESC LIMIT 1"))
    MailSettings findMailSettings();

    /**
     * Update recipient
     *
     * @param to recipient
     */
    @Update("UPDATE MailSettings SET to = #{to} WHERE id = 1 ")
    void updateMailSettingsTo(String to);

    @Update("UPDATE mailSettings set host = #{host}, username=#{username}," +
            " password=#{password}, mailSender = #{mailSender}, to = #{to} where id = 1")
    int updateMailSettings(MailSettings mailSettings);

}
