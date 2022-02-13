/*
Navicat MySQL Data Transfer

Source Server         : lixin
Source Server Version : 80025
Source Host           : localhost:3306
Source Database       : javase

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2022-02-13 14:33:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cv
-- ----------------------------
DROP TABLE IF EXISTS `cv`;
CREATE TABLE `cv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `now_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `education` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `work_experience` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `school_experience` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `job_experience` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `self_introduction` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of cv
-- ----------------------------
INSERT INTO `cv` VALUES ('2', '17', '测试', '836128500@qq.com', '13983788107', '成都', '本科', '工作经历修改', '学校经历', '测试', '自我介绍');

-- ----------------------------
-- Table structure for cv_offer
-- ----------------------------
DROP TABLE IF EXISTS `cv_offer`;
CREATE TABLE `cv_offer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `now_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `work_experience` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `school_experience` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `job_experience` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `self_introduction` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of cv_offer
-- ----------------------------
INSERT INTO `cv_offer` VALUES ('1', '17', '2', '1', '测试', '836128500@qq.com', '13983788107', '成都', '本科', '工作经历修改', '学校经历', '测试', '自我介绍');

-- ----------------------------
-- Table structure for job_information
-- ----------------------------
DROP TABLE IF EXISTS `job_information`;
CREATE TABLE `job_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `content` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `position` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `salaries` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `frequency` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `period` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `work_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of job_information
-- ----------------------------
INSERT INTO `job_information` VALUES ('2', '16', '测试内容1', '测试岗位', '测试薪水', '测试频率', '测试时间段', '测试', '工作地点');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `hashed_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '$2a$10$yzLVkZDSlK1S/teesDR7su5juCcjCjiksPXFaJ3WqFnLko8udQ3la', null, '');
INSERT INTO `user` VALUES ('13', 'lixin', '$2a$10$734M4YE8tT.IVOgbdcCx.uVnmZhdSvKG0d77enyt4/ZgYr0MUNb5O', '836128500@qqcom', 'ROLE_EMPLOYER');
INSERT INTO `user` VALUES ('15', 'lixin2', '$2a$10$BypyErRvS/yp8wCTD.QOl.AW8riNSU2yi/ia1O5MzTbhiAcJsbaN.', '836128500@qqcom', 'ROLE_EMPLOYEE');
INSERT INTO `user` VALUES ('16', 'ad', null, null, 'ROLE_EMPLOYER');
INSERT INTO `user` VALUES ('17', 'aq', null, null, 'ROLE_EMPLOYEE');
