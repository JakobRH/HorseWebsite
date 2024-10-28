
CREATE TABLE IF NOT EXISTS owner
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NOT NULL
);



CREATE TABLE IF NOT EXISTS horse
(
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  rating      INT NOT NULL CHECK (rating > 0 AND rating < 6),
  birth_date  DATE         NOT NULL,
  race        VARCHAR(255) NOT NULL,
  photo       CLOB NOT NULL,
  owner_id    INT,
  created_at  DATETIME     NOT NULL,
  updated_at  DATETIME     NOT NULL,
  FOREIGN KEY (owner_id) REFERENCES owner(id)
);