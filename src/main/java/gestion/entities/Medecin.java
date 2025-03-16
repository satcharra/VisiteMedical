package gestion.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medecin")
public class Medecin {
    @Id
    @Column(name = "codemed", length = 20)
    private String codeMed;

    @Column(name = "nom",nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom", length = 50)
    private String prenom;

    @Column(length = 50)
    private String grade;

    @OneToMany(mappedBy = "id.medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visiter> visiters;


    public Medecin(){}

    public Medecin(String codeMed, String nom, String prenom, String grade) {
        this.codeMed = codeMed;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
    }

    public String getCodeMed() {
        return codeMed;
    }

    public void setCodeMed(String codeMed) {
        this.codeMed = codeMed;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
