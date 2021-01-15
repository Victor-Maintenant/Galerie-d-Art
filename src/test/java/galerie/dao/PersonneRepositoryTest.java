/**
 *
 * @author Victor Maintenant
 */
package galerie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import galerie.dao.GalerieRepository;
import galerie.entity.Personne;
import galerie.entity.Transaction;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class PersonneRepositoryTest {

    @Autowired
    private PersonneRepository personneDAO;

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    public void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Personne'");
        int combienDansLeJeuDeTest = 4; 
        long nombre = personneDAO.count();
        assertEquals(combienDansLeJeuDeTest, nombre, "On doit trouver 4 personnes" );
    }
    
    @Test
    @Sql("test-data.sql")
    public void connaitreLeBudgetArtSQL(){
        log.info("On calcule le CA d'une galerie donnée selon une année donnée.");
        assertEquals(40000, personneDAO.budgetArtSQL(2020, 1), "On doit avoir 40000€ de recette");
    }
    
    @Test
    public void connaitreLeBudgetArt(){
        Personne p1 = new Personne("personne1", "France");
        Transaction t1 = new Transaction(1, Date.valueOf(LocalDate.now()), 20000, p1);
        Transaction t2 = new Transaction(2, Date.valueOf(LocalDate.now()), 20000, p1);
        Transaction t3 = new Transaction(3, Date.valueOf(LocalDate.of(2019, Month.JULY, 14)), 20000, p1);
        assertTrue(p1.getTransactions().isEmpty());
        p1.ajoutTransac(t1);
        p1.ajoutTransac(t2);
        p1.ajoutTransac(t3);
        assertFalse(p1.getTransactions().isEmpty());
        assertEquals(40000, p1.budgetArt(Date.valueOf(LocalDate.now()).getYear()));

    }
    
    @Test
    @Sql("test-data.sql")
    void listerLesEntites() {
        log.info("Lister les entités");
        List<Personne> liste = personneDAO.findAll(); // Renvoie la liste des entités dans la table
        log.info("Liste des entités: {}", liste);
    }

    @Test
    @Sql("test-data.sql")
    void touverParCle() {
        log.info("Trouver une entité par sa clé");
        int codePresent = 1;
        Optional<Personne> resultat = personneDAO.findById(codePresent);
        // On s'assure qu'on trouvé le résultat
        assertTrue(resultat.isPresent(), "Cette galerie existe");
        Personne p = resultat.get();
        assertEquals("Bastide", p.getNom());
        log.info("Entité trouvée: {}", p);
    }

    @Test
    @Sql("test-data.sql")
    void entiteInconnue() {
        log.info("Chercher une entité inconnue");
        int codeInconnu = 99;
        Optional<Personne> resultat = personneDAO.findById(codeInconnu);
        assertFalse(resultat.isPresent(), "Cette catégorie n'existe pas");

    }

    @Test
    @Sql("test-data.sql")
    void creerUneEntite() {
        log.info("Créer une entité");
        Personne nouvelle = new Personne();
        nouvelle.setNom("Test");
        nouvelle.setAdresse("Paris, France");
        assertNull(nouvelle.getId(), "L'entité n'a pas encore de clé");
        personneDAO.save(nouvelle); // 'save' enregistre l'entite dans la base
        Integer nouvellecle = nouvelle.getId(); // La clé a été auto-générée lors de l'enregistrement
        assertNotNull(nouvellecle, "Une nouvelle clé doit avoir été générée");
        log.info("Nouvelle entité: {}", nouvelle);
    }

    @Test
    @Sql("test-data.sql")
    void onNePeutPasDetruireUnePersonneQuiADesTransaction() {
        log.info("Détruire une catégorie avec des produits");
        Personne p = personneDAO.getOne(1);
        assertEquals("Bastide", p.getNom());
        // Il y a des produits dans la catégorie 'Boissons'
        assertTrue(personneDAO.aDesTransaction(p.getId()));
        // Si on essaie de détruire cette catégorie, on doit avoir une exception
        // de violation de contrainte d'intégrité
        assertThrows(DataIntegrityViolationException.class, () -> {
            personneDAO.delete(p);
            personneDAO.flush(); // Pour forcer la validation de la transaction
        });
    }
}