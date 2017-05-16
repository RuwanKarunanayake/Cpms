package com.mycompany.cpms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MedicalHistory.
 */
@Entity
@Table(name = "medical_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "diabates", nullable = false)
    private Boolean diabates;

    @NotNull
    @Column(name = "hypertension", nullable = false)
    private Boolean hypertension;

    @NotNull
    @Column(name = "ihd", nullable = false)
    private Boolean ihd;

    @NotNull
    @Column(name = "ba", nullable = false)
    private Boolean ba;

    @NotNull
    @Column(name = "ckd", nullable = false)
    private Boolean ckd;

    @NotNull
    @Column(name = "epilepsy", nullable = false)
    private Boolean epilepsy;

    @Lob
    @Column(name = "other_past")
    private String otherPast;

    @Lob
    @Column(name = "past_surgical")
    private String pastSurgical;

    @Lob
    @Column(name = "past_allergy")
    private String pastAllergy;

    @Lob
    @Column(name = "past_drug")
    private String pastDrug;

    @Lob
    @Column(name = "past_family")
    private String pastFamily;

    @NotNull
    @Column(name = "smoke", nullable = false)
    private Boolean smoke;

    @NotNull
    @Column(name = "alchohol", nullable = false)
    private Boolean alchohol;

    @Lob
    @Column(name = "other_history")
    private String otherHistory;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDiabates() {
        return diabates;
    }

    public MedicalHistory diabates(Boolean diabates) {
        this.diabates = diabates;
        return this;
    }

    public void setDiabates(Boolean diabates) {
        this.diabates = diabates;
    }

    public Boolean isHypertension() {
        return hypertension;
    }

    public MedicalHistory hypertension(Boolean hypertension) {
        this.hypertension = hypertension;
        return this;
    }

    public void setHypertension(Boolean hypertension) {
        this.hypertension = hypertension;
    }

    public Boolean isIhd() {
        return ihd;
    }

    public MedicalHistory ihd(Boolean ihd) {
        this.ihd = ihd;
        return this;
    }

    public void setIhd(Boolean ihd) {
        this.ihd = ihd;
    }

    public Boolean isBa() {
        return ba;
    }

    public MedicalHistory ba(Boolean ba) {
        this.ba = ba;
        return this;
    }

    public void setBa(Boolean ba) {
        this.ba = ba;
    }

    public Boolean isCkd() {
        return ckd;
    }

    public MedicalHistory ckd(Boolean ckd) {
        this.ckd = ckd;
        return this;
    }

    public void setCkd(Boolean ckd) {
        this.ckd = ckd;
    }

    public Boolean isEpilepsy() {
        return epilepsy;
    }

    public MedicalHistory epilepsy(Boolean epilepsy) {
        this.epilepsy = epilepsy;
        return this;
    }

    public void setEpilepsy(Boolean epilepsy) {
        this.epilepsy = epilepsy;
    }

    public String getOtherPast() {
        return otherPast;
    }

    public MedicalHistory otherPast(String otherPast) {
        this.otherPast = otherPast;
        return this;
    }

    public void setOtherPast(String otherPast) {
        this.otherPast = otherPast;
    }

    public String getPastSurgical() {
        return pastSurgical;
    }

    public MedicalHistory pastSurgical(String pastSurgical) {
        this.pastSurgical = pastSurgical;
        return this;
    }

    public void setPastSurgical(String pastSurgical) {
        this.pastSurgical = pastSurgical;
    }

    public String getPastAllergy() {
        return pastAllergy;
    }

    public MedicalHistory pastAllergy(String pastAllergy) {
        this.pastAllergy = pastAllergy;
        return this;
    }

    public void setPastAllergy(String pastAllergy) {
        this.pastAllergy = pastAllergy;
    }

    public String getPastDrug() {
        return pastDrug;
    }

    public MedicalHistory pastDrug(String pastDrug) {
        this.pastDrug = pastDrug;
        return this;
    }

    public void setPastDrug(String pastDrug) {
        this.pastDrug = pastDrug;
    }

    public String getPastFamily() {
        return pastFamily;
    }

    public MedicalHistory pastFamily(String pastFamily) {
        this.pastFamily = pastFamily;
        return this;
    }

    public void setPastFamily(String pastFamily) {
        this.pastFamily = pastFamily;
    }

    public Boolean isSmoke() {
        return smoke;
    }

    public MedicalHistory smoke(Boolean smoke) {
        this.smoke = smoke;
        return this;
    }

    public void setSmoke(Boolean smoke) {
        this.smoke = smoke;
    }

    public Boolean isAlchohol() {
        return alchohol;
    }

    public MedicalHistory alchohol(Boolean alchohol) {
        this.alchohol = alchohol;
        return this;
    }

    public void setAlchohol(Boolean alchohol) {
        this.alchohol = alchohol;
    }

    public String getOtherHistory() {
        return otherHistory;
    }

    public MedicalHistory otherHistory(String otherHistory) {
        this.otherHistory = otherHistory;
        return this;
    }

    public void setOtherHistory(String otherHistory) {
        this.otherHistory = otherHistory;
    }

    public User getUser() {
        return user;
    }

    public MedicalHistory user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MedicalHistory medicalHistory = (MedicalHistory) o;
        if (medicalHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medicalHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
            "id=" + id +
            ", diabates='" + diabates + "'" +
            ", hypertension='" + hypertension + "'" +
            ", ihd='" + ihd + "'" +
            ", ba='" + ba + "'" +
            ", ckd='" + ckd + "'" +
            ", epilepsy='" + epilepsy + "'" +
            ", otherPast='" + otherPast + "'" +
            ", pastSurgical='" + pastSurgical + "'" +
            ", pastAllergy='" + pastAllergy + "'" +
            ", pastDrug='" + pastDrug + "'" +
            ", pastFamily='" + pastFamily + "'" +
            ", smoke='" + smoke + "'" +
            ", alchohol='" + alchohol + "'" +
            ", otherHistory='" + otherHistory + "'" +
            '}';
    }
}
