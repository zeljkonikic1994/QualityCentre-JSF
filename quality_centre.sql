-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 14, 2018 at 05:09 PM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quality_centre`
--

-- --------------------------------------------------------

--
-- Table structure for table `completion_status`
--

DROP TABLE IF EXISTS `completion_status`;
CREATE TABLE IF NOT EXISTS `completion_status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `completion_status`
--

INSERT INTO `completion_status` (`status_id`, `name`) VALUES
(1, 'Success'),
(2, 'Failed'),
(3, 'No run'),
(4, 'Not completed');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) NOT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`project_id`, `project_name`) VALUES
(1, 'Project 1'),
(2, 'Project 2');

-- --------------------------------------------------------

--
-- Table structure for table `specificstep`
--

DROP TABLE IF EXISTS `specificstep`;
CREATE TABLE IF NOT EXISTS `specificstep` (
  `id` int(11) NOT NULL,
  `test_set_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `expected` varchar(255) NOT NULL,
  `folder` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`test_set_id`),
  KEY `fk_specific_test_set` (`test_set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `specificstep`
--

INSERT INTO `specificstep` (`id`, `test_set_id`, `name`, `description`, `expected`, `folder`) VALUES
(1, 13, 'Game_Load', 'Load the game on an iOS device', 'Game loads normally', 'Compatibility_test'),
(1, 14, 'Plug in headphones', 'Launch the game and plug in the headphones', 'Sound shoud continue playing through headphones', 'Sound_test'),
(1, 15, 'Request timeout', 'Wait until request times out', 'Game should display a message \"There has been an error\"', 'Timeout_test'),
(1, 19, 'Session timeout', 'Idle to session timeout', 'Game should display a message \"There has been an error\"', 'Timeout_test'),
(2, 13, 'Orientation', 'Launch the game in portrait or landscape\r\nChange orientation', 'Game scales fine during orientation change', 'Compatibility_test'),
(2, 14, 'Plug out headphones', 'Launch the game with headphones plugged in and plug them out', 'Sound shoud continue playing through speakers', 'Sound_test'),
(2, 15, 'Session timeout', 'Idle to session timeout', 'Game should display a message \"There has been an error\"', 'Timeout_test'),
(2, 19, 'Session timeout', 'Idle to session timeout', 'Game should display a message \"There has been an error\"', 'Timeout_test'),
(3, 13, 'Accept call', 'During the game is launched, call the device from another device', 'Game should pause during the conversation', 'Compatibility_test'),
(3, 15, 'Basic functionality', 'Make sure that game basic functionalities are working', 'All basic functionalities are working\r\n', 'Smoke_test'),
(4, 13, 'Responsive', 'Make sure that game is responsive', 'Game is responsive', 'Compatibility_test'),
(4, 15, 'Bonus functionality', 'Make sure that game bonus functionalities are working', 'All bonus functionalities are working\r\n', 'Smoke_test'),
(5, 13, 'Plug in headphones', 'Launch the game and plug in the headphones', 'Sound shoud continue playing through headphones', 'Sound_test'),
(5, 15, 'Sound', 'Make sure that sound is ok', 'Sound is ok', 'Smoke_test'),
(6, 13, 'Plug out headphones', 'Launch the game with headphones plugged in and plug them out', 'Sound shoud continue playing through speakers', 'Sound_test'),
(6, 15, 'Responsive', 'Make sure that game is responsive', 'Game is responsive', 'Smoke_test'),
(7, 13, 'Plug in headphones', 'Launch the game and plug in the headphones', 'Sound shoud continue playing through headphones', 'Sound_test');

-- --------------------------------------------------------

--
-- Table structure for table `step`
--

DROP TABLE IF EXISTS `step`;
CREATE TABLE IF NOT EXISTS `step` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `expected` varchar(255) NOT NULL,
  PRIMARY KEY (`step_id`,`test_id`),
  KEY `fk_step_test` (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4334 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `step`
--

INSERT INTO `step` (`step_id`, `test_id`, `name`, `description`, `expected`) VALUES
(1, 68, 'Game_Load', 'Load the game on an iOS device', 'Game loads normally'),
(1, 69, 'Plug in headphones', 'Launch the game and plug in the headphones', 'Sound shoud continue playing through headphones'),
(1, 70, 'Request timeout', 'Wait until request times out', 'Game should display a message \"There has been an error\"'),
(1, 71, 'Basic functionality', 'Make sure that game basic functionalities are working', 'All basic functionalities are working\r\n'),
(2, 68, 'Orientation', 'Launch the game in portrait or landscape\r\nChange orientation', 'Game scales fine during orientation change'),
(2, 69, 'Plug out headphones', 'Launch the game with headphones plugged in and plug them out', 'Sound shoud continue playing through speakers'),
(2, 70, 'Session timeout', 'Idle to session timeout', 'Game should display a message \"There has been an error\"'),
(2, 71, 'Bonus functionality', 'Make sure that game bonus functionalities are working', 'All bonus functionalities are working\r\n'),
(3, 68, 'Accept call', 'During the game is launched, call the device from another device', 'Game should pause during the conversation'),
(3, 71, 'Sound', 'Make sure that sound is ok', 'Sound is ok'),
(4, 71, 'Responsive', 'Make sure that game is responsive', 'Game is responsive'),
(555, 68, 'STEP 1', 'DESC', 'EXP');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_created` date NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`test_id`, `date_created`, `name`) VALUES
(68, '2018-03-02', 'Compatibility_test'),
(69, '2018-03-02', 'Sound_test'),
(70, '2018-03-02', 'Timeout_test'),
(71, '2018-03-02', 'Smoke_test');

-- --------------------------------------------------------

--
-- Table structure for table `testset`
--

DROP TABLE IF EXISTS `testset`;
CREATE TABLE IF NOT EXISTS `testset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `date_created` date NOT NULL,
  `date_modified` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `testset`
--

INSERT INTO `testset` (`id`, `name`, `date_created`, `date_modified`) VALUES
(13, 'CASINO_MONKEY_PRINCE', '2018-03-02', '2018-03-02'),
(14, 'CASINO_BALLONIES', '2018-03-02', '2018-03-02'),
(15, 'TABLE_BLACKJACK', '2018-03-02', '2018-03-02'),
(19, 'AAA', '2018-03-14', '2018-03-14'),
(20, 'BBB', '2018-03-14', '2018-03-14');

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
CREATE TABLE IF NOT EXISTS `type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`, `name`) VALUES
(1, 'Admin'),
(2, 'Project manager'),
(3, 'Senior tester'),
(4, 'Tester');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `Name` varchar(145) NOT NULL,
  `LastName` varchar(145) NOT NULL,
  `Gender` varchar(1) NOT NULL,
  `Mail` varchar(145) NOT NULL,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Type` int(11) NOT NULL,
  `RequestApproved` int(11) NOT NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`),
  KEY `Type` (`Type`),
  KEY `Type_2` (`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Name`, `LastName`, `Gender`, `Mail`, `UserName`, `Password`, `Type`, `RequestApproved`) VALUES
