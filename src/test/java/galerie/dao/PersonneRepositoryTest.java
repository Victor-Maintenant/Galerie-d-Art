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

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class PersonneRepositoryTest {

    @Autowired
    private PersonneRepository personneDAO;

    @Test
    @Sql("testPersonne-data.sql") // On peut charger des donnnées spécifiques pour un test
    public void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Personne'");
        int combienDansLeJeuDeTest = 4; 
        long nombre = personneDAO.count();
        assertEquals(combienDansLeJeuDeTest, nombre, "On doit trouver 4 personnes" );
    }
    
    @Test
    @Sql("testCAannee-data.sql")
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
}