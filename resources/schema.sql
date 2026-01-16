CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(200) NOT NULL,
                       author VARCHAR(200) NOT NULL,
                       price NUMERIC(8,2) NOT NULL CHECK (price > 0),
                       category_id INT NOT NULL,
                       book_type VARCHAR(20) NOT NULL,

                       CONSTRAINT fk_category
                           FOREIGN KEY (category_id)
                               REFERENCES category(id)
);

CREATE TABLE ebooks (
                        book_id INT PRIMARY KEY,
                        file_size_mb NUMERIC(6,2) NOT NULL CHECK (file_size_mb > 0),

                        CONSTRAINT fk_ebook_book
                            FOREIGN KEY (book_id)
                                REFERENCES books(id)
                                ON DELETE CASCADE
);

CREATE TABLE printed_books (
                               book_id INT PRIMARY KEY,
                               pages INT NOT NULL CHECK (pages > 0),

                               CONSTRAINT fk_printed_book
                                   FOREIGN KEY (book_id)
                                       REFERENCES books(id)
                                       ON DELETE CASCADE
);
