package galerie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import galerie.entity.Galerie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring 
public interface GalerieRepository extends JpaRepository<Galerie, Integer> {
    @Query(value =
        "SELECT SUM(prix_vente) AS budget FROM Galerie " +
        "INNER JOIN Exposition ON Exposition.galerie_id = Galerie.id "+
        "INNER JOIN Transaction ON Transaction.exposition_id = Exposition.id "+
        "WHERE extract(year FROM Transaction.vendu_le) = :annee "+
        "AND Galerie.id = :id "+
        "Group by Galerie.nom",
        nativeQuery = true)
    Float CAanneeSQL(@Param("annee")int annee, @Param("id")int id);
    
    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT id FROM Exposition "
            + "WHERE Exposition.galerie_id = :id) "
            + "THEN CAST(1 AS BIT)ELSE CAST(0 AS BIT) END",
            nativeQuery = true)
    Boolean aDesExpo(Integer id);
}
