package galerie.entity;
import java.sql.Date;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Transaction {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(unique=true)
    @NonNull
    private Date venduLe;
    
    @Column(unique=true)
    private float prixVente;
      
    @ManyToOne @NonNull
    private Exposition exposition;
    
    @ManyToOne @NonNull
    private Personne personne;
    
    @OneToOne @NonNull
    private Tableau tableau;
}
