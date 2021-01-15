package galerie.dao;

import galerie.entity.Galerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import galerie.dao.GalerieRepository;
import galerie.entity.Exposition;
import galerie.entity.Transaction;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class GalerieRepositoryTest {

    @Autowired
    private GalerieRepository galerieDAO;

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    public void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Galerie'");
        int combienDansLeJeuDeTest = 2;
        long nombre = galerieDAO.count();
        assertEquals(combienDansLeJeuDeTest, nombre, "On doit trouver 2 galerie");
    }

    @Test
    @Sql("test-data.sql")
    public void connaitreLeCAAnnuelSQL() {
        log.info("On calcule le CA d'une galerie donnée selon une année donnée.");
        assertEquals(40000, galerieDAO.CAanneeSQL(2020, 1), "On doit avoir 40000€ de recette");
    }

    @Test
    public void connaitreLeCAAnnuel() {
        Galerie g = new Galerie(1, "Galerie1", "Paris");
        Exposition e1 = new Exposition(1, "Impressionistes FR", 30, g);
        Exposition e2 = new Exposition(2, "Art Brut", 30, g);
        Transaction t1 = new Transaction(1, Date.valueOf(LocalDate.now()), 20000, e1);
        Transaction t2 = new Transaction(2, Date.valueOf(LocalDate.now()), 20000, e2);
        assertTrue(e1.getTransactions().isEmpty());
        assertTrue(e2.getTransactions().isEmpty());
        assertTrue(g.getExpositions().isEmpty());
        g.ajoutExpo(e1);
        g.ajoutExpo(e2);
        assertFalse(g.getExpositions().isEmpty());
        e1.ajoutTransac(t1);
        e2.ajoutTransac(t2);
        assertFalse(e1.getTransactions().isEmpty());
        assertFalse(e2.getTransactions().isEmpty());
        assertEquals(40000, g.CAannuel(Date.valueOf(LocalDate.now()).getYear()));

    }

    @Test
    @Sql("test-data.sql")
    void listerLesEntites() {
        log.info("Lister les entités");
        List<Galerie> liste = galerieDAO.findAll(); // Renvoie la liste des entités dans la table
        log.info("Liste des entités: {}", liste);
    }

    @Test
    @Sql("test-data.sql")
    void touverParCle() {
        log.info("Trouver une entité par sa clé");

        int codePresent = 1;
        Optional<Galerie> resultat = galerieDAO.findById(codePresent);
        // On s'assure qu'on trouvé le résultat
        assertTrue(resultat.isPresent(), "Cette galerie existe");
        Galerie g = resultat.get();
        assertEquals("Saatchi", g.getNom());
        log.info("Entité trouvée: {}", g);
    }

    @Test
    @Sql("test-data.sql")
    void entiteInconnue() {
        log.info("Chercher une entité inconnue");
        int codeInconnu = 99;
        Optional<Galerie> resultat = galerieDAO.findById(codeInconnu);
        assertFalse(resultat.isPresent(), "Cette catégorie n'existe pas");

    }

    @Test
    @Sql("test-data.sql")
    void creerUneEntite() {
        log.info("Créer une entité");
        Galerie nouvelle = new Galerie();
        nouvelle.setNom("Gal");
        nouvelle.setAdresse("Paris, France");
        assertNull(nouvelle.getId(), "L'entité n'a pas encore de clé");
        galerieDAO.save(nouvelle); // 'save' enregistre l'entite dans la base
        Integer nouvellecle = nouvelle.getId(); // La clé a été auto-générée lors de l'enregistrement
        assertNotNull(nouvellecle, "Une nouvelle clé doit avoir été générée");
        log.info("Nouvelle entité: {}", nouvelle);
    }

    @Test
    @Sql("test-data.sql")
    void erreurCreationEntite() {
        log.info("Créer une entité avec erreur");
        Galerie nouvelle = new Galerie();
        nouvelle.setNom("Saatchi");  // Ce libellé existe dans le jeu de test
        nouvelle.setAdresse("rue des lilas, Paris");
        try { // L'enregistreement peut générer des exceptions (ex : violation de contrainte d'intégrité)
            galerieDAO.save(nouvelle);
            fail("Les libellés doivent être tous distincts, on doit avoir une exception");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, on a eu l'exception attendue
        }

        assertNull(nouvelle.getId(), "La clé n'a pas été générée, l'entité n'est pas enregistrée");
    }

    @Test
    @Sql("test-data.sql")
    void onDetruitUneGalerie() {
        log.info("Détruire une catégorie avec des produits");
        Galerie g = galerieDAO.getOne(1);
        assertEquals("Saatchi", g.getNom());
        galerieDAO.delete(g);
        Optional res = galerieDAO.findById(1);
        assertFalse(res.isPresent(), "Cette galerie n'existe plus");
    }
}
