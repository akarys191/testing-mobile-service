
CREATE TABLE testing_mobile (
  id INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(50),
  version INT,
  PRIMARY KEY (id)
);


CREATE TABLE tester (
     id INT AUTO_INCREMENT NOT NULL,
     user_name VARCHAR(50),
     full_name VARCHAR(50),
     PRIMARY KEY (id)
);

CREATE TABLE mobile_booking_journal (
  id INT AUTO_INCREMENT NOT NULL,
  testing_mobile_id INT NOT NULL,
  tester_id INT NOT NULL,
  booking_date TIMESTAMP NOT NULL,
  release_date TIMESTAMP DEFAULT NULL,
  CONSTRAINT `fk_mobile_booking_tester_id_data` FOREIGN KEY (`tester_id`) REFERENCES `tester` (`id`),
  CONSTRAINT `fk_mobile_booking_testing_mobile_id_data` FOREIGN KEY (`testing_mobile_id`) REFERENCES `testing_mobile` (`id`),
  PRIMARY KEY (id)
);
