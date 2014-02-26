DROP TABLE chat_incident;
CREATE TABLE chat_incident(
id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
model VARCHAR(100),
serie VARCHAR(50),
problem VARCHAR(500),
reported_by VARCHAR(20),
assigned_to VARCHAR(20),
creation_date TIMESTAMP
);

DROP TABLE chat_incident;

SELECT * FROM chatincident;

DROP TABLE chat_incident_detail;

CREATE TABLE chat_incident_detail(
id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
chat_incident_id BIGINT(20),
message VARCHAR(500)
);

ALTER TABLE chat_incident_detail ADD FOREIGN KEY (chat_incident_id) REFERENCES chat_incident(id);
