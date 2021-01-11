package galerie.entity;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"TABLEAU_ID", "EXPOSITION_ID"})
})
public class apparition {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(optional = false)
    private Tableau tableau;
    
    @ManyToOne(optional = false)
    private Exposition exposition;
}