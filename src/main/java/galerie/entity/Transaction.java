package galerie.entity;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Transaction {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column
    @NonNull
    private Date venduLe;
    
    @Column
    private float prixVente;
      
    @ManyToOne @NonNull
    private Exposition exposition;
    
    @ManyToOne @NonNull
    private Personne personne;
    
    @OneToOne @NonNull
    private Tableau tableau;
    
    public Transaction(int id, Date vendu, float prix, Exposition expo){
        this.id = id;
        this.venduLe = vendu;
        this.prixVente = prix;
        this.exposition = expo;
    }

    public Transaction(int id, Date vendu, float prix, Personne perso) {
        this.id = id;
        this.venduLe = vendu;
        this.prixVente = prix;
        this.personne = perso;
    }
}
