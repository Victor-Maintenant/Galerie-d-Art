package galerie.entity;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA 
public class Artiste extends Personne{
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    
    @Column(unique=true)
    @NonNull
    private String biographie;
    
    public Artiste(String nom, String adresse, String biographie) {
        super(nom, adresse);
        this.biographie = biographie;
    }
    
    @OneToMany(mappedBy = "artiste")
    private List<Tableau> tableaux;
    
}
