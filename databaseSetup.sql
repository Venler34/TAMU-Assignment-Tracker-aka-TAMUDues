CREATE TABLE TAMUDues.Users (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`username` varchar(255),
    `password` varchar(255)
);

CREATE TABLE TAMUDues.Assignments (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(255),
    description TEXT,
    `dueDate` timestamp, # includes timezone
    userId int, 
    
	CONSTRAINT fk_assignments_users
	FOREIGN KEY (userId) REFERENCES TAMUDues.Users (id)
);

CREATE TABLE TAMUDues.Sections (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(255),
    `color` varchar(7), # would store #FF5733
    userId int,
    
    CONSTRAINT fk_sections_users
    FOREIGN KEY (userId) REFERENCES TAMUDues.Users (id)
);

CREATE TABLE TAMUDues.SectionsAssignments (
	sectionId INT,
    assignmentId INT,
    
    PRIMARY KEY (sectionId, assignmentId),
    
    CONSTRAINT fk_section
    FOREIGN KEY (sectionId) REFERENCES TAMUDues.Sections (id),
    
    CONSTRAINT fk_assignment
    FOREIGN KEY (assignmentId) REFERENCES TAMUDues.Assignments (id)
);

