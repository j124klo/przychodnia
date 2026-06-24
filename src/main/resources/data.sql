-- =======================================================================================
-- 1. TABELE NIEZALEŻNE (SŁOWNIKI I OSOBY)
-- =======================================================================================

-- ---------------------------------------------------------
-- CHOROBY (30 wpisów, klucz główny: ICD-10 String)
-- ---------------------------------------------------------
INSERT INTO CHOROBA (ICD10, NAZWA_CHOROBY) VALUES 
('I10', 'Samoistne (pierwotne) nadciśnienie'),
('E11', 'Cukrzyca insulinozależna'),
('J00', 'Ostre zapalenie nosa i gardła (przeziębienie)'),
('J01', 'Ostre zapalenie zatok'),
('J02', 'Ostre zapalenie gardła'),
('J03', 'Ostre zapalenie migdałków'),
('J04', 'Ostre zapalenie krtani i tchawicy'),
('J06', 'Ostre zakażenia górnych dróg oddechowych'),
('J20', 'Ostre zapalenie oskrzeli'),
('J21', 'Ostre zapalenie oskrzelików'),
('J18', 'Zapalenie płuc, nieokreślone'),
('K21', 'Refluks żołądkowo-przełykowy'),
('K29', 'Zapalenie żołądka i dwunastnicy'),
('E03', 'Inna niedoczynność tarczycy'),
('E05', 'Nadczynność tarczycy'),
('M54', 'Ból grzbietu'),
('G43', 'Migrena'),
('G44', 'Inne zespoły bólu głowy'),
('H10', 'Zapalenie spojówek'),
('H65', 'Nieropne zapalenie ucha środkowego'),
('L20', 'Atopowe zapalenie skóry'),
('L50', 'Pokrzywka'),
('N30', 'Zapalenie pęcherza moczowego'),
('N39', 'Inne zaburzenia układu moczowego'),
('M15', 'Wielostawowa choroba zwyrodnieniowa'),
('M10', 'Dna moczanowa'),
('D50', 'Niedokrwistość z niedoboru żelaza'),
('F32', 'Epizod depresyjny'),
('F41', 'Inne zaburzenia lękowe'),
('A09', 'Zakaźne zapalenie żołądka i jelit');

-- ---------------------------------------------------------
-- BADANIA (15 wpisów, klucz główny: ID Integer)
-- ---------------------------------------------------------
INSERT INTO BADANIE (ID, NAZWA_BADANIA) VALUES 
(1, 'Morfologia krwi (pełna)'),
(2, 'OB (Odczyn Biernackiego)'),
(3, 'CRP - białko C-reaktywne'),
(4, 'Badanie ogólne moczu'),
(5, 'Badanie ogólne kału'),
(6, 'EKG spoczynkowe'),
(7, 'EEG (Elektroencefalografia)'),
(8, 'RTG klatki piersiowej'),
(9, 'USG jamy brzusznej'),
(10, 'USG tarczycy'),
(11, 'Spirometria'),
(12, 'Holter EKG (24h)'),
(13, 'Gastroskopia'),
(14, 'Kolonoskopia'),
(15, 'Tomografia komputerowa (TK)'),
(16, 'Narkoza przy pomocy pięści');

