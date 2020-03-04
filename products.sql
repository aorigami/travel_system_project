/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : products

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 04/03/2020 16:57:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NICKNAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONENUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EMAIL` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('E61D65F673D54F68B0861025C69773DB', '张三', '小三', '18888888888', 'zs@163.com');

-- ----------------------------
-- Table structure for order_traveller
-- ----------------------------
DROP TABLE IF EXISTS `order_traveller`;
CREATE TABLE `order_traveller`  (
  `ORDERID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRAVELLERID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ORDERID`, `TRAVELLERID`) USING BTREE,
  INDEX `FK_Reference_2`(`TRAVELLERID`) USING BTREE,
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`ORDERID`) REFERENCES `orders` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`TRAVELLERID`) REFERENCES `traveller` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_traveller
-- ----------------------------
INSERT INTO `order_traveller` VALUES ('5DC6A48DD4E94592AE904930EA866AFA', '3FE27DF2A4E44A6DBC5D0FE4651D3D3E');
INSERT INTO `order_traveller` VALUES ('A0657832D93E4B10AE88A2D4B70B1A28', '3FE27DF2A4E44A6DBC5D0FE4651D3D3E');
INSERT INTO `order_traveller` VALUES ('3081770BC3984EF092D9E99760FDABDE', 'EE7A71FB6945483FBF91543DBE851960');
INSERT INTO `order_traveller` VALUES ('55F9AF582D5A4DB28FB4EC3199385762', 'EE7A71FB6945483FBF91543DBE851960');
INSERT INTO `order_traveller` VALUES ('5DC6A48DD4E94592AE904930EA866AFA', 'EE7A71FB6945483FBF91543DBE851960');
INSERT INTO `order_traveller` VALUES ('96CC8BD43C734CC2ACBFF09501B4DD5D', 'EE7A71FB6945483FBF91543DBE851960');
INSERT INTO `order_traveller` VALUES ('CA005CF1BE3C4EF68F88ABC7DF30E976', 'EE7A71FB6945483FBF91543DBE851960');
INSERT INTO `order_traveller` VALUES ('E4DD4C45EED84870ABA83574A801083E', 'EE7A71FB6945483FBF91543DBE851960');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORDERNUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORDERTIME` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `PEOPLECOUNT` int(11) NULL DEFAULT NULL,
  `ORDERDESC` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PAYTYPE` int(11) NULL DEFAULT NULL,
  `ORDERSTATUS` int(11) NULL DEFAULT NULL,
  `PRODUCTID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MEMBERID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `AK_Key_2`(`ORDERNUM`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('3081770BC3984EF092D9E99760FDABDE', '55555', '2019-11-08 16:11:24', 2, '没什么', 1, 1, '9F71F01CB448476DAFB309AA6DF9497F', 'E61D65F673D54F68B0861025C69773DB');
INSERT INTO `orders` VALUES ('55F9AF582D5A4DB28FB4EC3199385762', '33333', '2000-07-05 14:15:15', 2, '没什么', 0, 1, '9F71F01CB448476DAFB309AA6DF9497F', 'E61D65F673D54F68B0861025C69773DB');
INSERT INTO `orders` VALUES ('5DC6A48DD4E94592AE904930EA866AFA', '54321', '2019-11-08 16:11:36', 2, '没什么', 0, 0, '676C5BD1D35E429A8C2E114939C5685A', 'E61D65F673D54F68B0861025C69773DB');
INSERT INTO `orders` VALUES ('96CC8BD43C734CC2ACBFF09501B4DD5D', '22222', '1978-07-08 14:15:15', 2, '没什么', 0, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7', 'E61D65F673D54F68B0861025C69773DB');
INSERT INTO `orders` VALUES ('A0657832D93E4B10AE88A2D4B70B1A28', '98765', '2019-11-08 16:11:34', 2, '没什么', 1, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7', 'E61D65F673D54F68B0861025C69773DB');
INSERT INTO `orders` VALUES ('CA005CF1BE3C4EF68F88ABC7DF30E976', '44444', '2019-11-08 16:11:38', 2, '没什么', 0, 0, '9F71F01CB448476DAFB309AA6DF9497F', 'E61D65F673D54F68B0861025C69773DB');
INSERT INTO `orders` VALUES ('E4DD4C45EED84870ABA83574A801083E', '11111', '1998-07-08 14:15:15', 2, '没什么', 0, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7', 'E61D65F673D54F68B0861025C69773DB');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permissionName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('629530332a60394b219daa97cfe0b659', 'role test', '/role/test');
INSERT INTO `permission` VALUES ('765a04fcfeae11e9944db025aa305adf', 'user findAll', '/user/findAll.do');
INSERT INTO `permission` VALUES ('86e20f40feae11e9944db025aa305adf', 'user findById', '/user/findById.do');
INSERT INTO `permission` VALUES ('c292c203052111ea9831b025aa305adf', 'user findById', '/user/findById');
INSERT INTO `permission` VALUES ('cbd906e3fec411e9944db025aa305adf', 'role findAll', '/role/findAll.do');
INSERT INTO `permission` VALUES ('d9c22ba102e111eab5cfb025aa305adf', 'role findById', '/role/findById.do');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PRODUCTNUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PRODUCTNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CITYNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DEPARTURETIME` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `PRODUCTPRICE` decimal(8, 1) NULL DEFAULT NULL,
  `PRODUCTDESC` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PRODUCTSTATUS` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `PRODUCT`(`ID`, `PRODUCTNUM`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('128b2bfcfcab11e99d7fb025aa305adf', 'itcast-x003', '最终测试', '地球', '2020-03-02 15:59:10', 0.0, '来自十亿的微笑', 1);
INSERT INTO `product` VALUES ('129fcf36fc8211e99d7fb025aa305adf', '8301', '长沙你好', '长沙', '2020-03-02 16:00:12', 0.0, '不错的旅行', 0);
INSERT INTO `product` VALUES ('12B7ABF2A4C544568B0A7C69F36BF8B7', 'itcast-003', '上海五日游', '上海', '2020-03-02 16:51:04', 4396.0, '魔都我来了', 1);
INSERT INTO `product` VALUES ('278aedb202d011eab5cfb025aa305adf', 'itcast-x0011', '广州一日游', '家里', '2020-03-02 16:00:12', 0.0, '123123', 0);
INSERT INTO `product` VALUES ('2aa087de01fd11eaa6d3b025aa305adf', 'itcast-x004', '长沙五日游', '家里', '2020-03-02 15:59:10', 0.0, '长沙很厉害哦！', 1);
INSERT INTO `product` VALUES ('4162f4d002e011eab5cfb025aa305adf', 'itcast-x000', '不知道', '广州', '2020-03-02 16:00:22', 0.0, '123123123', 0);
INSERT INTO `product` VALUES ('51f9a19902d111eab5cfb025aa305adf', 'itcast-x0012', '最终测试006号', '广州', '2020-03-02 16:00:29', 0.0, '31321', 1);
INSERT INTO `product` VALUES ('59175b71fcaa11e99d7fb025aa305adf', 'itcast-x001', '广州一日游', '广州', '2020-03-02 16:00:29', 0.0, 'asdasdasd', 1);
INSERT INTO `product` VALUES ('676C5BD1D35E429A8C2E114939C5685A', 'itcast-002', '北京三日游', '北京', '2020-03-02 16:00:29', 0.0, '不错的旅行', 1);
INSERT INTO `product` VALUES ('93cdd6842297da1f783992132aa43074', 'test-11', '123', '广州', '2020-03-02 16:00:22', 0.0, '213', 0);
INSERT INTO `product` VALUES ('9f2c10f1038d11ea92dab025aa305adf', 'itcast-x??', 'max', 'max', '2019-11-10 07:41:00', 1000.0, '问号????', 0);
INSERT INTO `product` VALUES ('9F71F01CB448476DAFB309AA6DF9497F', 'itcast-001', '北京三日游', '北京', '2019-11-09 15:03:18', 1200.0, '不错的旅行', 0);
INSERT INTO `product` VALUES ('ee4057a1052111ea9831b025aa305adf', 'itcast-x00111', '暂无', '暂无', '2019-11-12 07:56:00', 0.0, '暂无', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `roleDesc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('095c6d6e563bd03e0d88f4ea2e7b622e', '测试4号', '没有描述！');
INSERT INTO `role` VALUES ('1111', 'ADMIN', 'vip');
INSERT INTO `role` VALUES ('2222', 'USER', 'vip');
INSERT INTO `role` VALUES ('47b4d175052111ea9831b025aa305adf', 'controller', '测试03');
INSERT INTO `role` VALUES ('5a9a7b3ffeb611e9944db025aa305adf', 'test', '测试账号');
INSERT INTO `role` VALUES ('69e635ea73449cd80b60bb08d186e348', '测试6号', '测试6号');
INSERT INTO `role` VALUES ('6d2ead96dd6899821542710e3d69e967', '测试5号', '没有描述！!');
INSERT INTO `role` VALUES ('cf896e7702e111eab5cfb025aa305adf', 'Mapper', '测试账号02');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `permissionId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`permissionId`, `roleId`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`permissionId`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('629530332a60394b219daa97cfe0b659', '1111');
INSERT INTO `role_permission` VALUES ('765a04fcfeae11e9944db025aa305adf', '1111');
INSERT INTO `role_permission` VALUES ('86e20f40feae11e9944db025aa305adf', '1111');
INSERT INTO `role_permission` VALUES ('c292c203052111ea9831b025aa305adf', '1111');
INSERT INTO `role_permission` VALUES ('cbd906e3fec411e9944db025aa305adf', '1111');
INSERT INTO `role_permission` VALUES ('d9c22ba102e111eab5cfb025aa305adf', '1111');
INSERT INTO `role_permission` VALUES ('765a04fcfeae11e9944db025aa305adf', '2222');
INSERT INTO `role_permission` VALUES ('86e20f40feae11e9944db025aa305adf', '2222');
INSERT INTO `role_permission` VALUES ('cbd906e3fec411e9944db025aa305adf', '2222');
INSERT INTO `role_permission` VALUES ('86e20f40feae11e9944db025aa305adf', '47b4d175052111ea9831b025aa305adf');
INSERT INTO `role_permission` VALUES ('cbd906e3fec411e9944db025aa305adf', '47b4d175052111ea9831b025aa305adf');
INSERT INTO `role_permission` VALUES ('765a04fcfeae11e9944db025aa305adf', '5a9a7b3ffeb611e9944db025aa305adf');
INSERT INTO `role_permission` VALUES ('cbd906e3fec411e9944db025aa305adf', '5a9a7b3ffeb611e9944db025aa305adf');
INSERT INTO `role_permission` VALUES ('629530332a60394b219daa97cfe0b659', '6d2ead96dd6899821542710e3d69e967');
INSERT INTO `role_permission` VALUES ('765a04fcfeae11e9944db025aa305adf', '6d2ead96dd6899821542710e3d69e967');
INSERT INTO `role_permission` VALUES ('765a04fcfeae11e9944db025aa305adf', 'cf896e7702e111eab5cfb025aa305adf');
INSERT INTO `role_permission` VALUES ('86e20f40feae11e9944db025aa305adf', 'cf896e7702e111eab5cfb025aa305adf');
INSERT INTO `role_permission` VALUES ('cbd906e3fec411e9944db025aa305adf', 'cf896e7702e111eab5cfb025aa305adf');
INSERT INTO `role_permission` VALUES ('d9c22ba102e111eab5cfb025aa305adf', 'cf896e7702e111eab5cfb025aa305adf');

-- ----------------------------
-- Table structure for syslog
-- ----------------------------
DROP TABLE IF EXISTS `syslog`;
CREATE TABLE `syslog`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `visitTime` timestamp(0) NULL DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `executionTime` int(11) NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of syslog
-- ----------------------------

-- ----------------------------
-- Table structure for traveller
-- ----------------------------
DROP TABLE IF EXISTS `traveller`;
CREATE TABLE `traveller`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SEX` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONENUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREDENTIALSTYPE` int(11) NULL DEFAULT NULL,
  `CREDENTIALSNUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TRAVELLERTYPE` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of traveller
-- ----------------------------
INSERT INTO `traveller` VALUES ('3FE27DF2A4E44A6DBC5D0FE4651D3D3E', '张龙', '男', '13333333333', 0, '123456789009876543', 0);
INSERT INTO `traveller` VALUES ('EE7A71FB6945483FBF91543DBE851960', '张小龙', '男', '15555555555', 0, '987654321123456789', 1);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5 加密',
  `phoneNum` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `STATUS` int(11) NULL DEFAULT NULL COMMENT '状态 账号是否开启 0关闭 1开启',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5 加盐',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('0ca2e08e98813e00d66c8b6377516f2a', 'test222@test12.com', 'test2', '34e42e55bd3615fd0b6a5110e88424c6', '13855566655', 1, 'ee7a2671ac93ebefd498cd5682cef0a6');
INSERT INTO `users` VALUES ('111-222', 'administrator@gmail.com', 'admin', '0e04b8a42b9a75e1a9fb5e7d2563d2c1', '8208820123', 1, '09a953e87921fff2e46e494f806802a4');
INSERT INTO `users` VALUES ('d94771f8026f8cb31d150f1601643144', 'test111@test1.com', 'test1', '07f8dda1519e11220f8485c8d432b706', '13855566677', 1, 'bcd753d0abc9e96c97eb2bc0e7a53447');

-- ----------------------------
-- Table structure for users_role
-- ----------------------------
DROP TABLE IF EXISTS `users_role`;
CREATE TABLE `users_role`  (
  `userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`userId`, `roleId`) USING BTREE,
  INDEX `users_role_ibfk_2`(`roleId`) USING BTREE,
  CONSTRAINT `users_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `users_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_role
-- ----------------------------
INSERT INTO `users_role` VALUES ('111-222', '095c6d6e563bd03e0d88f4ea2e7b622e');
INSERT INTO `users_role` VALUES ('0ca2e08e98813e00d66c8b6377516f2a', '1111');
INSERT INTO `users_role` VALUES ('111-222', '1111');
INSERT INTO `users_role` VALUES ('111-222', '2222');
INSERT INTO `users_role` VALUES ('111-222', '5a9a7b3ffeb611e9944db025aa305adf');
INSERT INTO `users_role` VALUES ('0ca2e08e98813e00d66c8b6377516f2a', '6d2ead96dd6899821542710e3d69e967');
INSERT INTO `users_role` VALUES ('111-222', 'cf896e7702e111eab5cfb025aa305adf');

SET FOREIGN_KEY_CHECKS = 1;
