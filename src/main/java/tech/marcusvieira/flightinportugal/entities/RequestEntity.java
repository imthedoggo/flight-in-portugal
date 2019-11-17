package tech.marcusvieira.flightinportugal.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
@Entity
public class RequestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fly_from")
    private String flyFrom;

    @Column(name = "fly_to")
    private String flyTo;

    @Column(name = "date_from")
    private String dateFrom;

    @Column(name = "date_to")
    private String dateTo;

    private String currency;

    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }
}