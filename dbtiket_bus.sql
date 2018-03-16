-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 16, 2018 at 06:49 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbtiket_bus`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_bus`
--

CREATE TABLE `tb_bus` (
  `id_bus` char(5) NOT NULL,
  `class_bus` varchar(10) NOT NULL,
  `harga_tiket` int(6) NOT NULL,
  `no_polisi` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_bus`
--

INSERT INTO `tb_bus` (`id_bus`, `class_bus`, `harga_tiket`, `no_polisi`) VALUES
('BS001', 'Ekonomi', 70000, 'BS5611KB'),
('BS002', 'Bisnis-AC', 90000, 'BS6766KK'),
('BS003', 'Ekonomi', 56000, 'BK8868HJK');

-- --------------------------------------------------------

--
-- Table structure for table `tb_detailtransaksi`
--

CREATE TABLE `tb_detailtransaksi` (
  `id_detailtransaksi` int(10) NOT NULL,
  `id_transaksi` char(10) NOT NULL,
  `id_kursi` char(5) NOT NULL,
  `nama_penumpang` varchar(50) NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_detailtransaksi`
--

INSERT INTO `tb_detailtransaksi` (`id_detailtransaksi`, `id_transaksi`, `id_kursi`, `nama_penumpang`, `tanggal`) VALUES
(1, 'TRANS001', 'KS002', 'Erik', '2017-12-22 17:00:00'),
(2, 'TRANS001', 'KS001', 'KOKOM', '2017-12-23 17:00:00'),
(5, 'TRANS004', 'KS001', 'Dewi Sakinah', '2017-12-25 06:41:17'),
(9, 'TRANS005', 'KS005', 'Dimas', '2017-12-25 07:23:21'),
(10, 'TRANS005', 'KS006', 'Amel', '2017-12-25 07:23:38'),
(11, 'TRANS004', 'KS022', 'Nisa', '2017-12-25 08:20:36'),
(12, 'TRANS004', 'KS021', 'Erik', '2018-01-10 10:35:09'),
(13, 'TRANS004', 'KS013', 'Alisa Subandono', '2018-01-10 11:49:23'),
(14, 'TRANS001', 'KS019', 'Alisa', '2018-01-10 11:59:44'),
(15, 'TRANS001', 'KS025', 'Amel', '2018-01-11 14:45:28'),
(16, 'TRANS001', 'KS016', 'Dimas', '2018-01-11 14:48:08'),
(17, 'TRANS006', 'KS001', 'erik', '2018-01-11 16:09:30'),
(18, 'TRANS007', 'KS004', 'Dwi', '2018-01-11 16:12:01'),
(19, 'TRANS008', 'KS004', 'Dimas Bayu', '2018-01-11 16:17:23');

-- --------------------------------------------------------

--
-- Table structure for table `tb_jadwal`
--

CREATE TABLE `tb_jadwal` (
  `id_jadwal` char(5) NOT NULL,
  `tgl_berangkat` date NOT NULL,
  `jam_berangkat` time NOT NULL,
  `tujuan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_jadwal`
--

INSERT INTO `tb_jadwal` (`id_jadwal`, `tgl_berangkat`, `jam_berangkat`, `tujuan`) VALUES
('JD001', '2018-01-11', '18:00:00', 'Cikarang - Jakarta'),
('JD002', '2018-01-11', '20:00:00', 'Jakarta - Bandung');

-- --------------------------------------------------------

--
-- Table structure for table `tb_kursi`
--

CREATE TABLE `tb_kursi` (
  `id_kursi` char(5) NOT NULL,
  `nomor_kursi` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_kursi`
--

INSERT INTO `tb_kursi` (`id_kursi`, `nomor_kursi`) VALUES
('KS001', 'B01'),
('KS002', 'B02'),
('KS003', 'B03'),
('KS004', 'B04'),
('KS005', 'B05'),
('KS006', 'B06'),
('KS007', 'B07'),
('KS008', 'B08'),
('KS009', 'B09'),
('KS010', 'B10'),
('KS011', 'B11'),
('KS012', 'B12'),
('KS013', 'B13'),
('KS014', 'B14'),
('KS015', 'B15'),
('KS016', 'B16'),
('KS017', 'B17'),
('KS018', 'B18'),
('KS019', 'B19'),
('KS020', 'B20'),
('KS021', 'B21'),
('KS022', 'B22'),
('KS023', 'B23'),
('KS024', 'B24'),
('KS025', 'B25');

-- --------------------------------------------------------

--
-- Table structure for table `tb_supir`
--

CREATE TABLE `tb_supir` (
  `id_supir` char(5) NOT NULL,
  `nama_supir` varchar(50) NOT NULL,
  `no_sim` varchar(12) NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `jk` enum('L','P') NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_supir`
--

INSERT INTO `tb_supir` (`id_supir`, `nama_supir`, `no_sim`, `no_hp`, `jk`, `alamat`) VALUES
('SP001', 'Erik Wibowo', '12345678910', '081510815414', 'L', 'Kajen'),
('SP002', 'Dewi', '53454354554', '432534545', 'P', 'KDW'),
('SP003', 'Qomriyah', '3534543545', '464564656356565', 'P', 'Kajen');

-- --------------------------------------------------------

--
-- Table structure for table `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `id_transaksi` char(10) NOT NULL,
  `id_jadwal` char(5) NOT NULL,
  `id_user` char(5) NOT NULL,
  `id_supir` char(5) NOT NULL,
  `id_bus` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_transaksi`
--

INSERT INTO `tb_transaksi` (`id_transaksi`, `id_jadwal`, `id_user`, `id_supir`, `id_bus`) VALUES
('TRANS001', 'JD001', 'ID001', 'SP001', 'BS001'),
('TRANS004', 'JD002', 'ID001', 'SP003', 'BS002'),
('TRANS005', 'JD001', 'ID001', 'SP002', 'BS001'),
('TRANS006', 'JD002', 'ID001', 'SP003', 'BS002'),
('TRANS007', 'JD001', 'ID001', 'SP003', 'BS001'),
('TRANS008', 'JD002', 'ID001', 'SP001', 'BS002');

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `id_user` char(5) NOT NULL,
  `nama_user` varchar(50) NOT NULL,
  `password` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  `jk` enum('L','P') NOT NULL,
  `telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id_user`, `nama_user`, `password`, `alamat`, `jk`, `telp`) VALUES
('ID001', 'Erik Wibowo', 'admin', 'Kajen', 'L', '081510815414'),
('ID002', 'Qomariyah', 'admin', 'Kajen', 'P', '081510815414'),
('ID003', 'Dewi Sakinah', 'admin', 'Kedungwuni', 'P', '081510815414'),
('ID004', 'Afrizal Ahmed', 'admin', 'Sragi', 'L', '081510815414');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_bus`
--
ALTER TABLE `tb_bus`
  ADD PRIMARY KEY (`id_bus`);

--
-- Indexes for table `tb_detailtransaksi`
--
ALTER TABLE `tb_detailtransaksi`
  ADD PRIMARY KEY (`id_detailtransaksi`,`id_transaksi`,`id_kursi`),
  ADD KEY `id_transaksi` (`id_transaksi`),
  ADD KEY `id_kursi` (`id_kursi`);

--
-- Indexes for table `tb_jadwal`
--
ALTER TABLE `tb_jadwal`
  ADD PRIMARY KEY (`id_jadwal`);

--
-- Indexes for table `tb_kursi`
--
ALTER TABLE `tb_kursi`
  ADD PRIMARY KEY (`id_kursi`);

--
-- Indexes for table `tb_supir`
--
ALTER TABLE `tb_supir`
  ADD PRIMARY KEY (`id_supir`);

--
-- Indexes for table `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`id_transaksi`,`id_jadwal`,`id_user`,`id_supir`,`id_bus`),
  ADD KEY `id_bus` (`id_bus`),
  ADD KEY `id_supir` (`id_supir`),
  ADD KEY `id_jadwal` (`id_jadwal`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_detailtransaksi`
--
ALTER TABLE `tb_detailtransaksi`
  MODIFY `id_detailtransaksi` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_detailtransaksi`
--
ALTER TABLE `tb_detailtransaksi`
  ADD CONSTRAINT `tb_detailtransaksi_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `tb_transaksi` (`id_transaksi`),
  ADD CONSTRAINT `tb_detailtransaksi_ibfk_2` FOREIGN KEY (`id_kursi`) REFERENCES `tb_kursi` (`id_kursi`);

--
-- Constraints for table `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD CONSTRAINT `tb_transaksi_ibfk_1` FOREIGN KEY (`id_bus`) REFERENCES `tb_bus` (`id_bus`),
  ADD CONSTRAINT `tb_transaksi_ibfk_2` FOREIGN KEY (`id_supir`) REFERENCES `tb_supir` (`id_supir`),
  ADD CONSTRAINT `tb_transaksi_ibfk_3` FOREIGN KEY (`id_jadwal`) REFERENCES `tb_jadwal` (`id_jadwal`),
  ADD CONSTRAINT `tb_transaksi_ibfk_4` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