-- ---------------------------------------------------------
-- LEKI (30 wpisów, klucz główny: ID Integer)
-- ---------------------------------------------------------
INSERT INTO LEK (ID, NAZWA_LEKU, DAWKA, SUBSTANCJA_AKTYWNA) VALUES 
(1, 'Cannabis sativa flos', '22 %', 'THC'),
(2, 'Ibuprom', '400 mg', 'Ibuprofen'),
(3, 'Ketonal Active', '50 mg', 'Ketoprofen'),
(4, 'Nurofen', '200 mg', 'Ibuprofen'),
(5, 'No-Spa', '40 mg', 'Drotaweryna'),
(6, 'Euthyrox N', '50 ug', 'Lewotyroksyna'),
(7, 'Letrox', '100 ug', 'Lewotyroksyna'),
(8, 'Metformina', '500 mg', 'Metformina'),
(9, 'Glucophage XR', '1000 mg', 'Metformina'),
(10, 'Polocard', '75 mg', 'Kwas acetylosalicylowy'),
(11, 'Acard', '150 mg', 'Kwas acetylosalicylowy'),
(12, 'Amoksiklav', '875 mg + 125 mg', 'Amoksycylina + kwas klawulanowy'),
(13, 'Augmentin', '1 g', 'Amoksycylina + kwas klawulanowy'),
(14, 'Duomox', '1 g', 'Amoksycylina'),
(15, 'Zinnat', '500 mg', 'Cefuroksym'),
(16, 'Sumamed', '500 mg', 'Azytromycyna'),
(17, 'Controloc', '20 mg', 'Pantoprazol'),
(18, 'Nolpaza', '40 mg', 'Pantoprazol'),
(19, 'Dexilant', '60 mg', 'Dekslanzoprazol'),
(20, 'ACC Optima', '600 mg', 'Acetylocysteina'),
(21, 'Flegamina', '8 mg', 'Bromheksyna'),
(22, 'Neosine', '500 mg', 'Inozyny pranobeks'),
(23, 'Groprinosin', '500 mg', 'Inozyny pranobeks'),
(24, 'Rutinoscorbin', '25 mg + 100 mg', 'Rutozyd + Kwas askorbowy'),
(25, 'Witamina C', '1000 mg', 'Kwas askorbowy'),
(26, 'Vigantol', '500 j.m./kropla', 'Cholekalcyferol (Wit. D3)'),
(27, 'Magne B6', '48 mg + 5 mg', 'Magnez + Witamina B6'),
(28, 'Kaldyum', '600 mg', 'Chlorek potasu'),
(29, 'Xarelto', '20 mg', 'Rywaroksaban'),
(30, 'Bisocard', '5 mg', 'Bisoprolol');

-- ---------------------------------------------------------
-- PERSONEL (10 wpisów, klucz główny: PWZ_ID String)
-- ---------------------------------------------------------
INSERT INTO PERSONEL (PWZ_ID, IMIE, NAZWISKO, ZAWOD) VALUES 
('1111111', 'Tomasz', 'Lekarski', 'Lekarz - Internista'),
('2222222', 'Anna', 'Kardiologiczna', 'Lekarz - Kardiolog'),
('3333333', 'Piotr', 'Kostka', 'Lekarz - Ortopeda'),
('4444444', 'Zofia', 'Skórna', 'Lekarz - Dermatolog'),
('5555555', 'Michał', 'Ząbek', 'Lekarz - Stomatolog'),
('6666666', 'Maria', 'Pielęgniarska', 'Pielęgniarka'),
('7777777', 'Katarzyna', 'Szprycha', 'Pielęgniarka zabiegowa'),
('8888888', 'Jan', 'Ratuje', 'Ratownik medyczny'),
('9999999', 'Ewa', 'Krewka', 'Technik analityki med.'),
('1010101', 'Krystyna', 'Recepcja', 'Rejestratorka medyczna');

