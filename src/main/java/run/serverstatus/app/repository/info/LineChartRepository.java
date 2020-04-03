package run.serverstatus.app.repository.info;

import run.serverstatus.app.entities.info.LineChartInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineChartRepository {

    /*latest minute ---------------------------------------------------------------------------------------------------*/
    @Insert("insert into LineChartMinuteInfo values(null, #{cpuLoad}, #{cpuTemperature}, #{internetSpeedIn}, #{internetSpeedOut}, #{longTime} )")
    void insertMinuteInfo(LineChartInfo lineChartInfo);

    @Select("select * from LineChartMinuteInfo ORDER BY id DESC LIMIT #{limit} ")
    List<LineChartInfo> findRecentLineChartInfoMinute(int limit);

    @Select("select CPULoad from LineChartMinuteInfo ORDER BY id DESC LIMIT #{limit} ")
    List<LineChartInfo> findRecentLineChartInfoMinuteCPULoad(int limit);


    /*latest hour ---------------------------------------------------------------------------------------------------*/
    @Delete("delete from LineChartMinuteInfo where longTime <  #{limit} ")
    void deleteExcessMinuteInfo(long limit);

    @Select("select count(id) from LineChartMinuteInfo")
    int findTotalMinuteInfo();

    @Insert("insert into LineChartHourInfo values(null, #{cpuLoad}, #{cpuTemperature}, #{internetSpeedIn}, #{internetSpeedOut}, #{longTime} )")
    void insertHourInfo(LineChartInfo lineChartInfo);

    @Select("select * from LineChartHourInfo ORDER BY id DESC LIMIT #{limit} ")
    List<LineChartInfo> findRecentLineChartInfoHour(int limit);

    /*latest day ---------------------------------------------------------------------------------------------------*/
}
