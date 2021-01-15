package galerie.dao;
import galerie.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Victor Maintenant
 */
public interface PersonneRepository extends JpaRepository<Personne, Integer> {

    @Query(value = "SELECT SUM(prix_vente) AS budget FROM Personne " +
        "INNER JOIN Transaction ON Transaction.personne_id = Personne.id "+
        "WHERE extract(year FROM Transaction.vendu_le) = :annee and Personne.id = :id "+
        "Group by Personne.nom",
        nativeQuery = true)
    float budgetArtSQL(int annee, int id);
}
