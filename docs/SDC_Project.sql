CREATE TABLE research_areas
( research_area VARCHAR(255) PRIMARY KEY NOT NULL,
  parent_areas VARCHAR(255) NULL );
   
CREATE TABLE publication_venue
( venue_name VARCHAR(255) PRIMARY KEY NOT NULL,
  publisher_organization VARCHAR(255) NOT NULL REFERENCES 
  publisher_organization(publisher_organization_name) ON UPDATE CASCADE,
  organizer_editor VARCHAR(255) NOT NULL,
  editor_contact VARCHAR(255) NULL,
  conference_year VARCHAR(255) NULL,
  conference_location VARCHAR(255) NULL
  );
  
  CREATE TABLE venue_research_areas
  ( venue_name VARCHAR(255) NOT NULL REFERENCES publication_venue(venue_name) ON UPDATE CASCADE,
    research_area VARCHAR(255) NOT NULL REFERENCES reserch_areas(research_area) ON UPDATE CASCADE
  );
  
  CREATE TABLE publications
  (
     identifier VARCHAR(255) PRIMARY KEY NOT NULL,
     title VARCHAR(255) NOT NULL,
     journal VARCHAR(255) NULL REFERENCES publication_venue(venue_name) ON UPDATE CASCADE,
     conference VARCHAR(255) NULL REFERENCES publication_venue(venue_name) ON UPDATE CASCADE,
     pages VARCHAR(255) NOT NULL,
     volume VARCHAR(255) NULL,
     issue_number VARCHAR(255) NULL,
     publish_month VARCHAR(255) NULL,
     publish_year VARCHAR(255) NOT NULL,
     location VARCHAR(255) NULL
  );
  
  CREATE TABLE authors
  (  
    identifier VARCHAR(255) NOT NULL REFERENCES publications(identifier) ON UPDATE CASCADE,
    author_name VARCHAR(255) NOT NULL
  );
  
CREATE TABLE publication_references
(
  identifier VARCHAR(255) NOT NULL REFERENCES publications(identifier) ON UPDATE CASCADE,
  publication_reference VARCHAR(255) NOT NULL
);

CREATE TABLE publisher_organization
(
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  publisher_organization_name VARCHAR(255) UNIQUE KEY NOT NULL,
  email VARCHAR(255) NULL,
  location VARCHAR(255) NOT NULL
);
  
  
  