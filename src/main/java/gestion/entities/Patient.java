package gestion.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "codepat", length = 20, unique = true, nullable = false)
    private String codePat;

    @Column(name = "nom",nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom",length = 50)
    private String prenom;

    @Column(name = "sexe",length = 10)
    private String sexe;

    @Column(name = "adresse",length = 255)
    private String adresse;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Visiter> visiters;

    public Patient() {}

    public Patient(String codePat, String nom, String prenom, String sexe, String adresse) {
        this.codePat = codePat;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.adresse = adresse;
    }

    public String getCodePat() {
        return codePat;
    }

    public void setCodePat(String codePat) {
        this.codePat = codePat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
