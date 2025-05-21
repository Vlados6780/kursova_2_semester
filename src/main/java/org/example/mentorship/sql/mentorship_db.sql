CREATE TABLE users (
        id_user SERIAL PRIMARY KEY,
        username VARCHAR(50) NOT NULL,
        age INTEGER NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        role VARCHAR(20) NOT NULL CHECK (role IN ('student', 'mentor')),
        profile_description TEXT,
        date_registration TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
        id_student SERIAL PRIMARY KEY,
        id_user INTEGER NOT NULL UNIQUE,
        university VARCHAR(100) NOT NULL,
        study_year INTEGER NOT NULL,
        education_level VARCHAR(50) NOT NULL,
        FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE
);

CREATE TABLE mentors (
        id_mentor SERIAL PRIMARY KEY,
        id_user INTEGER NOT NULL UNIQUE,
        specialization VARCHAR(100) NOT NULL,
        experience_years INTEGER NOT NULL,
        FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE
);

CREATE TABLE comments (
        id_comment SERIAL PRIMARY KEY,
        content TEXT NOT NULL,
        posted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        author_id INTEGER NOT NULL,
        target_id INTEGER NOT NULL,
        FOREIGN KEY (author_id) REFERENCES users(id_user) ON DELETE CASCADE,
        FOREIGN KEY (target_id) REFERENCES users(id_user) ON DELETE CASCADE
);

CREATE TABLE consultationRequests (
        id_consultation_request SERIAL PRIMARY KEY,
        request_message TEXT NOT NULL,
        requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        status_request VARCHAR(20) NOT NULL CHECK (status_request IN ('pending', 'confirmed', 'rejected')),
        id_student INTEGER NOT NULL,
        id_mentor INTEGER NOT NULL,
        FOREIGN KEY (id_student) REFERENCES users(id_user) ON DELETE CASCADE,
        FOREIGN KEY (id_mentor) REFERENCES users(id_user) ON DELETE CASCADE
);

CREATE TABLE mentorResponses (
        id_mentor_response SERIAL PRIMARY KEY,
        id_consultation_request INTEGER NOT NULL UNIQUE,
        response_message TEXT NOT NULL,
        sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (id_consultation_request) REFERENCES consultationRequests(id_consultation_request) ON DELETE CASCADE
);