package run.serverstatus.app.handler.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LocalFileHandler {
    private Logger logger = LoggerFactory.getLogger(LocalFileHandler.class);

    void creatAdditionalLocation() {
        String location = System.getProperty("spring.config.additional-location");
        File file = new File(location);
        boolean flag = file.mkdir();
        logger.info("Create spring config additional location: " + flag);
    }

}
