package galerie.entity;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Tableau {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(unique=true)
    @NonNull
    private String titre;
    
    @Column(unique=true)
    private String support;
    
    @Column
    private int largeur;
    
    @Column
    private int hauteur;
      
}
