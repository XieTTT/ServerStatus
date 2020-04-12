package run.serverstatus.app.repository;

import run.serverstatus.app.entities.info.Processor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProcessRepository {
    //Get Processor Sorted By CPu
    @Select("select * from processCPU where hourInfo_id = #{hourInfo_id} ")
    List<Processor> findProcessorSortedByCPU(int hourInfo_id);

    //Get Processor Sorted By MEN
    @Select("select * from processMEN where hourInfo_id = #{hourInfo_id} ")
    List<Processor> findProcessorSortedByMEN(int hourInfo_id);

    //Insert Processors CPU
    @Insert("insert into processCPU(orderBy, PID, CPU, MEN, VSZ, RSS, Name, hourInfo_id) " +
            "values(#{orderBy},#{PID},#{CPU},#{MEN},#{VSZ},#{RSS},#{Name},#{hourInfo_id})")
    void insertProcessorSortedByCPU(Processor processor);

    //Insert Processors MEn
    @Insert("insert into processMEN( orderBy, PID, CPU, MEN, VSZ, RSS, Name, hourInfo_id) " +
            "values(#{orderBy},#{PID},#{CPU},#{MEN},#{VSZ},#{RSS},#{Name},#{hourInfo_id})")
    void insertProcessorSortedByMEN(Processor processor);

}
