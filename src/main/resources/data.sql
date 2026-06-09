INSERT INTO Artista (nacionalidad, fecha_nacimiento, email) VALUES ( 'Argentina', '1990-11-11' , 'damianr@maxmusic.com'),
                                                                    ('Argentina', '1992-12-12', 'carobf@maxmusic.com'),
                                                                    ('Argentina', '1990-01-01', 'nicon@maximusic.com');

INSERT INTO Album (titulo, genero, fecha_estreno, artista_id) VALUES ( 'Iron Maiden The Number of the Beast', 'Rock', '2026-01-01', 1),
                                                                     ('Korn', 'Nu Metal', '2026-02-02', 2),
                                                                     ('Back in Black', 'Rock', '2026-03-03', 3),
                                                                     ('Power Up', 'Rock', '2026-03-03', 3);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ( 'Cancion 1', 199.00, 1),
                                                     ( 'Cancion 2', 299.00, 1),
                                                     ( 'Cancion 3', 399.00, 1);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ( 'Cancion 4', 199.00, 2),
                                                     ( 'Cancion 5', 299.00, 2),
                                                     ( 'Cancion 6', 399.00, 2);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ( 'Cancion 7', 199.00, 3),
                                                     ( 'Cancion 8', 299.00, 3),
                                                     ( 'Cancion 9', 399.00, 3);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ( 'Cancion 10', 199.00, 4),
                                                     ( 'Cancion 11', 299.00, 4),
                                                     ( 'Cancion 12', 399.00, 4);

--contraseña: admin
INSERT INTO usuario (username, contrasena, rol, nombre, apellido) VALUES ('admin','{bcrypt}$2a$10$rJSJrFqKN2trDGzJ0AS60uBKXwJHw9b74WU5Xavfs7j/1CoWQs0fm', 'ROL_ADMIN', 'José', 'Pérez');
INSERT INTO usuario (username, contrasena, rol, nombre, apellido) VALUES ('lectura1','{bcrypt}$2a$10$rJSJrFqKN2trDGzJ0AS60uBKXwJHw9b74WU5Xavfs7j/1CoWQs0fm', 'ROL_LECTURA', 'Martin', 'Gomez');
INSERT INTO usuario (username, contrasena, rol, nombre, apellido) VALUES ('lectura2','{bcrypt}$2a$10$rJSJrFqKN2trDGzJ0AS60uBKXwJHw9b74WU5Xavfs7j/1CoWQs0fm', 'ROL_LECTURA', 'Juan', 'Villalba');

INSERT INTO usuario (username, contrasena, rol, nombre, apellido, artista_id) VALUES ('artista_damian','{bcrypt}$2a$10$rJSJrFqKN2trDGzJ0AS60uBKXwJHw9b74WU5Xavfs7j/1CoWQs0fm', 'ROL_ARTISTA', 'damian', 'r', 1);
INSERT INTO usuario (username, contrasena, rol, nombre, apellido, artista_id) VALUES ('artista_caro','{bcrypt}$2a$10$rJSJrFqKN2trDGzJ0AS60uBKXwJHw9b74WU5Xavfs7j/1CoWQs0fm', 'ROL_ARTISTA', 'carobf', 'bf', 2);
INSERT INTO usuario (username, contrasena, rol, nombre, apellido, artista_id) VALUES ('artista_nico','{bcrypt}$2a$10$rJSJrFqKN2trDGzJ0AS60uBKXwJHw9b74WU5Xavfs7j/1CoWQs0fm', 'ROL_ARTISTA', 'nico', 'n', 3);