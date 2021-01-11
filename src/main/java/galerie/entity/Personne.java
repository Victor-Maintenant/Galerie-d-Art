package galerie.entity;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor
@ToString(callSuper = true) 
@Inheritance(strategy = InheritanceType.JOINED)
@Entity // Une entité JPA
public class Personne {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(unique=true)
    @NonNull
    private String nom;
    
    @Column(unique=true)
    private String adresse;
    
    public Personne(String nom, String adresse){
        this.nom = nom;
        this.adresse = adresse;
    }
    
    @OneToMany(mappedBy = "personne")
    private List<Transaction> transactions;
}