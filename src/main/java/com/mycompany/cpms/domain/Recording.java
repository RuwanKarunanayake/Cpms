package com.mycompany.cpms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Recording.
 */
@Entity
@Table(name = "recording")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recording implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ferbrile", nullable = false)
    private Boolean ferbrile;

    @NotNull
    @Column(name = "pillar", nullable = false)
    private Boolean pillar;

    @NotNull
    @Column(name = "dispensys", nullable = false)
    private Boolean dispensys;

    @Column(name = "r_r")
    private String rR;

    @NotNull
    @Column(name = "clear_lungs", nullable = false)
    private Boolean clearLungs;

    @NotNull
    @Column(name = "ronchi", nullable = false)
    private Boolean ronchi;

    @NotNull
    @Column(name = "crackles", nullable = false)
    private Boolean crackles;

    @Lob
    @Column(name = "other_rs")
    private String otherRS;

    @Column(name = "h_r")
    private String hR;

    @NotNull
    @Column(name = "regular", nullable = false)
    private Boolean regular;

    @NotNull
    @Column(name = "murmurs", nullable = false)
    private Boolean murmurs;

    @Lob
    @Column(name = "other_cvs")
    private String otherCVS;

    @NotNull
    @Column(name = "soft_abdomen", nullable = false)
    private Boolean softAbdomen;

    @NotNull
    @Column(name = "tense", nullable = false)
    private Boolean tense;

    @NotNull
    @Column(name = "tender", nullable = false)
    private Boolean tender;

    @NotNull
    @Column(name = "none_tender", nullable = false)
    private Boolean noneTender;

    @Lob
    @Column(name = "other_abdomen")
    private String otherAbdomen;

    @Lob
    @Column(name = "neurology")
    private String neurology;

    @Lob
    @Column(name = "other_systems")
    private String otherSystems;

    @NotNull
    @Size(min = 5, max = 15)
    @Column(name = "rec_no", length = 15, nullable = false)
    private String recNo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    private Clinic clinic;

    @OneToOne(mappedBy = "recording")
    @JsonIgnore
    private Investigation investigation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFerbrile() {
        return ferbrile;
    }

    public Recording ferbrile(Boolean ferbrile) {
        this.ferbrile = ferbrile;
        return this;
    }

    public void setFerbrile(Boolean ferbrile) {
        this.ferbrile = ferbrile;
    }

    public Boolean isPillar() {
        return pillar;
    }

    public Recording pillar(Boolean pillar) {
        this.pillar = pillar;
        return this;
    }

    public void setPillar(Boolean pillar) {
        this.pillar = pillar;
    }

    public Boolean isDispensys() {
        return dispensys;
    }

    public Recording dispensys(Boolean dispensys) {
        this.dispensys = dispensys;
        return this;
    }

    public void setDispensys(Boolean dispensys) {
        this.dispensys = dispensys;
    }

    public String getrR() {
        return rR;
    }

    public Recording rR(String rR) {
        this.rR = rR;
        return this;
    }

    public void setrR(String rR) {
        this.rR = rR;
    }

    public Boolean isClearLungs() {
        return clearLungs;
    }

    public Recording clearLungs(Boolean clearLungs) {
        this.clearLungs = clearLungs;
        return this;
    }

    public void setClearLungs(Boolean clearLungs) {
        this.clearLungs = clearLungs;
    }

    public Boolean isRonchi() {
        return ronchi;
    }

    public Recording ronchi(Boolean ronchi) {
        this.ronchi = ronchi;
        return this;
    }

    public void setRonchi(Boolean ronchi) {
        this.ronchi = ronchi;
    }

    public Boolean isCrackles() {
        return crackles;
    }

    public Recording crackles(Boolean crackles) {
        this.crackles = crackles;
        return this;
    }

    public void setCrackles(Boolean crackles) {
        this.crackles = crackles;
    }

    public String getOtherRS() {
        return otherRS;
    }

    public Recording otherRS(String otherRS) {
        this.otherRS = otherRS;
        return this;
    }

    public void setOtherRS(String otherRS) {
        this.otherRS = otherRS;
    }

    public String gethR() {
        return hR;
    }

    public Recording hR(String hR) {
        this.hR = hR;
        return this;
    }

    public void sethR(String hR) {
        this.hR = hR;
    }

    public Boolean isRegular() {
        return regular;
    }

    public Recording regular(Boolean regular) {
        this.regular = regular;
        return this;
    }

    public void setRegular(Boolean regular) {
        this.regular = regular;
    }

    public Boolean isMurmurs() {
        return murmurs;
    }

    public Recording murmurs(Boolean murmurs) {
        this.murmurs = murmurs;
        return this;
    }

    public void setMurmurs(Boolean murmurs) {
        this.murmurs = murmurs;
    }

    public String getOtherCVS() {
        return otherCVS;
    }

    public Recording otherCVS(String otherCVS) {
        this.otherCVS = otherCVS;
        return this;
    }

    public void setOtherCVS(String otherCVS) {
        this.otherCVS = otherCVS;
    }

    public Boolean isSoftAbdomen() {
        return softAbdomen;
    }

    public Recording softAbdomen(Boolean softAbdomen) {
        this.softAbdomen = softAbdomen;
        return this;
    }

    public void setSoftAbdomen(Boolean softAbdomen) {
        this.softAbdomen = softAbdomen;
    }

    public Boolean isTense() {
        return tense;
    }

    public Recording tense(Boolean tense) {
        this.tense = tense;
        return this;
    }

    public void setTense(Boolean tense) {
        this.tense = tense;
    }

    public Boolean isTender() {
        return tender;
    }

    public Recording tender(Boolean tender) {
        this.tender = tender;
        return this;
    }

    public void setTender(Boolean tender) {
        this.tender = tender;
    }

    public Boolean isNoneTender() {
        return noneTender;
    }

    public Recording noneTender(Boolean noneTender) {
        this.noneTender = noneTender;
        return this;
    }

    public void setNoneTender(Boolean noneTender) {
        this.noneTender = noneTender;
    }

    public String getOtherAbdomen() {
        return otherAbdomen;
    }

    public Recording otherAbdomen(String otherAbdomen) {
        this.otherAbdomen = otherAbdomen;
        return this;
    }

    public void setOtherAbdomen(String otherAbdomen) {
        this.otherAbdomen = otherAbdomen;
    }

    public String getNeurology() {
        return neurology;
    }

    public Recording neurology(String neurology) {
        this.neurology = neurology;
        return this;
    }

    public void setNeurology(String neurology) {
        this.neurology = neurology;
    }

    public String getOtherSystems() {
        return otherSystems;
    }

    public Recording otherSystems(String otherSystems) {
        this.otherSystems = otherSystems;
        return this;
    }

    public void setOtherSystems(String otherSystems) {
        this.otherSystems = otherSystems;
    }

    public String getRecNo() {
        return recNo;
    }

    public Recording recNo(String recNo) {
        this.recNo = recNo;
        return this;
    }

    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    public LocalDate getDate() {
        return date;
    }

    public Recording date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public Recording user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public Recording clinic(Clinic clinic) {
        this.clinic = clinic;
        return this;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Investigation getInvestigation() {
        return investigation;
    }

    public Recording investigation(Investigation investigation) {
        this.investigation = investigation;
        return this;
    }

    public void setInvestigation(Investigation investigation) {
        this.investigation = investigation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recording recording = (Recording) o;
        if (recording.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, recording.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Recording{" +
            "id=" + id +
            ", ferbrile='" + ferbrile + "'" +
            ", pillar='" + pillar + "'" +
            ", dispensys='" + dispensys + "'" +
            ", rR='" + rR + "'" +
            ", clearLungs='" + clearLungs + "'" +
            ", ronchi='" + ronchi + "'" +
            ", crackles='" + crackles + "'" +
            ", otherRS='" + otherRS + "'" +
            ", hR='" + hR + "'" +
            ", regular='" + regular + "'" +
            ", murmurs='" + murmurs + "'" +
            ", otherCVS='" + otherCVS + "'" +
            ", softAbdomen='" + softAbdomen + "'" +
            ", tense='" + tense + "'" +
            ", tender='" + tender + "'" +
            ", noneTender='" + noneTender + "'" +
            ", otherAbdomen='" + otherAbdomen + "'" +
            ", neurology='" + neurology + "'" +
            ", otherSystems='" + otherSystems + "'" +
            ", recNo='" + recNo + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
