-- ----------------------------
-- Table structure for HourInfo
-- ----------------------------
DROP TABLE IF EXISTS `TimedInfo`;
CREATE TABLE `TimedInfo`(
    `id` INT(11) NOT NULL AUTO_INCREMENT, -- 主键
    osUptime varchar(30) NOT NULL,
    osTime varchar(20) NOT NULL,
    availableMemory varchar(20) NOT NULL,
    fanSpeed varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for processCPU
-- ----------------------------
DROP TABLE IF EXISTS `processCPU`;
CREATE TABLE `processCPU` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  sortID INT(11),
  orderBy VARCHAR(20),
  PID VARCHAR(20),
  CPU VARCHAR(20),
  MEN VARCHAR(20),
  VSZ VARCHAR(20),
  RSS VARCHAR(20),
  NAME VARCHAR(30),
  hourInfo_id INT(11),
  PRIMARY KEY (`id`),
  CONSTRAINT processCPU_HourInfo_fk FOREIGN KEY (hourInfo_id) REFERENCES TimedInfo(id)
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for processMEN
-- ----------------------------
DROP TABLE IF EXISTS `processMEN`;
CREATE TABLE `processMEN` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    sortID INT(11),
    orderBy VARCHAR(20),
    PID VARCHAR(20),
    CPU VARCHAR(20),
    MEN VARCHAR(20),
    VSZ VARCHAR(20),
    RSS VARCHAR(20),
    NAME VARCHAR(30),
    hourInfo_id INT(11),
    PRIMARY KEY (`id`),
    CONSTRAINT processMEN_HourInfo_fk FOREIGN KEY (hourInfo_id) REFERENCES TimedInfo(id)
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for LineChartMinuteInfo
-- ----------------------------
DROP TABLE IF EXISTS `LineChartMinuteInfo`;
CREATE TABLE `LineChartMinuteInfo`(
    `id` INT(11) NOT NULL AUTO_INCREMENT, -- 主键
    cpuLoad double(20) NOT NULL,
    cpuTemperature varchar(30) NOT NULL,
    internetSpeedIn varchar(50) NOT NULL,
    internetSpeedOut varchar(50) NOT NULL,
    longTime varchar (30) not NULL ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for LineChartHourInfo
-- ----------------------------
DROP TABLE IF EXISTS `LineChartHourInfo`;
CREATE TABLE `LineChartHourInfo`(
    `id` INT(11) NOT NULL AUTO_INCREMENT, -- 主键
    cpuLoad double(20) NOT NULL,
    cpuTemperature varchar(30) NOT NULL,
    internetSpeedIn varchar(50) NOT NULL,
    internetSpeedOut varchar(50) NOT NULL,
    longTime varchar (30) not NULL ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for StaticInfo
-- ----------------------------
DROP TABLE IF EXISTS `Settings`;
CREATE TABLE `Settings`(
    `id` INT(11) NOT NULL AUTO_INCREMENT, -- 主键
    language varchar (20) NOT NULL,
    hostName varchar(20) NOT NULL,
    processNum varchar(20) NOT NULL,
    mark varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`(
    `id` INT(11) NOT NULL AUTO_INCREMENT, -- 主键
    username varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for MailSettings
-- ----------------------------
DROP TABLE IF EXISTS `MailSettings`;
CREATE TABLE `MailSettings`(
    `id` INT(11) NOT NULL AUTO_INCREMENT, -- 主键
    host varchar(20) NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(20) NOT NULL,
    mailSender varchar(20) NOT NULL,
    to varchar(20) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;