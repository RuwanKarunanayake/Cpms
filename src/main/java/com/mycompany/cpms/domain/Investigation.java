package com.mycompany.cpms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Investigation.
 */
@Entity
@Table(name = "investigation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Investigation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "f_bc", nullable = false)
    private Boolean fBC;

    @NotNull
    @Column(name = "b_v", nullable = false)
    private Boolean bV;

    @NotNull
    @Column(name = "s_e", nullable = false)
    private Boolean sE;

    @NotNull
    @Column(name = "s_cv", nullable = false)
    private Boolean sCV;

    @NotNull
    @Column(name = "l_ft", nullable = false)
    private Boolean lFT;

    @NotNull
    @Column(name = "lipid_profile", nullable = false)
    private Boolean lipidProfile;

    @NotNull
    @Column(name = "f_bs", nullable = false)
    private Boolean fBS;

    @NotNull
    @Column(name = "p_pbs", nullable = false)
    private Boolean pPBS;

    @NotNull
    @Column(name = "blood_test_completed", nullable = false)
    private Boolean bloodTestCompleted;

    @NotNull
    @Column(name = "u_fr", nullable = false)
    private Boolean uFR;

    @NotNull
    @Column(name = "urine_test_completed", nullable = false)
    private Boolean urineTestCompleted;

    @NotNull
    @Column(name = "culture", nullable = false)
    private Boolean culture;

    @NotNull
    @Column(name = "culture_test_completed", nullable = false)
    private Boolean cultureTestCompleted;

    @Lob
    @Column(name = "other")
    private String other;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Recording recording;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isfBC() {
        return fBC;
    }

    public Investigation fBC(Boolean fBC) {
        this.fBC = fBC;
        return this;
    }

    public void setfBC(Boolean fBC) {
        this.fBC = fBC;
    }

    public Boolean isbV() {
        return bV;
    }

    public Investigation bV(Boolean bV) {
        this.bV = bV;
        return this;
    }

    public void setbV(Boolean bV) {
        this.bV = bV;
    }

    public Boolean issE() {
        return sE;
    }

    public Investigation sE(Boolean sE) {
        this.sE = sE;
        return this;
    }

    public void setsE(Boolean sE) {
        this.sE = sE;
    }

    public Boolean issCV() {
        return sCV;
    }

    public Investigation sCV(Boolean sCV) {
        this.sCV = sCV;
        return this;
    }

    public void setsCV(Boolean sCV) {
        this.sCV = sCV;
    }

    public Boolean islFT() {
        return lFT;
    }

    public Investigation lFT(Boolean lFT) {
        this.lFT = lFT;
        return this;
    }

    public void setlFT(Boolean lFT) {
        this.lFT = lFT;
    }

    public Boolean isLipidProfile() {
        return lipidProfile;
    }

    public Investigation lipidProfile(Boolean lipidProfile) {
        this.lipidProfile = lipidProfile;
        return this;
    }

    public void setLipidProfile(Boolean lipidProfile) {
        this.lipidProfile = lipidProfile;
    }

    public Boolean isfBS() {
        return fBS;
    }

    public Investigation fBS(Boolean fBS) {
        this.fBS = fBS;
        return this;
    }

    public void setfBS(Boolean fBS) {
        this.fBS = fBS;
    }

    public Boolean ispPBS() {
        return pPBS;
    }

    public Investigation pPBS(Boolean pPBS) {
        this.pPBS = pPBS;
        return this;
    }

    public void setpPBS(Boolean pPBS) {
        this.pPBS = pPBS;
    }

    public Boolean isBloodTestCompleted() {
        return bloodTestCompleted;
    }

    public Investigation bloodTestCompleted(Boolean bloodTestCompleted) {
        this.bloodTestCompleted = bloodTestCompleted;
        return this;
    }

    public void setBloodTestCompleted(Boolean bloodTestCompleted) {
        this.bloodTestCompleted = bloodTestCompleted;
    }

    public Boolean isuFR() {
        return uFR;
    }

    public Investigation uFR(Boolean uFR) {
        this.uFR = uFR;
        return this;
    }

    public void setuFR(Boolean uFR) {
        this.uFR = uFR;
    }

    public Boolean isUrineTestCompleted() {
        return urineTestCompleted;
    }

    public Investigation urineTestCompleted(Boolean urineTestCompleted) {
        this.urineTestCompleted = urineTestCompleted;
        return this;
    }

    public void setUrineTestCompleted(Boolean urineTestCompleted) {
        this.urineTestCompleted = urineTestCompleted;
    }

    public Boolean isCulture() {
        return culture;
    }

    public Investigation culture(Boolean culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Boolean culture) {
        this.culture = culture;
    }

    public Boolean isCultureTestCompleted() {
        return cultureTestCompleted;
    }

    public Investigation cultureTestCompleted(Boolean cultureTestCompleted) {
        this.cultureTestCompleted = cultureTestCompleted;
        return this;
    }

    public void setCultureTestCompleted(Boolean cultureTestCompleted) {
        this.cultureTestCompleted = cultureTestCompleted;
    }

    public String getOther() {
        return other;
    }

    public Investigation other(String other) {
        this.other = other;
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Recording getRecording() {
        return recording;
    }

    public Investigation recording(Recording recording) {
        this.recording = recording;
        return this;
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Investigation investigation = (Investigation) o;
        if (investigation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, investigation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Investigation{" +
            "id=" + id +
            ", fBC='" + fBC + "'" +
            ", bV='" + bV + "'" +
            ", sE='" + sE + "'" +
            ", sCV='" + sCV + "'" +
            ", lFT='" + lFT + "'" +
            ", lipidProfile='" + lipidProfile + "'" +
            ", fBS='" + fBS + "'" +
            ", pPBS='" + pPBS + "'" +
            ", bloodTestCompleted='" + bloodTestCompleted + "'" +
            ", uFR='" + uFR + "'" +
            ", urineTestCompleted='" + urineTestCompleted + "'" +
            ", culture='" + culture + "'" +
            ", cultureTestCompleted='" + cultureTestCompleted + "'" +
            ", other='" + other + "'" +
            '}';
    }
}
