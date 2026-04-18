-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 13 Jan 2026 pada 13.17
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nmp`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `my_friends`
--

CREATE TABLE `my_friends` (
  `id` int(11) NOT NULL,
  `nrp` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `student`
--

CREATE TABLE `student` (
  `nama` varchar(100) DEFAULT NULL,
  `nrp` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `program` varchar(50) DEFAULT NULL,
  `about` text DEFAULT NULL,
  `my_course` text DEFAULT NULL,
  `my_experiences` text DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `student`
--

INSERT INTO `student` (`nama`, `nrp`, `email`, `program`, `about`, `my_course`, `my_experiences`, `photo_url`) VALUES
('Angela Wong (SUPRA)', '123456678', 'wong@gmail.com', 'NCS', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut justo massa, commodo eu finibus at.', 'Cryptography, Information and Security Assurance', 'Participated in network security lab, practiced penetration testing, secured WiFi networks, and learned basic cryptography.\r\n', 'https://i.pinimg.com/736x/66/01/a4/6601a46f4b756ea99c5c685696de0ee7.jpg'),
('Laurentius Deaven', '160423003', 's160423003@student.ubaya.ac.id', 'IMES', 'Suka tidur di pagi hari dan bangun di malam hari, itulah saya.', 'Web Programming, Mobile App Development, UI/UX Design', 'Freelance web developer, UI designer for startup', 'https://my.ubaya.ac.id/img/mhs/160423003_l.jpg'),
('William Haryanto', '160423008', 's160423008@student.ubaya.ac.id', 'DSAI', 'Saya adalah mahasiswa yang ambis untuk belajar dan berkarya', 'Data Mining, Computer Vision, Emerging Technology', 'Data Analyst PT Kapal api', 'https://my.ubaya.ac.id/img/mhs/160423008_l.jpg'),
('Aria Hansa Junior', '160423037', 's160423037@student.ubaya.ac.id', 'DSAI', 'Mahasiswa rajin menabung dan tidak sombong. Rank Mobile Legends saya adalah mythic immortal', 'Machine learning, Applied Multivariate Analysis', 'Finance staff at BCA, Treasurer of campus organization', 'https://my.ubaya.ac.id/img/mhs/160423037_l.jpg'),
('Monica (SOLAR)', '38469843', 'monica@gmail.com', 'DMT', 'In dapibus, erat finibus porttitor tristique, risus erat iaculis ligula, in laoreet ligula erat vel mauris.', 'Database Systems, Cloud Computing, Software Engineering', 'Backend developer intern, Built REST API projects', 'https://i.pinimg.com/736x/2f/9a/6a/2f9a6ad6632f6dcf4f85ff3e02943c93.jpg'),
('Dean Kirkwood (HALILINTAR)', '45235236', 'dean@gmail.com', 'GD', 'Aenean auctor iaculis sem nec condimentum. Quisque consequat sapien ut turpis tincidunt.', 'Game Development, Computer Graphics, Unity Engine', 'Indie game developer, Game jam winner', 'https://static.wikia.nocookie.net/boboiboy/images/3/39/BoBoiBoy_Halilintar.png/revision/latest?cb=20230813102718&path-prefix=id');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `my_friends`
--
ALTER TABLE `my_friends`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nrp` (`nrp`);

--
-- Indeks untuk tabel `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`nrp`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `my_friends`
--
ALTER TABLE `my_friends`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `my_friends`
--
ALTER TABLE `my_friends`
  ADD CONSTRAINT `my_friends_ibfk_1` FOREIGN KEY (`nrp`) REFERENCES `student` (`nrp`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
