/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "Potrawy.findBySumaKcal", query = "SELECT p FROM Potrawy p WHERE p.sumaKcal = :sumaKcal"),
    @NamedQuery(name = "Potrawy.findBySumaTluszcz", query = "SELECT p FROM Potrawy p WHERE p.sumaTluszcz = :sumaTluszcz"),
    @NamedQuery(name = "Potrawy.findBySumaBialko", query = "SELECT p FROM Potrawy p WHERE p.sumaBialko = :sumaBialko"),
    @NamedQuery(name = "Potrawy.findByNazwa", query = "SELECT p FROM Potrawy p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Potrawy.findBySumaCukryZlozone", query = "SELECT p FROM Potrawy p WHERE p.sumaCukryZlozone = :sumaCukryZlozone"),
    @NamedQuery(name = "Potrawy.findBySumaSol", query = "SELECT p FROM Potrawy p WHERE p.sumaSol = :sumaSol"),
    @NamedQuery(name = "Potrawy.findBySumaBlonnik", query = "SELECT p FROM Potrawy p WHERE p.sumaBlonnik = :sumaBlonnik"),
    @NamedQuery(name = "Potrawy.findBySumaCukrySuma", query = "SELECT p FROM Potrawy p WHERE p.sumaCukrySuma = :sumaCukrySuma"),
    @NamedQuery(name = "Potrawy.findBySumaCukryProste", query = "SELECT p FROM Potrawy p WHERE p.sumaCukryProste = :sumaCukryProste"),
    @NamedQuery(name = "Potrawy.findByWaga", query = "SELECT p FROM Potrawy p WHERE p.waga = :waga")})
public class Potrawy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUMA_KCAL")
    private Double sumaKcal;
    @Column(name = "SUMA_TLUSZCZ")
    private Double sumaTluszcz;
    @Column(name = "SUMA_BIALKO")
    private Double sumaBialko;
    @Basic(optional = false)
    @Column(name = "NAZWA")
    private String nazwa;
    @Column(name = "SUMA_CUKRY_ZLOZONE")
    private Double sumaCukryZlozone;
    @Column(name = "SUMA_SOL")
    private Double sumaSol;
    @Column(name = "SUMA_BLONNIK")
    private Double sumaBlonnik;
    @Column(name = "SUMA_CUKRY_SUMA")
    private Double sumaCukrySuma;
    @Column(name = "SUMA_CUKRY_PROSTE")
    private Double sumaCukryProste;
    @Column(name = "WAGA")
    private Double waga;

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
        return sumaKcal;
    }

    public void setSumaKcal(Double sumaKcal) {
        this.sumaKcal = sumaKcal;
    }

    public Double getSumaTluszcz() {
        return sumaTluszcz;
    }

    public void setSumaTluszcz(Double sumaTluszcz) {
        this.sumaTluszcz = sumaTluszcz;
    }

    public Double getSumaBialko() {
        return sumaBialko;
    }

    public void setSumaBialko(Double sumaBialko) {
        this.sumaBialko = sumaBialko;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getSumaCukryZlozone() {
        return sumaCukryZlozone;
    }

    public void setSumaCukryZlozone(Double sumaCukryZlozone) {
        this.sumaCukryZlozone = sumaCukryZlozone;
    }

    public Double getSumaSol() {
        return sumaSol;
    }

    public void setSumaSol(Double sumaSol) {
        this.sumaSol = sumaSol;
    }

    public Double getSumaBlonnik() {
        return sumaBlonnik;
    }

    public void setSumaBlonnik(Double sumaBlonnik) {
        this.sumaBlonnik = sumaBlonnik;
    }

    public Double getSumaCukrySuma() {
        return sumaCukrySuma;
    }

    public void setSumaCukrySuma(Double sumaCukrySuma) {
        this.sumaCukrySuma = sumaCukrySuma;
    }

    public Double getSumaCukryProste() {
        return sumaCukryProste;
    }

    public void setSumaCukryProste(Double sumaCukryProste) {
        this.sumaCukryProste = sumaCukryProste;
    }

    public Double getWaga() {
        return waga;
    }

    public void setWaga(Double waga) {
        this.waga = waga;
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
        return "Entities.Potrawy[ id=" + id + " ]";
    }
    
}
