package gestion.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "visiter")
public class Visiter {
    @EmbeddedId
    private VisiterId id;
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date dateVisite;

    public Visiter() {}

    public Visiter(VisiterId id, Date dateVisite) {
        this.id = id;
        this.dateVisite = dateVisite;
    }

    public VisiterId getId() {
        return id;
    }

    public void setId(VisiterId id) {
        this.id = id;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }
    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Visiter visiter = (Visiter) o;
//        return Objects.equals(id, visiter.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