-- ---------------------------------------------------------
-- PACJENCI (20 wpisów, klucz główny: PESEL String)
-- ---------------------------------------------------------
INSERT INTO PACJENT (PESEL, IMIE, NAZWISKO, PLEC, DATA_UR, WZROST, MASA, GRUPA_KRWI) VALUES 
('70010112345', 'Jan', 'Kowalski', 'M', '1970-01-01', 180, 80.5, 'A+'),
('75020223456', 'Adam', 'Nowak', 'M', '1975-02-02', 175, 75.0, 'B-'),
('80030334567', 'Krzysztof', 'Wiśniewski', 'M', '1980-03-03', 185, 90.0, 'O+'),
('85040445678', 'Piotr', 'Wójcik', 'M', '1985-04-04', 170, 70.0, 'AB+'),
('90050556789', 'Michał', 'Kowalczyk', 'M', '1990-05-05', 182, 85.0, 'A-'),
('95060667890', 'Tomasz', 'Kamiński', 'M', '1995-06-06', 178, 77.0, 'B+'),
('00270778901', 'Andrzej', 'Lewandowski', 'M', '2000-07-07', 190, 95.0, 'O-'),
('05280889012', 'Marcin', 'Zieliński', 'M', '2005-08-08', 165, 60.0, 'AB-'),
('10290990123', 'Jakub', 'Szymański', 'M', '2010-09-09', 150, 45.0, 'A+'),
('65011001234', 'Marek', 'Woźniak', 'M', '1965-01-10', 176, 82.0, 'B-'),
('71021112345', 'Anna', 'Dąbrowska', 'K', '1971-02-11', 165, 60.0, 'A+'),
('76031223456', 'Maria', 'Kozłowska', 'K', '1976-03-12', 160, 55.0, 'O+'),
('81041334567', 'Katarzyna', 'Mazur', 'K', '1981-04-13', 168, 65.0, 'B+'),
('86051445678', 'Małgorzata', 'Krawczyk', 'K', '1986-05-14', 170, 68.0, 'AB+'),
('91061556789', 'Agnieszka', 'Kaczmarek', 'K', '1991-06-15', 164, 58.0, 'A-'),
('96071667890', 'Barbara', 'Piotrowska', 'K', '1996-07-16', 172, 70.0, 'O-'),
('01281778901', 'Ewa', 'Grabowska', 'K', '2001-08-17', 167, 62.0, 'B-'),
('06291889012', 'Krystyna', 'Pawłowska', 'K', '2006-09-18', 155, 50.0, 'AB-'),
('11301990123', 'Magdalena', 'Michalska', 'K', '2011-10-19', 140, 35.0, 'A+'),
('66012001234', 'Zofia', 'Nowakowska', 'K', '1966-01-20', 160, 65.0, 'O+');


-- =======================================================================================
-- 2. TABELE ZALEŻNE (WIZYTY)
-- =======================================================================================

