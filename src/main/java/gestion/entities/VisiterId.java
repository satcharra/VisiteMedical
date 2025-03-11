package gestion.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class VisiterId implements Serializable {
    @Column(name = "codemed")
    private String codeMed;
    @Column(name = "codepat")
    private String codePat;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    public VisiterId() {}

    public VisiterId(String codeMed, String codePat, Date date) {
        this.codeMed = codeMed;
        this.codePat = codePat;
        this.date = date;
    }

    public String getCodeMed() {
        return codeMed;
    }

    public void setCodeMed(String codeMed) {
        this.codeMed = codeMed;
    }

    public String getCodePat() {
        return codePat;
    }

    public void setCodePat(String codePat) {
        this.codePat = codePat;
    }

    public Date getDateVisite() {
        return date;
    }

    public void setDateVisite(Date dateVisite) {
        this.date = dateVisite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisiterId visiterId = (VisiterId) o;
        return Objects.equals(codePat, visiterId.codePat) && Objects.equals(codeMed, visiterId.codeMed) &&
                Objects.equals(date, visiterId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codePat, codeMed, date);
    }

}
