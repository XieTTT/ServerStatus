package run.serverstatus.app;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.*;

import java.util.*;

public class Test2 {
    SystemInfo info = new SystemInfo();
    HardwareAbstractionLayer hardware = info.getHardware();
    OperatingSystem operatingSystem = info.getOperatingSystem();

    //Get all computerSystem info
    public Map<String, Object> computerSystem() {
        ComputerSystem computerSystem = hardware.getComputerSystem();
        HashMap<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        Baseboard baseboard = computerSystem.getBaseboard();
        sb.
                append("Manufacturer（制造商）: ").append(baseboard.getManufacturer()).append(";   ").
                append("SerialNumber（序列号）: ").append(baseboard.getSerialNumber()).append(";   ").
                append("Version（版本）: ").append(baseboard.getVersion()).append(";   ");
        map.put("baseboard", sb.toString());
        map.put("Firmware", computerSystem.getFirmware().toString());
        map.put("SerialNumber", "SerialNumber(电脑系统序列号): " + computerSystem.getSerialNumber());
        map.put("model", "Model(电脑型号): " + computerSystem.getModel());
        return map;
    }

    //Get all diskStores info
    public List<Map<String, Object>> diskStores() {
        List<Map<String, Object>> maps = new ArrayList<>();
        HWDiskStore[] diskStores = hardware.getDiskStores();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < diskStores.length; i++) {
            Map<String, Object> map = new HashMap<>();
            HWDiskStore diskStore = diskStores[i];
            sb.
                    append("Model(型号)：").append(diskStore.getModel()).append(";    ").
                    append("Name(名称)：").append(diskStore.getName()).append(";    ").
                    append("Reads(读取次数)：").append(diskStore.getReads()).append(";    ").
                    append("Writes(写入次数)：").append(diskStore.getWrites()).append(";    ").
                    append("Serial(硬盘序列号)：").append(diskStore.getSerial()).append(";    ").
                    append("Size(硬盘容量)：").append(diskStore.getSize() / 1024 / 1024 / 1024).append("GB;    ");
            map.put("diskStore" + i, sb.toString());
            sb.delete(0, sb.length());
            maps.add(map);
        }
        return maps;
    }

    //Get all processor info
    public String processor() {
        CentralProcessor processor = hardware.getProcessor();
        return processor.toString();
    }
    //Get all fileSystem info
    public List<String> fileSystem() {
        FileSystem fileSystem = operatingSystem.getFileSystem();
        OSFileStore[] fileStores = fileSystem.getFileStores();
        List<String> fileSystems = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (OSFileStore fileStore : fileStores) {
            sb.
                    append("Name(盘名)：").append(fileStore.getName()).append(";   ").
                    append("FreeSpace(剩余容量)：").append(fileStore.getFreeSpace() / 1024 / 1024 / 1024).append("GB;   ").
                    append("TotalSpace(总容量)：").append(fileStore.getTotalSpace() / 1024 / 1024 / 1024).append("GB;   ").
                    append("Description(描述)：").append(fileStore.getDescription()).append(";   ").
                    append("Type(类型)：").append(fileStore.getType()).append(";   ");
            fileSystems.add(sb.toString());
            sb.delete(0, sb.length());
        }
        return fileSystems;
    }
    //Get  operatingSystem info
    public String operatingSystem(){
        String manufacturer = operatingSystem.getManufacturer();
        StringBuilder sb = new StringBuilder();
        sb.
                append("OSName(操作系统)：").append(System.getProperty("os.name")).append("; ").
                append("Bitness(系统位数)：").append(operatingSystem.getBitness()).append(";    ").
                append("SystemBootTime(系统启动时间)：").append(operatingSystem.getSystemBootTime()).append("; ").
                append("SystemUptime(系统运行时间)：").append(operatingSystem.getSystemUptime()).append("; ").
                append("ProcessCount(进程数)：").append(operatingSystem.getProcessCount()).append("; ");
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = new Test2().operatingSystem();
        System.out.println(s);
    }
}