-- ---------------------------------------------------------
-- WIZYTY (60 wpisów, po 3 dla każdego pacjenta)
-- Klucz obcy: PACJENT_PESEL
-- ---------------------------------------------------------
-- Poniżej generujemy zapytania łączące 20 pacjentów z 3 wizytami każdy
INSERT INTO WIZYTA (ID, ADRES_PRZYCHODNI, DATA, DIAGNOZA, RECEPTA, SKIEROWANIE, PACJENT_PESEL) VALUES 
-- Pacjent 1
(1, 'Główna 1, Mielec', '2023-01-10', 'Ogólne osłabienie', 'Witaminy', 'Brak', '70010112345'),
(2, 'Główna 1, Mielec', '2023-06-15', 'Kontrola ciśnienia', 'Recepta nr 111', 'Brak', '70010112345'),
(3, 'Główna 1, Mielec', '2023-11-20', 'Ból gardła', 'Recepta nr 112', 'Wymaz', '70010112345'),
-- Pacjent 2
(4, 'Główna 1, Mielec', '2023-02-11', 'Kaszel', 'Syrop', 'Brak', '75020223456'),
(5, 'Główna 1, Mielec', '2023-07-16', 'Bóle stawów', 'Maść', 'RTG stawów', '75020223456'),
(6, 'Główna 1, Mielec', '2023-12-21', 'Katar', 'Krople', 'Brak', '75020223456'),
-- Pacjent 3
(7, 'Główna 1, Mielec', '2023-03-12', 'Problemy żołądkowe', 'Dieta', 'Brak', '80030334567'),
(8, 'Główna 1, Mielec', '2023-08-17', 'Kontrola po chorobie', 'Brak', 'Brak', '80030334567'),
(9, 'Główna 1, Mielec', '2024-01-22', 'Wysoka gorączka', 'Recepta nr 113', 'Brak', '80030334567'),
-- Pacjent 4
(10, 'Główna 1, Mielec', '2023-04-13', 'Wysypka', 'Maść sterydowa', 'Dermatolog', '85040445678'),
(11, 'Główna 1, Mielec', '2023-09-18', 'Ból głowy', 'Recepta nr 114', 'Neurolog', '85040445678'),
(12, 'Główna 1, Mielec', '2024-02-23', 'Kontrola wyników', 'Brak', 'Brak', '85040445678'),
-- Pacjent 5
(13, 'Główna 1, Mielec', '2023-05-14', 'Ból ucha', 'Krople do uszu', 'Brak', '90050556789'),
(14, 'Główna 1, Mielec', '2023-10-19', 'Zatkane ucho', 'Brak', 'Laryngolog', '90050556789'),
(15, 'Główna 1, Mielec', '2024-03-24', 'Zapalenie ucha', 'Recepta nr 115', 'Brak', '90050556789'),
-- Pacjent 6
(16, 'Główna 1, Mielec', '2023-01-05', 'Ból brzucha', 'Leki rozkurczowe', 'USG', '95060667890'),
(17, 'Główna 1, Mielec', '2023-05-10', 'Badania okresowe', 'Brak', 'Morfologia', '95060667890'),
(18, 'Główna 1, Mielec', '2023-10-15', 'Przeziębienie', 'Recepta nr 116', 'Brak', '95060667890'),
-- Pacjent 7
(19, 'Główna 1, Mielec', '2023-02-06', 'Duszności', 'Inhalator', 'Spirometria', '00270778901'),
(20, 'Główna 1, Mielec', '2023-06-11', 'Kontrola astmy', 'Recepta nr 117', 'Brak', '00270778901'),
(21, 'Główna 1, Mielec', '2023-11-16', 'Kaszel alergiczny', 'Leki przeciwhistaminowe', 'Brak', '00270778901'),
-- Pacjent 8
(22, 'Główna 1, Mielec', '2023-03-07', 'Uraz ręki', 'Opaska uciskowa', 'RTG', '05280889012'),
(23, 'Główna 1, Mielec', '2023-08-12', 'Kontrola po urazie', 'Brak', 'Rehabilitacja', '05280889012'),
(24, 'Główna 1, Mielec', '2024-01-17', 'Zakończenie leczenia', 'Brak', 'Brak', '05280889012'),
-- Pacjent 9
(25, 'Główna 1, Mielec', '2023-04-08', 'Angina', 'Recepta nr 118', 'Brak', '10290990123'),
(26, 'Główna 1, Mielec', '2023-09-13', 'Kontrola po anginie', 'Brak', 'Brak', '10290990123'),
(27, 'Główna 1, Mielec', '2024-02-18', 'Szczepienie', 'Brak', 'Brak', '10290990123'),
-- Pacjent 10
(28, 'Główna 1, Mielec', '2023-05-09', 'Zawroty głowy', 'Leki poprawiające krążenie', 'EKG', '65011001234'),
(29, 'Główna 1, Mielec', '2023-10-14', 'Kontrola kardiologiczna', 'Recepta nr 119', 'Holter', '65011001234'),
(30, 'Główna 1, Mielec', '2024-03-19', 'Podwyższony cholesterol', 'Dieta', 'Lipidogram', '65011001234'),
-- Pacjent 11
(31, 'Główna 1, Mielec', '2023-01-20', 'Zgaga', 'Recepta nr 120', 'Gastroskopia', '71021112345'),
(32, 'Główna 1, Mielec', '2023-06-25', 'Omówienie wyników', 'Dieta', 'Brak', '71021112345'),
(33, 'Główna 1, Mielec', '2023-11-30', 'Odnowienie recepty', 'Recepta nr 121', 'Brak', '71021112345'),
-- Pacjent 12
(34, 'Główna 1, Mielec', '2023-02-21', 'Ból zęba', 'Brak', 'Stomatolog', '76031223456'),
(35, 'Główna 1, Mielec', '2023-07-26', 'Kontrola jamy ustnej', 'Brak', 'Brak', '76031223456'),
(36, 'Główna 1, Mielec', '2023-12-31', 'Stan zapalny dziąseł', 'Recepta nr 122', 'Brak', '76031223456'),
-- Pacjent 13
(37, 'Główna 1, Mielec', '2023-03-22', 'Ból kolana', 'Żel przeciwbólowy', 'USG kolana', '81041334567'),
(38, 'Główna 1, Mielec', '2023-08-27', 'Skierowanie na zabiegi', 'Brak', 'Fizjoterapia', '81041334567'),
(39, 'Główna 1, Mielec', '2024-01-05', 'Kontrola ortopedyczna', 'Brak', 'Brak', '81041334567'),
-- Pacjent 14
(40, 'Główna 1, Mielec', '2023-04-23', 'Zmęczenie, senność', 'Brak', 'Badania tarczycy', '86051445678'),
(41, 'Główna 1, Mielec', '2023-09-28', 'Niedoczynność zdiagnozowana', 'Recepta nr 123', 'Brak', '86051445678'),
(42, 'Główna 1, Mielec', '2024-02-08', 'Dobór dawki leku', 'Recepta nr 124', 'Brak', '86051445678'),
-- Pacjent 15
(43, 'Główna 1, Mielec', '2023-05-24', 'Infekcja pęcherza', 'Recepta nr 125', 'Badanie moczu', '91061556789'),
(44, 'Główna 1, Mielec', '2023-10-29', 'Nawrót infekcji', 'Recepta nr 126', 'Urolog', '91061556789'),
(45, 'Główna 1, Mielec', '2024-03-10', 'Kontrola urologiczna', 'Brak', 'Brak', '91061556789'),
-- Pacjent 16
(46, 'Główna 1, Mielec', '2023-01-15', 'Wysokie ciśnienie', 'Recepta nr 127', 'EKG', '96071667890'),
(47, 'Główna 1, Mielec', '2023-06-20', 'Zła tolerancja leków', 'Recepta nr 128', 'Brak', '96071667890'),
(48, 'Główna 1, Mielec', '2023-11-25', 'Ustabilizowane ciśnienie', 'Recepta nr 129', 'Brak', '96071667890'),
-- Pacjent 17
(49, 'Główna 1, Mielec', '2023-02-16', 'Skręcenie kostki', 'Opaska', 'RTG stopy', '01281778901'),
(50, 'Główna 1, Mielec', '2023-07-21', 'Zakończenie gipsu', 'Brak', 'Rehabilitacja', '01281778901'),
(51, 'Główna 1, Mielec', '2023-12-26', 'Bóle przeciążeniowe', 'Recepta nr 130', 'Brak', '01281778901'),
-- Pacjent 18
(52, 'Główna 1, Mielec', '2023-03-17', 'Ból zatok', 'Recepta nr 131', 'RTG zatok', '06291889012'),
(53, 'Główna 1, Mielec', '2023-08-22', 'Zapalenie nawracające', 'Recepta nr 132', 'Laryngolog', '06291889012'),
(54, 'Główna 1, Mielec', '2024-01-27', 'Skierowanie na zabieg', 'Brak', 'Szpital', '06291889012'),
-- Pacjent 19
(55, 'Główna 1, Mielec', '2023-04-18', 'Bilans zdrowia', 'Brak', 'Morfologia', '11301990123'),
(56, 'Główna 1, Mielec', '2023-09-23', 'Omówienie bilansu', 'Witaminy', 'Brak', '11301990123'),
(57, 'Główna 1, Mielec', '2024-02-28', 'Szczepienie przypominające', 'Brak', 'Brak', '11301990123'),
-- Pacjent 20
(58, 'Główna 1, Mielec', '2023-05-19', 'Rwa kulszowa', 'Zastrzyki', 'Neurolog', '66012001234'),
(59, 'Główna 1, Mielec', '2023-10-24', 'Leczenie zachowawcze', 'Recepta nr 133', 'Rehabilitacja', '66012001234'),
(60, 'Główna 1, Mielec', '2024-03-29', 'Poprawa stanu', 'Brak', 'Brak', '66012001234');


