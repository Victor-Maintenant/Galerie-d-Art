package galerie.entity;
import java.sql.Date;
import javax.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Artiste {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    
    
    @Column(unique=true)
    @NonNull
    private String biographie;
    
}
