package galerie.entity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Exposition {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    
    @Column
    @NonNull
    private Date debut;
    
    @Column
    @NonNull
    private String intitule;
    
    @Column
    private int duree;
    
    @ManyToOne @NonNull
    private Galerie galerie;
    
    @OneToMany(mappedBy = "exposition")
    private List<Transaction> transactions = new LinkedList<>();
    
    @ManyToMany
    @JoinTable(name="expo_tableau",joinColumns = 
                @JoinColumn(name = "exposition_id", referencedColumnName="id"),
        inverseJoinColumns = 
                @JoinColumn(name = "tableau_id",  referencedColumnName="id"))    
    List<Tableau> tableaux = new LinkedList<>();
    
    public Exposition(int id, String intitule, int duree, Galerie galerie){
        this.id = id;
        this.debut = Date.valueOf(LocalDate.now());
        this.intitule = intitule;
        this.duree = duree;
        this.galerie = galerie;
    }
    
    public void ajoutTransac(Transaction t){
        this.transactions.add(t);
    }
    
    public float CA(){
        float res = 0;
        for (Transaction t : this.transactions){
            res += t.getPrixVente();
        }
        return res;
    }
}