-- =======================================================================================
-- 3. RELACJE MANY-TO-MANY (Tabela łącząca)
-- Nazwy tabel i kolumn wygenerowane wg standardów Spring Boot / Hibernate
-- =======================================================================================

-- Powiązanie wizyt z personelem (WIZYTA_ID -> PERSONEL_PWZ_ID)
-- Wizyty 1-10 - Lekarz 1111111, Wizyty 11-20 - Lekarz 2222222 itd...
INSERT INTO WIZYTA_PERSONEL (WIZYTY_ID, PERSONEL_PWZ_ID) VALUES
(1, '1111111'), (2, '1111111'), (3, '1111111'), (4, '1111111'), (5, '1111111'),
(10, '2222222'), (11, '2222222'), (12, '2222222'), (13, '2222222'), (14, '2222222'),
(20, '3333333'), (21, '3333333'), (22, '3333333'), (23, '3333333'), (24, '3333333'),
(30, '4444444'), (31, '4444444'), (32, '4444444'), (33, '4444444'), (34, '5555555'),
(40, '1111111'), (45, '6666666'), (50, '3333333'), (55, '1111111'), (60, '2222222');

-- Powiązanie wizyt z chorobami (WIZYTA_ID -> CHOROBY_ICD10)
INSERT INTO WIZYTA_CHOROBY (WIZYTY_ID, CHOROBY_ICD10) VALUES
(1, 'J00'), (3, 'J02'), (4, 'J00'), (5, 'M15'), (6, 'J00'), 
(9, 'J18'), (10, 'L20'), (13, 'H65'), (15, 'H65'), (19, 'J20'), 
(25, 'J03'), (28, 'I10'), (30, 'E11'), (31, 'K21'), (37, 'M54'), 
(41, 'E03'), (43, 'N30'), (46, 'I10'), (52, 'J01'), (58, 'M54');

