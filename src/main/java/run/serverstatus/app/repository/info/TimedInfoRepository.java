package run.serverstatus.app.repository.info;

import run.serverstatus.app.entities.info.TimedInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimedInfoRepository {
    @Insert("insert into TimedInfo values(null,#{osTime}, #{osUptime}, #{availableMemory},#{fanSpeed} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insertTimedInfo(TimedInfo timedInfo);

    @Select("SELECT * FROM TimedInfo ORDER BY id DESC LIMIT #{limit}")
    List<TimedInfo> findRecentTimedInfo(int limit);
}
