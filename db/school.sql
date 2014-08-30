-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 30, 2014 at 03:46 PM
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
  `tid` int(11) DEFAULT NULL COMMENT '教师ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员表' AUTO_INCREMENT=3 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `account`, `password`, `rid`, `time`, `tid`) VALUES
(1, 'admin', 'admin', 1, '1409413041310', NULL),
(2, '1', '1', 5, '1407894986555', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `assign`
--

CREATE TABLE IF NOT EXISTS `assign` (
  `gid` int(11) NOT NULL COMMENT '年级ID',
  `cid` int(11) NOT NULL COMMENT '课程编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学期排课表';

--
-- Dumping data for table `assign`
--

INSERT INTO `assign` (`gid`, `cid`) VALUES
(1, 1),
(1, 2),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 6),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(9, 1),
(9, 2),
(9, 3),
(9, 4),
(9, 7),
(9, 8),
(9, 9),
(9, 10),
(9, 11),
(8, 1),
(8, 2),
(8, 3),
(8, 4),
(8, 7),
(8, 8),
(8, 9),
(8, 10),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 7),
(7, 8),
(2, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE IF NOT EXISTS `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(8) NOT NULL COMMENT '班级编号',
  `name` varchar(20) DEFAULT NULL COMMENT '班级名称',
  `tid` int(11) DEFAULT NULL COMMENT '班主任编号',
  `sid` int(11) DEFAULT NULL COMMENT '学校编号',
  `year` varchar(20) DEFAULT NULL COMMENT '入学年份',
  `sort` int(11) DEFAULT '0' COMMENT '排序值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='班级' AUTO_INCREMENT=15 ;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`id`, `uuid`, `name`, `tid`, `sid`, `year`, `sort`) VALUES
(3, '20010000', '2001级00班', 1, 1, '2001', 77),
(4, '20010002', '2001级02班', NULL, 1, '2001', 55),
(5, '20010003', '2001级03班', NULL, 1, '2001', 66),
(6, '20010004', '2001级04班', NULL, 1, '2001', 77),
(7, '20020001', '2002级01班', NULL, 1, '2002', 22),
(8, '20010005', '2001级05班', NULL, 1, '2001', 44),
(9, '20030001', '2003级01班', NULL, 1, '2003', 55),
(10, '20030002', '2003级02班', NULL, 1, '2003', 33),
(11, '20040001', '2004级01班', NULL, 1, '2004', 36),
(12, '20070001', '2007级01班', NULL, 1, '2007', 55),
(14, '20020002', '2002级02班', 1, 1, '2002', NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='课程表' AUTO_INCREMENT=12 ;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `uuid`, `name`, `remark`, `sort`) VALUES
(1, 'VMzal9Ll', '数学', '数学(Mathematics)是研究数量、结构、变化以及空间等概念的一门学科,从某种角度看属于形式科学的一种。', 99),
(2, 'BxGnxHOJ', '语文', '语文，一般认为是语言和文字的综合科。语言和文章、语言知识和文化知识的简约式统称等都离不开它。它是听、说、读、写、译、编等语言文字能力和知识，文化知识的统称。语文的能力是学习其他学科和科学的基础，也是一门重要的人文社会学科，人们交流思想的工具。哲学家认为语文是进行表述、记录、传递口头或书面信息的文字言词的物质存在形式；语文是描述事实、引证思维、陈述思想、表达意志、抒发情怀以及改造事物和思想的信息定位的一种意识存在内容。语文具有统一的工具性与人文性等性质。语文也是中国的学校等教育机构开设的一门主要学科，中国语文教科书一般讲授的是汉语文。', 88),
(3, '9otiqXIW', '英语', '英语是世界上最被广泛使用的第二语言，是欧盟和许多国际组织与英联邦国家的官方语言之一，也是联合国的工作语言之一。英语的母语使用者数量位居世界第二，少于标准汉语，但上两个世纪英国和美国在文化、经济、军事、政治和科学上的领先地位使得英语成为一种国际语言，如今许多国际场合都使用英语作为沟通的媒介。', 88),
(4, 'fsYyrdWD', '思想品德', '思想品德课程是为小学（小学的思想品德叫“品德”）、初中学生（初中包括初中以后的思想品德叫“政治”）思想品德健康发展奠定基础的一门综合性的必修课程。', 66),
(5, 'L7RbDFK4', '生活', '生活指为幸福的意义而存在。\r\n经济的发展带动了价值的体现，实现我们的梦想，带着我们走进先进科学社会，懂得生活的乐趣。\r\n生活也是体现人类这种生命的所有的日常活动和经历的总和。\r\n广义上指人的各种活动，包括日常生活行动、学习、工作、休闲、社交、娱乐等职业生活，个人生活，家庭生活和社会生活以及玩味生活。', 97),
(6, '03LZzZbX', '自然', '自然，哲学术语，既用作名词指具有无穷多样性的一切存在物，也用作形容词指天然的，非人为的或不做作，不刻意，不拘束，不呆板。自然泛指自然界。是人类的生活场所，人们离不开自然，必须保护自然。', 77),
(7, 'i8kwOpBf', '物理', '物理课程是按照教学目的(或目标)、要求，以及学生的认识规律，有计划地选取物理科学的内容，并将其改造成为学校的一门课程。', 44),
(8, '47UVnmYr', '化学', '化学是自然科学的一种，在分子、原子层次上研究物质性质、组成、结构与变化规律；创造新物质的科学。世界由物质组成，化学则是人类用以认识和改造物质世界的主要方法和手段之一。它是一门历史悠久而又富有活力的学科，它的成就是社会文明的重要标志，化学中存在着化学变化和物理变化两种变化形式。', 57),
(9, 'MTJ0FjzR', '历史', '历史，简称史，是记载和解释作为一系列人类活动进程的历史事件的一门学科，多数时候也是对当下时代的映射。如果仅仅只是总结和映射，那么历史作为一个存在，就应该消失。历史的问题在于不断发现真的过去，在于用材料说话，让人如何在现实中可能成为可以讨论的问题。', 56),
(10, 'EmL8rODr', '地理', '地理学是研究地球表面的地理环境中各种自然现象和人文现象，以及它们之间相互关系的学科。人们学习了地理学，便可以更深入地了解地球的构造以及各地区的环境差异。', 33),
(11, 'lf0A8kU9', '生物', '是当代的重大科学课题，然而却又是至今依旧了解甚少的最基本的生物学问题。', 23);

-- --------------------------------------------------------

--
-- Table structure for table `grade`
--

CREATE TABLE IF NOT EXISTS `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='年级表' AUTO_INCREMENT=10 ;

--
-- Dumping data for table `grade`
--

INSERT INTO `grade` (`id`, `name`) VALUES
(1, '一年级'),
(2, '二年级'),
(3, '三年级'),
(4, '四年级'),
(5, '五年级'),
(6, '六年级'),
(7, '七年级'),
(8, '八年级'),
(9, '九年级');

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
(23, '分配课程', '/admin/assign/index', 8),
(24, '学生成绩列表', '/admin/score/index', 9),
(25, '学生成绩录入', '/admin/score/add', 9);

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
  `phone` varchar(45) NOT NULL COMMENT '电话',
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学校信息表' AUTO_INCREMENT=2 ;

--
-- Dumping data for table `school`
--

INSERT INTO `school` (`id`, `uuid`, `name`, `desc`, `level`, `address`, `rector`, `phone`, `fax`, `web`, `email`, `kind`, `sort`, `image`, `time`) VALUES
(1, 'S4dSkbzM', '佳木斯市第六小学', '1962年更名为师范附小。1970年成立独立学校，该名为五七小学。1980年根据市教育局的统一要求，按建校时间顺序，正式定名为佳木斯市第六小学。建校以来，几代六小人薪火相传，不懈奋斗，走过了五十五年的风雨历程。在半个多世纪的历史画卷上写满了凝重与辉煌，积淀了丰富的文化底蕴，培养了大批优秀人才。', 0, '黑龙江省佳木斯市中山街211号', '张龙江', '0454-8223289', '0454-8223289', 'http://www.lxschool.net/', 'jialiuxiaoxue@163.com', 0, 99, '/upload/avatar/1409073481951.jpg', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

CREATE TABLE IF NOT EXISTS `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL COMMENT '学期ID',
  `sid` int(11) NOT NULL COMMENT '学生学号',
  `cid` int(11) NOT NULL COMMENT '课程编号',
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
  `sid` int(11) NOT NULL COMMENT '学校ID',
  `cid` int(11) NOT NULL COMMENT '班级ID',
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
  `sid` int(11) NOT NULL COMMENT '学校编号',
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='教师信息表' AUTO_INCREMENT=2 ;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`id`, `uuid`, `name`, `identity`, `rid`, `sid`, `sex`, `birth`, `birthplace`, `national`, `feature`, `desc`, `education`, `address`, `seniority`, `phone`, `email`, `status`, `sort`, `image`, `time`) VALUES
(1, '10703080', '王大狗', '130182197809231130', 3, 1, 0, '1978-09-23', '河北省石家庄市南古村', '汉', 1, 'adfasdfasfasfasfsfasf', 1, '河北省石家庄市桥西区', 1, '13983154436', 'wangdagou@163.com', 1, 99, '/upload/avatar/1409323065811.jpg', NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学期表' AUTO_INCREMENT=37 ;

--
-- Dumping data for table `term`
--

INSERT INTO `term` (`id`, `name`, `start`, `end`) VALUES
(23, '2007-2008学年第1学期', '2007年9月', '2008年1月'),
(24, '2007-2008学年第2学期', '2008年2月', '2008年7月'),
(25, '2008-2009学年第1学期', '2008年9月', '2009年1月'),
(26, '2008-2009学年第2学期', '2009年2月', '2009年7月'),
(27, '2009-2010学年第1学期', '2009年9月', '2010年1月'),
(28, '2009-2010学年第2学期', '2010年2月', '2010年7月'),
(29, '2010-2011学年第1学期', '2010年9月', '2011年1月'),
(30, '2010-2011学年第2学期', '2011年2月', '2011年7月'),
(31, '2011-2012学年第1学期', '2011年9月', '2012年1月'),
(32, '2011-2012学年第2学期', '2012年2月', '2012年7月'),
(33, '2012-2013学年第1学期', '2012年9月', '2013年1月'),
(34, '2012-2013学年第2学期', '2013年2月', '2013年7月'),
(35, '2013-2014学年第1学期', '2013年9月', '2014年1月'),
(36, '2013-2014学年第2学期', '2014年2月', '2014年7月');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
