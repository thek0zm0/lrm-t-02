package hfoods.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_meal")
public class Meal implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String timeHour;

    @ManyToOne
    @JoinColumn(name = "diet_id")
    private Diet diet;
}
