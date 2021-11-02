/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : yummy_user

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 02/11/2021 22:16:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `uid`       bigint UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `email`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `phone_num` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NOT NULL,
    `password`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`uid`),
    UNIQUE INDEX `uidx_email` (`email`),
    UNIQUE INDEX `uidx_phone_num` (`phone_num`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`
(
    `id`         bigint UNSIGNED                                       NOT NULL AUTO_INCREMENT,
    `uid`        bigint UNSIGNED                                       NOT NULL,
    `raw`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `created_at` timestamp                                             NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `idx_uid` (`uid`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend_request
-- ----------------------------
DROP TABLE IF EXISTS `friend_request`;
CREATE TABLE `friend_request`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `from_uid`   bigint UNSIGNED NOT NULL,
    `to_uid`     bigint UNSIGNED NOT NULL,
    `created_at` timestamp       NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `idx_from_uid_to_uid` (`from_uid`, `to_uid`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friendship
-- ----------------------------
DROP TABLE IF EXISTS `friendship`;
CREATE TABLE `friendship`
(
    `id`       bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `from_uid` bigint UNSIGNED NOT NULL,
    `to_uid`   bigint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    unique INDEX `uidx_from_uid_to_uid` (`from_uid`, `to_uid`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for moment
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment`
(
    `id`         bigint UNSIGNED                                       NOT NULL AUTO_INCREMENT,
    `uid`        bigint UNSIGNED                                       NOT NULL,
    `access`     tinyint                                               NOT NULL,
    `raw`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `created_at` timestamp                                             NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `idx_uid_access` (`uid`, `access`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for feed
-- ----------------------------
DROP TABLE IF EXISTS `feed`;
CREATE TABLE `feed`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `from_uid`   bigint UNSIGNED NOT NULL,
    `to_uid`     bigint unsigned NOT NULL,
    `moment_id`  bigint UNSIGNED NOT NULL,
    `created_at` timestamp       NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `idx_to_id` (`to_uid`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`
(
    `id`   bigint UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `session_id` bigint UNSIGNED NOT NULL,
    `uid`        bigint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    unique INDEX `uidx_session_id_uid` (`session_id`, `uid`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for single_chat
-- ----------------------------
DROP TABLE IF EXISTS `single_chat`;
CREATE TABLE `single_chat`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `session_id` bigint UNSIGNED NOT NULL,
    `uid_1`      bigint UNSIGNED NOT NULL,
    `uid_2`      bigint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    index idx_session (`session_id`),
    unique index `idx_uid_1_uid_2` (`uid_1`, `uid_2`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`
(
    `id`         bigint                                                NOT NULL,
    `session_id` bigint                                                NOT NULL,
    `uid`        bigint                                                NOT NULL,
    `seq_id`     bigint                                                NOT NULL,
    `raw`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `created_at` timestamp                                             NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    unique INDEX `uidx_session_user_seq` (`session_id`, `uid`, `seq_id`),
    INDEX `uidx_session` (`session_id`) /* id 作为二级索引的一部分，有序的 */
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;
