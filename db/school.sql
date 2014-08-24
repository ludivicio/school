-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 24, 2014 at 01:17 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `school`
--
CREATE DATABASE IF NOT EXISTS `school` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `school`;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) NOT NULL COMMENT '管理员账号',
  `password` varchar(45) NOT NULL COMMENT '管理员密码',
  `rid` int(11) NOT NULL COMMENT '角色ID',
  `time` varchar(45) NOT NULL COMMENT '登录时间',
  `tuuid` int(11) DEFAULT NULL COMMENT '教师编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员表' AUTO_INCREMENT=3 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `account`, `password`, `rid`, `time`, `tuuid`) VALUES
(1, 'admin', 'admin', 1, '1408885010799', NULL),
(2, '1', '1', 5, '1407894986555', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `assign`
--

CREATE TABLE IF NOT EXISTS `assign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL COMMENT '学期ID',
  `gid` int(11) NOT NULL COMMENT '年级ID',
  `cuuid` varchar(8) NOT NULL COMMENT '课程编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学期排课表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE IF NOT EXISTS `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(8) NOT NULL COMMENT '班级编号',
  `name` varchar(20) DEFAULT NULL COMMENT '编辑名称',
  `tuuid` int(11) DEFAULT NULL COMMENT '班主任编号',
  `suuid` int(11) DEFAULT NULL COMMENT '学校编号',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(8) NOT NULL COMMENT '8位的课程编号',
  `name` varchar(45) NOT NULL COMMENT '课程名称',
  `remark` text COMMENT '课程备注',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `grade`
--

CREATE TABLE IF NOT EXISTS `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='年级表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '权限名称',
  `url` varchar(45) DEFAULT NULL COMMENT 'url',
  `pid` int(11) DEFAULT NULL COMMENT '父权限ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表' AUTO_INCREMENT=26 ;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`id`, `name`, `url`, `pid`) VALUES
(2, '学校信息管理', '/admin/school', 0),
(3, '班级信息管理', '/admin/class', 0),
(4, '教师信息管理', '/admin/teacher', 0),
(5, '学生信息管理', '/admin/student', 0),
(6, '学期管理', '/admin/term', 0),
(7, '课程管理', '/admin/course', 0),
(8, '学期课程分配', '/admin/assign', 0),
(9, '成绩管理', '/admin/score', 0),
(12, '学校列表', '/admin/school/index', 2),
(13, '录入学校信息', '/admin/school/add', 2),
(14, '班级列表', '/admin/class/index', 3),
(15, '录入班级信息', '/admin/class/add', 3),
(16, '教师列表', '/admin/teacher/index', 4),
(17, '录入教师信息', '/admin/teacher/add', 4),
(18, '学生列表', '/admin/student/index', 5),
(19, '录入学生信息', '/admin/student/add', 5),
(20, '查看学期', '/admin/term/index', 6),
(21, '课程列表', '/admin/course/index', 7),
(22, '添加课程', '/admin/course/add', 7),
(23, '分配课程', '/admin/assign/process', 8),
(24, '学生成绩列表', '/admin/score/index', 9),
(25, '学生成绩录入', '/admin/score/input', 9);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '角色名',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表' AUTO_INCREMENT=5 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `remark`) VALUES
(1, '系统管理员', '拥有所有权限'),
(2, '数据管理员', '管理学校，班级，教师，学生等实体的信息录入'),
(3, '班主任', '只有录入学生成绩的权限'),
(4, '任课教师', '普通角色，无管理权限');

-- --------------------------------------------------------

--
-- Table structure for table `role_permission`
--

CREATE TABLE IF NOT EXISTS `role_permission` (
  `rid` int(11) NOT NULL COMMENT '角色ID',
  `pid` int(11) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限对应表';

--
-- Dumping data for table `role_permission`
--

INSERT INTO `role_permission` (`rid`, `pid`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(3, 1),
(3, 9);

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

CREATE TABLE IF NOT EXISTS `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(8) NOT NULL COMMENT '8位学校编号',
  `name` varchar(45) NOT NULL COMMENT '学校名称',
  `desc` text COMMENT '学校介绍',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '学校级别（小学、中学）',
  `address` varchar(100) NOT NULL COMMENT '学校地址',
  `rector` varchar(16) NOT NULL COMMENT '校长姓名',
  `phone` varchar(11) NOT NULL COMMENT '电话',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `web` varchar(100) DEFAULT NULL COMMENT '网址',
  `email` varchar(100) NOT NULL COMMENT '电子邮箱',
  `kind` int(11) NOT NULL DEFAULT '0' COMMENT '学校类型（公立、私立）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `image` varchar(100) DEFAULT NULL COMMENT '图片url',
  `time` varchar(45) DEFAULT NULL COMMENT '信息录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

CREATE TABLE IF NOT EXISTS `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL COMMENT '学期ID',
  `suuid` varchar(11) NOT NULL COMMENT '学生学号',
  `cuuid` varchar(8) NOT NULL COMMENT '课程编号',
  `score` int(11) NOT NULL COMMENT '分数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生成绩表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(8) NOT NULL COMMENT '11位学号',
  `name` varchar(45) NOT NULL COMMENT '姓名',
  `identity` varchar(18) NOT NULL COMMENT '身份证',
  `cuuid` int(11) NOT NULL COMMENT '班级ID',
  `sex` int(11) NOT NULL COMMENT '性别',
  `birth` varchar(45) NOT NULL COMMENT '出生日期',
  `birthplace` varchar(45) NOT NULL COMMENT '籍贯',
  `national` varchar(10) NOT NULL COMMENT '民族',
  `feature` int(11) NOT NULL DEFAULT '0' COMMENT '政治面貌（党员，团员，群众）',
  `desc` text COMMENT '学生介绍',
  `address` varchar(100) NOT NULL COMMENT '家庭住址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（在职，病退，退休，休假）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `image` varchar(100) DEFAULT NULL COMMENT '图片url',
  `time` varchar(45) DEFAULT NULL COMMENT '信息录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(8) NOT NULL COMMENT '11位教师编号',
  `name` varchar(45) NOT NULL COMMENT '姓名',
  `identity` varchar(18) NOT NULL COMMENT '身份证',
  `rid` int(11) NOT NULL COMMENT '角色ID',
  `suuid` int(11) NOT NULL COMMENT '学校编号',
  `sex` int(11) NOT NULL COMMENT '性别',
  `birth` varchar(45) NOT NULL COMMENT '出生日期',
  `birthplace` varchar(45) NOT NULL COMMENT '籍贯',
  `national` varchar(10) NOT NULL COMMENT '民族',
  `feature` int(11) NOT NULL DEFAULT '0' COMMENT '政治面貌（党员，团员，群众）',
  `desc` text COMMENT '教师介绍',
  `education` int(11) NOT NULL DEFAULT '0' COMMENT '学历（学士，硕士，博士）',
  `address` varchar(100) NOT NULL COMMENT '家庭住址',
  `seniority` int(11) NOT NULL COMMENT '教龄',
  `phone` varchar(11) NOT NULL COMMENT '电话',
  `email` varchar(100) NOT NULL COMMENT '电子邮箱',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（在职，病退，退休，休假）',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `image` varchar(100) DEFAULT NULL COMMENT '图片url',
  `time` varchar(45) DEFAULT NULL COMMENT '信息录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师信息表' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `term`
--

CREATE TABLE IF NOT EXISTS `term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '学期名称',
  `start` varchar(45) DEFAULT NULL COMMENT '学期开始时间',
  `end` varchar(45) DEFAULT NULL COMMENT '学期结束时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学期表' AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
