INSERT INTO Artista (nacionalidad, fecha_nacimiento, email) VALUES
                                                                ('Argentina', '1990-11-18', 'diegoalonso@maxmusic.com'),
                                                                ('Argentina', '1990-11-18', 'ezequielgallo@maxmusic.com'),
                                                                ('Argentina', '1990-11-18', 'eliassanti@maxmusic.com');

INSERT INTO Album (titulo, genero, fecha_estreno, artista_id) VALUES
                                                                  ('Countdown to Extinction', 'Rock', '2022-02-1', 1),
                                                                  ('The Marshall Mathers LP', 'Rap', '2000-02-1', 2),
                                                                  ('Ameri', 'Trap', '2022-02-1', 3);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ('Cancion 1', 188.00, 1),
                                                     ('Cancion 2', 128.00, 1),
                                                     ('Cancion 3', 184.00, 1),
                                                     ('Cancion 4', 134.00, 1);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ('Cancion 5', 188.00, 2),
                                                     ('Cancion 6', 128.00, 2),
                                                     ('Cancion 7', 184.00, 2),
                                                     ('Cancion 8', 134.00, 2);

INSERT INTO Cancion (nombre, duracion, album_id) VALUES
                                                     ('Cancion 9', 188.00, 3),
                                                     ('Cancion 10', 128.00, 3),
                                                     ('Cancion 11', 184.00, 3),
                                                     ('Cancion 12', 134.00, 3);

INSERT INTO usuario (username, contrasena, rol, nombre, apellido) VALUES ( 'admin', '{bcrypt}$2a$12$eyd1Muaz4ZxxZvC4nd556Ou1iaqfA/RReZagH4PpawE7Yazf9tQi6', 'ROL_ADMIN', 'José', 'Pérez');
INSERT INTO usuario (username, contrasena, rol, nombre, apellido) VALUES ( 'lectura1', '{bcrypt}$2a$12$eyd1Muaz4ZxxZvC4nd556Ou1iaqfA/RReZagH4PpawE7Yazf9tQi6', 'ROL_LECTURA', 'Martin', 'Gomez');
INSERT INTO usuario (username, contrasena, rol, nombre, apellido) VALUES ( 'lectura2', '{bcrypt}$2a$12$eyd1Muaz4ZxxZvC4nd556Ou1iaqfA/RReZagH4PpawE7Yazf9tQi6', 'ROL_LECTURA', 'Juan', 'Villalba');

INSERT INTO usuario (username, contrasena, rol, nombre, apellido, artista_id) VALUES ( 'artista_diegoalonso', '{bcrypt}$2a$12$eyd1Muaz4ZxxZvC4nd556Ou1iaqfA/RReZagH4PpawE7Yazf9tQi6', 'ROL_ARTISTA', 'Diego', 'aaa', 1);
INSERT INTO usuario (username, contrasena, rol, nombre, apellido, artista_id) VALUES ( 'artista_ezequielgallo', '{bcrypt}$2a$12$eyd1Muaz4ZxxZvC4nd556Ou1iaqfA/RReZagH4PpawE7Yazf9tQi6', 'ROL_ARTISTA', 'Eze', 'bbb', 2);
INSERT INTO usuario (username, contrasena, rol, nombre, apellido, artista_id) VALUES ( 'artista_eliassanti', '{bcrypt}$2a$12$eyd1Muaz4ZxxZvC4nd556Ou1iaqfA/RReZagH4PpawE7Yazf9tQi6', 'ROL_ARTISTA', 'Elias', 'ccc', 3);