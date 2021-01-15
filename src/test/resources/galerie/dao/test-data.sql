/**
 * Author:  Victor Maintenant
 * Created: 14 janv. 2021
 */
INSERT INTO Galerie(id, nom, adresse) VALUES (1, 'Saatchi', 'Kings Road, Londres');
INSERT INTO Galerie(id, nom, adresse) VALUES (2, 'GalerY', 'Rue du Roi, Paris');
INSERT INTO Exposition(id, debut, intitule, duree, galerie_id)VALUES (1, TO_DATE('01/01/2020', 'DD/MM/YYYY'), 'Les Impressionnistes Fr', 30, 1);
INSERT INTO Exposition(id, debut, intitule, duree, galerie_id)VALUES (2, TO_DATE('01/05/2020', 'DD/MM/YYYY'), 'Le cubisme', 20, 1);
INSERT INTO Exposition(id, debut, intitule, duree, galerie_id)VALUES (3, TO_DATE('01/01/2020', 'DD/MM/YYYY'), 'Art Brut', 15, 2);
INSERT INTO Personne(id, nom, adresse) VALUES (1, 'Bastide', 'Castres');
INSERT INTO Personne(id, nom, adresse) VALUES (2, 'Maintenant', 'Castres');
INSERT INTO Personne(id, nom, adresse) VALUES (3, 'Artiste1', 'Paris');
INSERT INTO Personne(id, nom, adresse) VALUES (4, 'Artiste2', 'Londres');
INSERT INTO Artiste(id, biographie)VALUES (3, 'Très sympa');
INSERT INTO Artiste(id, biographie)VALUES (4, 'Très bon');
INSERT INTO Tableau(id, titre, support, artiste_id)VALUES (1, 'Impressionnant', 'huile sur toile', 3);
INSERT INTO Tableau(id, titre, support, artiste_id)VALUES (2, 'Cube', 'huile sur toile', 4);
INSERT INTO Tableau(id, titre, support, artiste_id)VALUES (3, 'Brut', 'huile sur toile', 4);
INSERT INTO Expo_tableau VALUES (1, 1);
INSERT INTO Expo_tableau VALUES (2, 2);
INSERT INTO Expo_tableau VALUES (3, 3);
INSERT INTO Transaction(id, vendu_le, prix_vente, exposition_id, personne_id, tableau_id)VALUES (1, TO_DATE('02/01/2020', 'DD/MM/YYYY'), 20000, 1, 1, 1);
INSERT INTO Transaction(id, vendu_le, prix_vente, exposition_id, personne_id, tableau_id)VALUES (2, TO_DATE('10/05/2020', 'DD/MM/YYYY'), 20000, 2, 2, 2);
INSERT INTO Transaction(id, vendu_le, prix_vente, exposition_id, personne_id, tableau_id)VALUES (3, TO_DATE('06/01/2020', 'DD/MM/YYYY'), 20000, 3, 1, 3);
Update Tableau set transaction_id = 1 where id = 1; 
Update Tableau set transaction_id = 2 where id = 2; 
Update Tableau set transaction_id = 3 where id = 3; 