('aaa', 'aaa', 'm', 'aaa@aaa.com', 'aaaa', 'Pass123#', 3, 1),
('Jelica', 'Cincovic', 'f', 'jelica94@rocketmail.com', 'admin', 'Admin123#', 1, 1),
('Marko', 'Djordjevic', 'm', 'mdj@gmail.com', 'djordjevic', 'Pilot123#', 4, 1),
('Dusica', 'Spasic', 'f', 'ds@gmail.com', 'dusicaspasic', 'Stju123#', 3, 1),
('Filip', 'Filipovic', 'm', 'ff@gmail.com', 'filipovic', 'Admin123#', 3, 1),
('Katarina', 'Sreckovic', 'f', 'ks@gmail.com', 'katarinasreckovic', 'Stju123#', 3, 1),
('Nemanja', 'Maric', 'm', 'nm@gmail.com', 'maric', 'Pilot123#', 4, 1),
('Boban', 'Marjanovic', 'm', 'bm@gmail.com', 'marjanovic', 'Pilot123#', 1, 1),
('mika', 'Mikic', 'f', 'mika@gmail.com', 'mika', 'Work123#', 4, 1),
('Neda', 'Stankovic', 'f', 'ns@gmail.com', 'nedastankovic', 'Stju123#', 3, 1),
('Pera', 'Peric', 'm', 'pp@gmail.com', 'pera', 'Work123#', 4, 1),
('Milos', 'Teodosic', 'm', 'mt@gmail.com', 'teodosic', 'Pilot123#', 2, 1),
('Test', 'Test', 'm', 'test@test.com', 'test', 'Pass123#', 2, 1),
('Test ', 'Test', 'm', 'a@a.com', 'testUser', 'Test123.', 2, 1),
('Tijana', 'Dimitrijevic', 'f', 'td@gmail.com', 'tijanadimitrijevic', 'Stju123#', 3, 1),
('Vesna', 'Vulovic', 'f', 'vv@gmail.com', 'vesnavulovic', 'Stju123#', 3, 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `specificstep`
--
ALTER TABLE `specificstep`
  ADD CONSTRAINT `fk_specific_test_set` FOREIGN KEY (`test_set_id`) REFERENCES `testset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `step`
--
ALTER TABLE `step`
  ADD CONSTRAINT `fk_step_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_type` FOREIGN KEY (`Type`) REFERENCES `type` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
