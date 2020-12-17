/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marek
 */
@Entity
@Table(name = "POTRAWY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Potrawy.findAll", query = "SELECT p FROM Potrawy p"),
    @NamedQuery(name = "Potrawy.findById", query = "SELECT p FROM Potrawy p WHERE p.id = :id"),
    @NamedQuery(name = "Potrawy.findByNazwa", query = "SELECT p FROM Potrawy p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Potrawy.findByPrzepis", query = "SELECT p FROM Potrawy p WHERE p.przepis = :przepis")})
public class Potrawy implements Serializable {

    @Column(name = "PRZEPIS")
    private String przepis;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Transient
    private Double sumaKcal = 0.0;
    @Transient
    private Double sumaTluszcz = 0.0;
    @Transient
    private Double sumaBialko = 0.0;
    @Basic(optional = false)
    @Column(name = "NAZWA")
    private String nazwa;
    @Transient
    private Double sumaCukryZlozone = 0.0;
    @Transient
    private Double sumaSol = 0.0;
    @Transient
    private Double sumaBlonnik = 0.0;
    @Transient
    private Double sumaCukrySuma = 0.0;
    @Transient
    private Double sumaCukryProste = 0.0;
    @Transient
    private Double waga;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPotrawy", fetch = FetchType.EAGER)
    private List<ProduktyWPotrawie> produktyWPotrawieCollection;
    @OneToMany(mappedBy = "obiad", fetch = FetchType.LAZY)
    private Collection<PotrawyWDniu> potrawyWDniuCollection;
    @OneToMany(mappedBy = "sniadanie", fetch = FetchType.LAZY)
    private Collection<PotrawyWDniu> potrawyWDniuCollection1;
    @OneToMany(mappedBy = "kolacja", fetch = FetchType.LAZY)
    private Collection<PotrawyWDniu> potrawyWDniuCollection2;
    @OneToMany(mappedBy = "podwieczorek", fetch = FetchType.LAZY)
    private Collection<PotrawyWDniu> potrawyWDniuCollection3;
    @OneToMany(mappedBy = "drugieSniadanie", fetch = FetchType.LAZY)
    private Collection<PotrawyWDniu> potrawyWDniuCollection4;
    @OneToMany(mappedBy = "lunch", fetch = FetchType.LAZY)
    private Collection<PotrawyWDniu> potrawyWDniuCollection5;

    public Potrawy() {
    }

    public Potrawy(Integer id) {
        this.id = id;
    }

    public Potrawy(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSumaKcal() {
        sumaKcal = 0.0;
        if (getProduktyWPotrawieCollection() != null){
            for (ProduktyWPotrawie produkt : getProduktyWPotrawieCollection()) {
                    sumaKcal += produkt.getSumaKcal();
            }
        }
        return sumaKcal;
    }

    public Double getSumaTluszcz() {
        sumaTluszcz = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaTluszcz += produkt.getSumaTluszcz();
        }
        return sumaTluszcz;
    }

    public Double getSumaBialko() {
        sumaBialko = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaBialko += produkt.getSumaBialko();
        }
        return sumaBialko;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getSumaCukryZlozone() {
        sumaCukryZlozone = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaCukryZlozone += produkt.getSumaCukryZlozone();
        }
        return sumaCukryZlozone;
    }

    public Double getSumaSol() {
        sumaSol = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaSol += produkt.getSumaSol();
        }
        return sumaSol;
    }

    public Double getSumaBlonnik() {
        sumaBlonnik = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaBlonnik += produkt.getSumaBlonnik();
        }
        return sumaBlonnik;
    }

    public Double getSumaCukrySuma() {
        sumaCukrySuma = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaCukrySuma += produkt.getSumaCukrySuma();
        }
        return sumaCukrySuma;
    }

    public Double getSumaCukryProste() {
        sumaCukryProste = 0.0;
        for (ProduktyWPotrawie produkt : produktyWPotrawieCollection){
            sumaCukryProste += produkt.getSumaCukryProste();
        }
        return sumaCukryProste;
    }

    public Double getWaga() {
        return waga;
    }

    public void setWaga(Double waga) {
        this.waga = waga;
    }

    @XmlTransient
    public List<ProduktyWPotrawie> getProduktyWPotrawieCollection() {
        Set<ProduktyWPotrawie> setForAvoidDuplicates = new HashSet<ProduktyWPotrawie> (produktyWPotrawieCollection);
        produktyWPotrawieCollection = new ArrayList<ProduktyWPotrawie> (setForAvoidDuplicates);
        return produktyWPotrawieCollection;
    }

    public void setProduktyWPotrawieCollection(List<ProduktyWPotrawie> produktyWPotrawieCollection) {
        this.produktyWPotrawieCollection = produktyWPotrawieCollection;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection() {
        return potrawyWDniuCollection;
    }

    public void setPotrawyWDniuCollection(Collection<PotrawyWDniu> potrawyWDniuCollection) {
        this.potrawyWDniuCollection = potrawyWDniuCollection;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection1() {
        return potrawyWDniuCollection1;
    }

    public void setPotrawyWDniuCollection1(Collection<PotrawyWDniu> potrawyWDniuCollection1) {
        this.potrawyWDniuCollection1 = potrawyWDniuCollection1;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection2() {
        return potrawyWDniuCollection2;
    }

    public void setPotrawyWDniuCollection2(Collection<PotrawyWDniu> potrawyWDniuCollection2) {
        this.potrawyWDniuCollection2 = potrawyWDniuCollection2;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection3() {
        return potrawyWDniuCollection3;
    }

    public void setPotrawyWDniuCollection3(Collection<PotrawyWDniu> potrawyWDniuCollection3) {
        this.potrawyWDniuCollection3 = potrawyWDniuCollection3;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection4() {
        return potrawyWDniuCollection4;
    }

    public void setPotrawyWDniuCollection4(Collection<PotrawyWDniu> potrawyWDniuCollection4) {
        this.potrawyWDniuCollection4 = potrawyWDniuCollection4;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection5() {
        return potrawyWDniuCollection5;
    }

    public void setPotrawyWDniuCollection5(Collection<PotrawyWDniu> potrawyWDniuCollection5) {
        this.potrawyWDniuCollection5 = potrawyWDniuCollection5;
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
        if (!(object instanceof Potrawy)) {
            return false;
        }
        Potrawy other = (Potrawy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazwa;
    }

    public String getPrzepis() {
        return przepis;
    }

    public void setPrzepis(String przepis) {
        this.przepis = przepis;
    }
    
}
