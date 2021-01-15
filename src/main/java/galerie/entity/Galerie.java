package galerie.entity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

// Un exemple d'entité
// On utilise Lombok pour auto-générer getter / setter / toString...
// cf. https://examples.javacodegeeks.com/spring-boot-with-lombok/
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Galerie {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(unique=true)
    @NonNull
    private String nom;
    
    @Column(unique=true)
    @NonNull
    private String adresse;
    
    @OneToMany(mappedBy = "galerie")
    private List<Exposition> expositions = new LinkedList<>();

    public Galerie(int id, String nom, String ville) {
        this.id = id;
        this.nom = nom;
        this.adresse = ville;
    }

    public void ajoutExpo(Exposition e){
        this.expositions.add(e);
    }
    
    public float CAannuel(int annee){
        float res = 0;
        for(Exposition e : this.expositions){
            if(e.getDebut().getYear() == annee){
                res += e.CA();
            }
        }
        return res;
    }
}
