package com.mycompany.cpms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.mycompany.cpms.domain.enumeration.GenderEnum;

/**
 * A Userinfo.
 */
@Entity
@Table(name = "userinfo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Userinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 15)
    @Column(name = "reg_no", length = 15, nullable = false)
    private String regNo;

    @Column(name = "dob")
    private LocalDate dob;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @NotNull
    @Size(min = 10, max = 12)
    @Column(name = "telephone", length = 12, nullable = false)
    private String telephone;

    @Column(name = "mobile")
    private String mobile;

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

    public String getRegNo() {
        return regNo;
    }

    public Userinfo regNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Userinfo dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public Userinfo address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public Userinfo gender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public Userinfo telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public Userinfo mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getUser() {
        return user;
    }

    public Userinfo user(User user) {
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
        Userinfo userinfo = (Userinfo) o;
        if (userinfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userinfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Userinfo{" +
            "id=" + id +
            ", regNo='" + regNo + "'" +
            ", dob='" + dob + "'" +
            ", address='" + address + "'" +
            ", gender='" + gender + "'" +
            ", telephone='" + telephone + "'" +
            ", mobile='" + mobile + "'" +
            '}';
    }
}
