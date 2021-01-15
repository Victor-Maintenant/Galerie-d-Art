package galerie.dao;

import galerie.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Victor Maintenant
 */
public interface PersonneRepository extends JpaRepository<Personne, Integer> {

    @Query(value = "SELECT SUM(prix_vente) AS budget FROM Personne "
            + "INNER JOIN Transaction ON Transaction.personne_id = Personne.id "
            + "WHERE extract(year FROM Transaction.vendu_le) = :annee and Personne.id = :id "
            + "Group by Personne.nom",
            nativeQuery = true)
    public Float budgetArtSQL(int annee, int id);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT id FROM Transaction "
            + "Where Transaction.personne_id = :id) "
            + "THEN CAST(1 as BIT) ELSE CAST(0 as BIT) END",
            nativeQuery = true)
    public Boolean aDesTransaction(Integer id);
}
