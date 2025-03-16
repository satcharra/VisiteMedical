package gestion.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class VisiterId implements Serializable {
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "codemed", nullable = false)
    private Medecin medecin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codepat", nullable = false)
    private Patient patient;

    public VisiterId() {}

    public VisiterId(Medecin medecin, Patient patient){
        this.medecin = medecin;
        this.patient = patient;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisiterId visiterId = (VisiterId) o;
        return medecin.equals(visiterId.medecin) && patient.equals(visiterId.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medecin, patient);
    }
}
