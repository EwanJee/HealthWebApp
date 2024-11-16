CREATE TABLE member (
                        id VARCHAR(36) NOT NULL,
                        created_at DATETIME(6),
                        deleted_at DATETIME(6),
                        updated_at DATETIME(6),
                        age INTEGER NOT NULL,
                        data JSON,
                        name VARCHAR(255),
                        sex VARCHAR(255),
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_member_id ON member (id);
