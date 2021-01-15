package galerie.dao;

import galerie.entity.Galerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import galerie.entity.Exposition;
import galerie.dao.ExpositionRepository;
import galerie.entity.Transaction;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class ExpositionRepositoryTest {

    @Autowired
    private ExpositionRepository expoDAO;

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    public void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Exposition'");
        int combienDansLeJeuDeTest = 3;
        long nombre = expoDAO.count();
        assertEquals(combienDansLeJeuDeTest, nombre, "On doit trouver 3 expositions");
    }

    @Test
    @Sql("test-data.sql")
    public void connaitreLeCASQL() {
        log.info("On calcule le CA d'une galerie donnée selon une année donnée.");
        assertEquals(20000, expoDAO.CASQL(1), "On doit avoir 20000€ de recette");
    }

    @Test
    public void connaitreLeCA() {
        Galerie g = new Galerie(1, "Galerie1", "Paris");
        Exposition e1 = new Exposition(1, "Impressionistes FR", 30, g);
        Transaction t1 = new Transaction(1, Date.valueOf(LocalDate.now()), 20000, e1);
        Transaction t2 = new Transaction(2, Date.valueOf(LocalDate.now()), 20000, e1);
        assertTrue(e1.getTransactions().isEmpty());
        e1.ajoutTransac(t1);
        e1.ajoutTransac(t2);
        assertFalse(e1.getTransactions().isEmpty());
        assertEquals(40000, e1.CA());

    }

    @Test
    @Sql("test-data.sql")
    void listerLesEntites() {
        log.info("Lister les entités");
        List<Exposition> liste = expoDAO.findAll(); // Renvoie la liste des entités dans la table
        log.info("Liste des entités: {}", liste);
    }

    @Test
    @Sql("test-data.sql")
    void touverParCle() {
        log.info("Trouver une entité par sa clé");
        int codePresent = 1;
        Optional<Exposition> resultat = expoDAO.findById(codePresent);
        // On s'assure qu'on trouvé le résultat
        assertTrue(resultat.isPresent(), "Cette galerie existe");
        Exposition e = resultat.get();
        assertEquals("Les Impressionnistes Fr", e.getIntitule());
        log.info("Entité trouvée: {}", e);
    }

    @Test
    @Sql("test-data.sql")
    void entiteInconnue() {
        log.info("Chercher une entité inconnue");
        int codeInconnu = 99;
        Optional<Exposition> resultat = expoDAO.findById(codeInconnu);
        assertFalse(resultat.isPresent(), "Cette catégorie n'existe pas");

    }

    @Test
    @Sql("test-data.sql")
    void creerUneEntite() {
        log.info("Créer une entité");
        Exposition nouvelle = new Exposition();
        Galerie g = new Galerie();
        nouvelle.setIntitule("Test");
        nouvelle.setDebut(Date.valueOf(LocalDate.now()));
        nouvelle.setGalerie(g);
        assertNull(nouvelle.getId(), "L'entité n'a pas encore de clé");
        expoDAO.save(nouvelle); // 'save' enregistre l'entite dans la base
        Integer nouvellecle = nouvelle.getId(); // La clé a été auto-générée lors de l'enregistrement
        assertNotNull(nouvellecle, "Une nouvelle clé doit avoir été générée");
        log.info("Nouvelle entité: {}", nouvelle);
    }

    @Test
    @Sql("test-data.sql")
    void onNePeutPasDetruireUneGalerieQuiADesExpo() {
        log.info("Détruire une catégorie avec des produits");
        Exposition e = expoDAO.getOne(1);
        assertEquals("Les Impressionnistes Fr", e.getIntitule());
        // Il y a des produits dans la catégorie 'Transaction'
        assertTrue(expoDAO.aDesTransactions(e.getId()));
        assertTrue(expoDAO.aDesTableaux(e.getId()));
        // Si on essaie de détruire cette catégorie, on doit avoir une exception
        // de violation de contrainte d'intégrité
        assertThrows(DataIntegrityViolationException.class, () -> {
            expoDAO.delete(e);
            expoDAO.flush(); // Pour forcer la validation de la transaction
        });
    }
}
