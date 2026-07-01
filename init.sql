-- ============================================================
-- Script d'initialisation de la base gestion_transport
-- Exécuter avec : psql -U postgres -d gestion_transport -f init.sql
-- ============================================================

-- Supprimer les tables si elles existent déjà (pour pouvoir réexécuter le script)
DROP TABLE IF EXISTS voyage;
DROP TABLE IF EXISTS voiture;

-- ============================================================
-- Table : voiture
-- ============================================================
CREATE TABLE voiture (
    id              VARCHAR(10) PRIMARY KEY,
    type_voiture    VARCHAR(20)  NOT NULL,
    numero_matricule VARCHAR(15) NOT NULL,
    nbre_places     INT          NOT NULL
);

-- ============================================================
-- Table : voyage
-- ============================================================
CREATE TABLE voyage (
    id            VARCHAR(10) PRIMARY KEY,
    ville_depart  VARCHAR(30) NOT NULL,
    ville_arrivee VARCHAR(30) NOT NULL,
    date_depart   TIMESTAMP   NOT NULL,
    date_arrivee  TIMESTAMP   NOT NULL
);

-- ============================================================
-- Insertion des données (4 voitures)
-- ============================================================
INSERT INTO voiture (id, type_voiture, numero_matricule, nbre_places) VALUES
('V001', 'LITE',    '1234-TBA', 16),
('V002', 'PREMIUM', '5678-TBA', 16),
('V003', 'VIP',     '9012-TBA',  8),
('V004', 'VVIP',    '3456-TBA',  8);

-- ============================================================
-- Insertion des données (5 voyages)
-- ============================================================
INSERT INTO voyage (id, ville_depart, ville_arrivee, date_depart, date_arrivee) VALUES
('PL1', 'ANTANANARIVO', 'TOAMASINA',   '2025-06-01 08:00:00', '2025-06-01 16:00:00'),
('PL2', 'ANTANANARIVO', 'MAHAJANGA',   '2025-06-02 06:00:00', '2025-06-02 18:00:00'),
('PL3', 'ANTANANARIVO', 'FIANARANTSOA','2025-06-03 07:00:00', '2025-06-03 15:00:00'),
('PL4', 'TOAMASINA',    'ANTANANARIVO','2025-06-04 09:00:00', '2025-06-04 17:00:00'),
('PL5', 'MAHAJANGA',    'ANTANANARIVO','2025-06-05 05:00:00', '2025-06-05 17:00:00');
