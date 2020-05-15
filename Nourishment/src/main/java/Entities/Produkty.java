/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marek
 */
@Entity
@Table(name = "PRODUKTY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produkty.findAll", query = "SELECT p FROM Produkty p"),
    @NamedQuery(name = "Produkty.findById", query = "SELECT p FROM Produkty p WHERE p.id = :id"),
    @NamedQuery(name = "Produkty.findByNazwa", query = "SELECT p FROM Produkty p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Produkty.findByKcalNa100g", query = "SELECT p FROM Produkty p WHERE p.kcalNa100g = :kcalNa100g"),
    @NamedQuery(name = "Produkty.findByWagaJednostki", query = "SELECT p FROM Produkty p WHERE p.wagaJednostki = :wagaJednostki"),
    @NamedQuery(name = "Produkty.findByBialko", query = "SELECT p FROM Produkty p WHERE p.bialko = :bialko"),
    @NamedQuery(name = "Produkty.findByTluszcz", query = "SELECT p FROM Produkty p WHERE p.tluszcz = :tluszcz"),
    @NamedQuery(name = "Produkty.findByCukryProste", query = "SELECT p FROM Produkty p WHERE p.cukryProste = :cukryProste"),
    @NamedQuery(name = "Produkty.findByCukryZlozone", query = "SELECT p FROM Produkty p WHERE p.cukryZlozone = :cukryZlozone"),
    @NamedQuery(name = "Produkty.findByCukrySuma", query = "SELECT p FROM Produkty p WHERE p.cukrySuma = :cukrySuma"),
    @NamedQuery(name = "Produkty.findByBlonnik", query = "SELECT p FROM Produkty p WHERE p.blonnik = :blonnik"),
    @NamedQuery(name = "Produkty.findBySol", query = "SELECT p FROM Produkty p WHERE p.sol = :sol")})
public class Produkty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAZWA")
    private String nazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "KCAL_NA_100G")
    private Double kcalNa100g;
    @Column(name = "WAGA_JEDNOSTKI")
    private Double wagaJednostki;
    @Column(name = "BIALKO")
    private Double bialko;
    @Column(name = "TLUSZCZ")
    private Double tluszcz;
    @Column(name = "CUKRY_PROSTE")
    private Double cukryProste;
    @Column(name = "CUKRY_ZLOZONE")
    private Double cukryZlozone;
    @Column(name = "CUKRY_SUMA")
    private Double cukrySuma;
    @Column(name = "BLONNIK")
    private Double blonnik;
    @Column(name = "SOL")
    private Double sol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduktu")
    private Collection<ProduktyWPotrawie> produktyWPotrawieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produkt")
    private Collection<ProduktyLuzneWDniu> produktyLuzneWDniu;

    public Produkty() {
    }

    public Produkty(Integer id) {
        this.id = id;
    }

    public Produkty(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getKcalNa100g() {
        return kcalNa100g;
    }

    public void setKcalNa100g(Double kcalNa100g) {
        this.kcalNa100g = kcalNa100g;
    }

    public Double getWagaJednostki() {
        return wagaJednostki;
    }

    public void setWagaJednostki(Double wagaJednostki) {
        this.wagaJednostki = wagaJednostki;
    }

    public Double getBialko() {
        return bialko;
    }

    public void setBialko(Double bialko) {
        this.bialko = bialko;
    }

    public Double getTluszcz() {
        return tluszcz;
    }

    public void setTluszcz(Double tluszcz) {
        this.tluszcz = tluszcz;
    }

    public Double getCukryProste() {
        return cukryProste;
    }

    public void setCukryProste(Double cukryProste) {
        this.cukryProste = cukryProste;
    }

    public Double getCukryZlozone() {
        return cukryZlozone;
    }

    public void setCukryZlozone(Double cukryZlozone) {
        this.cukryZlozone = cukryZlozone;
    }

    public Double getCukrySuma() {
        return cukrySuma;
    }

    public void setCukrySuma(Double cukrySuma) {
        this.cukrySuma = cukrySuma;
    }

    public Double getBlonnik() {
        return blonnik;
    }

    public void setBlonnik(Double blonnik) {
        this.blonnik = blonnik;
    }

    public Double getSol() {
        return sol;
    }

    public void setSol(Double sol) {
        this.sol = sol;
    }

    @XmlTransient
    public Collection<ProduktyWPotrawie> getProduktyWPotrawieCollection() {
        return produktyWPotrawieCollection;
    }

    public void setProduktyWPotrawieCollection(Collection<ProduktyWPotrawie> produktyWPotrawieCollection) {
        this.produktyWPotrawieCollection = produktyWPotrawieCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produkty)) {
            return false;
        }
        Produkty other = (Produkty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazwa;
    }
    
}
