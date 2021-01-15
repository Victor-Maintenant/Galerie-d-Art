package galerie.entity;
import galerie.dao.PersonneRepository;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor
@ToString(callSuper = true) 
@Inheritance(strategy = InheritanceType.JOINED)
@Entity // Une entité JPA
public class Personne{
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @NonNull
    private String nom;
    
    private String adresse;
    
    public Personne(String nom, String adresse){
        this.nom = nom;
        this.adresse = adresse;
    }
    
    @OneToMany(mappedBy = "personne")
    private List<Transaction> transactions = new LinkedList<>();
    
    public void ajoutTransac(Transaction t){
        this.transactions.add(t);
    }
    
    public float budgetArt(int annee){
        float res = 0;
        for(Transaction t : this.transactions){
            if (t.getVenduLe().getYear() == annee){
                res += t.getPrixVente();
            }
        }
        return res;
    }
}