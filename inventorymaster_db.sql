-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 22 Tem 2022, 15:11:14
-- Sunucu sürümü: 10.4.24-MariaDB
-- PHP Sürümü: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `inventorymaster_db`
--

DELIMITER $$
--
-- Yordamlar
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_delpeted_products` ()   BEGIN  
    SELECT * FROM products WHERE quantity = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_paroducts_byquantity` (IN `quantity` DOUBLE)   BEGIN
    SELECT * FROM products WHERE products.quantity = quantity;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product`
--

CREATE TABLE `product` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `quantity` float NOT NULL,
  `SupplierID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `product`
--

INSERT INTO `product` (`ID`, `name`, `quantity`, `SupplierID`) VALUES
(15, 'Beko T145', 56, 1),
(16, 'Arçelik AK567 A+', 77, 1),
(20, 'Yellow 56 T 32', 4, 1),
(22, 'Karma Z45', 0, 3);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `supplier`
--

CREATE TABLE `supplier` (
  `ID` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `supplier`
--

INSERT INTO `supplier` (`ID`, `Name`) VALUES
(1, 'Farma'),
(2, 'Gama'),
(3, 'Zama Co.');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Supplier` (`SupplierID`);

--
-- Tablo için indeksler `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`ID`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `product`
--
ALTER TABLE `product`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Tablo için AUTO_INCREMENT değeri `supplier`
--
ALTER TABLE `supplier`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK_Supplier` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`ID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