-- Powiązanie wizyt z badaniami (WIZYTA_ID -> BADANIA_ID)
INSERT INTO WIZYTA_BADANIA (WIZYTY_ID, BADANIA_ID) VALUES
(2, 1), (2, 2), (5, 8), (9, 3), (12, 1), 
(16, 9), (17, 1), (17, 4), (19, 11), (22, 8), 
(28, 6), (29, 12), (30, 1), (31, 13), (37, 9), 
(40, 1), (43, 4), (46, 6), (49, 8), (52, 8), (55, 1);

-- Powiązanie wizyt z lekami (WIZYTA_ID -> LEKI_ID)
INSERT INTO WIZYTA_LEKI (WIZYTY_ID, LEKI_ID) VALUES
(1, 25), (2, 30), (3, 14), (4, 21), (5, 3), 
(9, 13), (10, 2), (11, 1), (13, 2), (15, 15), 
(16, 5), (18, 1), (20, 20), (21, 22), (25, 16), 
(29, 10), (30, 8), (31, 17), (36, 12), (41, 6), 
(42, 6), (43, 15), (46, 30), (48, 30), (51, 3), 
(52, 20), (53, 12), (59, 3);


-- =======================================================================================
-- 4. RESETOWANIE SEKWENCJI H2 (Niezbędne przy GenerationType.IDENTITY)
-- =======================================================================================
-- Ponieważ wymusiliśmy ręcznie ID w insertach wyżej (żeby łatwo zrobić relacje Many-To-Many), 
-- musimy poinformować bazę danych, żeby podczas zapisywania z POSTMANA nie zaczynała liczyć od 1.
ALTER TABLE BADANIE ALTER COLUMN ID RESTART WITH 16;
ALTER TABLE LEK ALTER COLUMN ID RESTART WITH 31;
ALTER TABLE WIZYTA ALTER COLUMN ID RESTART WITH 61;