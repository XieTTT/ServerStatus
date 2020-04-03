package run.serverstatus.app.repository.info;

import run.serverstatus.app.entities.info.BootInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BootInfoRepository {

    @Insert("insert into BaseInfo(mark, intranetIp, publicIp, osName, osUptime,appBootTime, osBootTime, hostname) " +
            "values(#{mark}, #{intranetIp}, #{publicIp}, #{osName}, #{osUptime}, #{appBootTime}, #{osBootTime}, #{hostname})")
    void insertBaseInfo(BootInfo bootInfo);

    @Select("select * from BaseInfo  ORDER BY id DESC LIMIT 1")
    BootInfo findBootInfo();
}
