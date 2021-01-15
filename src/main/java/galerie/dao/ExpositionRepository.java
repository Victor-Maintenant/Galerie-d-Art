package galerie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import galerie.entity.Exposition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Victor Maintenant
 */
public interface ExpositionRepository extends JpaRepository<Exposition, Integer> {

    @Query(
            value = "SELECT SUM(prix_vente) AS budget FROM Exposition "
            + "INNER JOIN Transaction ON Transaction.exposition_id = Exposition.id "
            + "WHERE Exposition.id = :id Group by Exposition.id",
            nativeQuery = true)
    public Float CASQL(int id);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT id FROM Transaction "
            + "WHERE Transaction.exposition_id = :id) "
            + "THEN CAST(1 AS BIT)ELSE CAST(0 AS BIT) END",
            nativeQuery = true)
    public Boolean aDesTransactions(Integer id);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT Expo_tableau.exposition_id FROM Expo_tableau "
            + "WHERE Expo_tableau.exposition_id = :id) "
            + "THEN CAST(1 AS BIT)ELSE CAST(0 AS BIT) END",
            nativeQuery = true)
    public Boolean aDesTableaux(Integer id);
    
}
