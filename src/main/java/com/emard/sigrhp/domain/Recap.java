package com.emard.sigrhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Recap.
 */
/*@Entity
@Table(name = "recap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class Recap implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "brut_fiscal")
    private Double brutFiscal;

    @Column(name = "net_a_payer")
    private Double netAPayer;

    @Column(name = "salaire_brut_mensuel")
    private Double salaireBrutMensuel;

    @Column(name = "impot_sur_revenu")
    private Double impotSurRevenu;

    @Column(name = "trimf")
    private Double trimf;

    @Column(name = "ipres_part_salariale")
    private Double ipresPartSalariale;

    @Column(name = "tot_retenue")
    private Double totRetenue;

    @Column(name = "ipres_part_patronales")
    private Double ipresPartPatronales;

    @Column(name = "css_accident_de_travail")
    private Double cssAccidentDeTravail;

    @Column(name = "css_prestation_familiale")
    private Double cssPrestationFamiliale;

    @Column(name = "ipm_patronale")
    private Double ipmPatronale;

    @Column(name = "contribution_forfaitaire")
    private Double contributionForfaitaire;

    @Column(name = "prime_imposable")
    private Double primeImposable;

    @Column(name = "prime_non_imposable")
    private Double primeNonImposable;

    @Column(name = "avantage")
    private Double avantage;

    @Column(name = "recap_ligne")
    private Double recapLigne;

    @ManyToOne
    @JsonIgnoreProperties(value = "recaps", allowSetters = true)
    private Collaborateur collaborateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBrutFiscal() {
        return brutFiscal;
    }

    public Recap brutFiscal(Double brutFiscal) {
        this.brutFiscal = brutFiscal;
        return this;
    }

    public void setBrutFiscal(Double brutFiscal) {
        this.brutFiscal = brutFiscal;
    }

    public Double getNetAPayer() {
        return netAPayer;
    }

    public Recap netAPayer(Double netAPayer) {
        this.netAPayer = netAPayer;
        return this;
    }

    public void setNetAPayer(Double netAPayer) {
        this.netAPayer = netAPayer;
    }

    public Double getSalaireBrutMensuel() {
        return salaireBrutMensuel;
    }

    public Recap salaireBrutMensuel(Double salaireBrutMensuel) {
        this.salaireBrutMensuel = salaireBrutMensuel;
        return this;
    }

    public void setSalaireBrutMensuel(Double salaireBrutMensuel) {
        this.salaireBrutMensuel = salaireBrutMensuel;
    }

    public Double getImpotSurRevenu() {
        return impotSurRevenu;
    }

    public Recap impotSurRevenu(Double impotSurRevenu) {
        this.impotSurRevenu = impotSurRevenu;
        return this;
    }

    public void setImpotSurRevenu(Double impotSurRevenu) {
        this.impotSurRevenu = impotSurRevenu;
    }

    public Double getTrimf() {
        return trimf;
    }

    public Recap trimf(Double trimf) {
        this.trimf = trimf;
        return this;
    }

    public void setTrimf(Double trimf) {
        this.trimf = trimf;
    }

    public Double getIpresPartSalariale() {
        return ipresPartSalariale;
    }

    public Recap ipresPartSalariale(Double ipresPartSalariale) {
        this.ipresPartSalariale = ipresPartSalariale;
        return this;
    }

    public void setIpresPartSalariale(Double ipresPartSalariale) {
        this.ipresPartSalariale = ipresPartSalariale;
    }

    public Double getTotRetenue() {
        return totRetenue;
    }

    public Recap totRetenue(Double totRetenue) {
        this.totRetenue = totRetenue;
        return this;
    }

    public void setTotRetenue(Double totRetenue) {
        this.totRetenue = totRetenue;
    }

    public Double getIpresPartPatronales() {
        return ipresPartPatronales;
    }

    public Recap ipresPartPatronales(Double ipresPartPatronales) {
        this.ipresPartPatronales = ipresPartPatronales;
        return this;
    }

    public void setIpresPartPatronales(Double ipresPartPatronales) {
        this.ipresPartPatronales = ipresPartPatronales;
    }

    public Double getCssAccidentDeTravail() {
        return cssAccidentDeTravail;
    }

    public Recap cssAccidentDeTravail(Double cssAccidentDeTravail) {
        this.cssAccidentDeTravail = cssAccidentDeTravail;
        return this;
    }

    public void setCssAccidentDeTravail(Double cssAccidentDeTravail) {
        this.cssAccidentDeTravail = cssAccidentDeTravail;
    }

    public Double getCssPrestationFamiliale() {
        return cssPrestationFamiliale;
    }

    public Recap cssPrestationFamiliale(Double cssPrestationFamiliale) {
        this.cssPrestationFamiliale = cssPrestationFamiliale;
        return this;
    }

    public void setCssPrestationFamiliale(Double cssPrestationFamiliale) {
        this.cssPrestationFamiliale = cssPrestationFamiliale;
    }

    public Double getIpmPatronale() {
        return ipmPatronale;
    }

    public Recap ipmPatronale(Double ipmPatronale) {
        this.ipmPatronale = ipmPatronale;
        return this;
    }

    public void setIpmPatronale(Double ipmPatronale) {
        this.ipmPatronale = ipmPatronale;
    }

    public Double getContributionForfaitaire() {
        return contributionForfaitaire;
    }

    public Recap contributionForfaitaire(Double contributionForfaitaire) {
        this.contributionForfaitaire = contributionForfaitaire;
        return this;
    }

    public void setContributionForfaitaire(Double contributionForfaitaire) {
        this.contributionForfaitaire = contributionForfaitaire;
    }

    public Double getPrimeImposable() {
        return primeImposable;
    }

    public Recap primeImposable(Double primeImposable) {
        this.primeImposable = primeImposable;
        return this;
    }

    public void setPrimeImposable(Double primeImposable) {
        this.primeImposable = primeImposable;
    }

    public Double getPrimeNonImposable() {
        return primeNonImposable;
    }

    public Recap primeNonImposable(Double primeNonImposable) {
        this.primeNonImposable = primeNonImposable;
        return this;
    }

    public void setPrimeNonImposable(Double primeNonImposable) {
        this.primeNonImposable = primeNonImposable;
    }

    public Double getAvantage() {
        return avantage;
    }

    public Recap avantage(Double avantage) {
        this.avantage = avantage;
        return this;
    }

    public void setAvantage(Double avantage) {
        this.avantage = avantage;
    }

    public Double getRecapLigne() {
        return recapLigne;
    }

    public Recap recapLigne(Double recapLigne) {
        this.recapLigne = recapLigne;
        return this;
    }

    public void setRecapLigne(Double recapLigne) {
        this.recapLigne = recapLigne;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public Recap collaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
        return this;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recap)) {
            return false;
        }
        return id != null && id.equals(((Recap) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recap{" +
            "id=" + getId() +
            ", brutFiscal=" + getBrutFiscal() +
            ", netAPayer=" + getNetAPayer() +
            ", salaireBrutMensuel=" + getSalaireBrutMensuel() +
            ", impotSurRevenu=" + getImpotSurRevenu() +
            ", trimf=" + getTrimf() +
            ", ipresPartSalariale=" + getIpresPartSalariale() +
            ", totRetenue=" + getTotRetenue() +
            ", ipresPartPatronales=" + getIpresPartPatronales() +
            ", cssAccidentDeTravail=" + getCssAccidentDeTravail() +
            ", cssPrestationFamiliale=" + getCssPrestationFamiliale() +
            ", ipmPatronale=" + getIpmPatronale() +
            ", contributionForfaitaire=" + getContributionForfaitaire() +
            ", primeImposable=" + getPrimeImposable() +
            ", primeNonImposable=" + getPrimeNonImposable() +
            ", avantage=" + getAvantage() +
            ", recapLigne=" + getRecapLigne() +
            "}";
    }*/
}
