package gestion.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "visiter")
public class Visiter {
    @EmbeddedId
    private VisiterId id;
    @ManyToOne
    @MapsId("codeMed")
    @JoinColumn(name = "codemed")
    private Medecin medecin;

    @ManyToOne
    @MapsId("codePat")
    @JoinColumn(name = "codepat")
    private Patient patient;

    public Visiter(){}

    public Visiter(Patient patient, Medecin medecin, Date dateVisite){
        this.patient = patient;
        this.medecin = medecin;
        this.id = new VisiterId(patient.getCodePat(), medecin.getCodeMed(), dateVisite);
    }

    public VisiterId getId() {
        return id;
    }

    public void setId(VisiterId id) {
        this.id = id;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDateVisite() {
        if (this.id == null) {
            throw new IllegalStateException("VisiterId non initialisé");
        }
        return this.id.getDateVisite(); // Appel au getter public
    }

    public void setDateVisite(Date date) {
        if (this.id == null) {
            this.id = new VisiterId();
        }
        this.id.setDateVisite(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visiter visiter = (Visiter) o;
        return Objects.equals(id, visiter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
