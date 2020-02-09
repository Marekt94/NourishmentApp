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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marek
 */
@Entity
@Table(name = "PRODUKTY_W_POTRAWIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProduktyWPotrawie.findAll", query = "SELECT p FROM ProduktyWPotrawie p"),
    @NamedQuery(name = "ProduktyWPotrawie.findById", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.id = :id"),
    @NamedQuery(name = "ProduktyWPotrawie.findByIloscWG", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.iloscWG = :iloscWG"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaBlonnik", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaBlonnik = :sumaBlonnik"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaKcal", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaKcal = :sumaKcal"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaSol", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaSol = :sumaSol"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaTluszcz", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaTluszcz = :sumaTluszcz"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaBialko", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaBialko = :sumaBialko"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaCukryProste", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaCukryProste = :sumaCukryProste"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaCukryZlozone", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaCukryZlozone = :sumaCukryZlozone"),
    @NamedQuery(name = "ProduktyWPotrawie.findBySumaCukrySuma", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.sumaCukrySuma = :sumaCukrySuma")})
public class ProduktyWPotrawie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ID_PRODUKTU", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Produkty idProduktu;
    @Basic(optional = false)
    @Column(name = "ILOSC_W_G")
    private double iloscWG;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUMA_BLONNIK")
    private Double sumaBlonnik;
    @Column(name = "SUMA_KCAL")
    private Double sumaKcal;
    @Column(name = "SUMA_SOL")
    private Double sumaSol;
    @Column(name = "SUMA_TLUSZCZ")
    private Double sumaTluszcz;
    @Column(name = "SUMA_BIALKO")
    private Double sumaBialko;
    @Column(name = "SUMA_CUKRY_PROSTE")
    private Double sumaCukryProste;
    @Column(name = "SUMA_CUKRY_ZLOZONE")
    private Double sumaCukryZlozone;
    @Column(name = "SUMA_CUKRY_SUMA")
    private Double sumaCukrySuma;
    @JoinColumn(name = "ID_POTRAWY", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Potrawy idPotrawy;

    public ProduktyWPotrawie() {
    }

    public ProduktyWPotrawie(Integer id) {
        this.id = id;
    }

    public ProduktyWPotrawie(Integer id, double iloscWG) {
        this.id = id;
        this.iloscWG = iloscWG;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getIloscWG() {
        return iloscWG;
    }

    public void setIloscWG(double iloscWG) {
        this.iloscWG = iloscWG;
    }

    public Double getSumaBlonnik() {
        return sumaBlonnik;
    }

    public void setSumaBlonnik(Double sumaBlonnik) {
        this.sumaBlonnik = sumaBlonnik;
    }

    public Double getSumaKcal() {
        return sumaKcal;
    }

    public void setSumaKcal(Double sumaKcal) {
        this.sumaKcal = sumaKcal;
    }

    public Double getSumaSol() {
        return sumaSol;
    }

    public void setSumaSol(Double sumaSol) {
        this.sumaSol = sumaSol;
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

    public Double getSumaCukryProste() {
        return sumaCukryProste;
    }

    public void setSumaCukryProste(Double sumaCukryProste) {
        this.sumaCukryProste = sumaCukryProste;
    }

    public Double getSumaCukryZlozone() {
        return sumaCukryZlozone;
    }

    public void setSumaCukryZlozone(Double sumaCukryZlozone) {
        this.sumaCukryZlozone = sumaCukryZlozone;
    }

    public Double getSumaCukrySuma() {
        return sumaCukrySuma;
    }

    public void setSumaCukrySuma(Double sumaCukrySuma) {
        this.sumaCukrySuma = sumaCukrySuma;
    }

    public Potrawy getIdPotrawy() {
        return idPotrawy;
    }

    public void setIdPotrawy(Potrawy idPotrawy) {
        this.idPotrawy = idPotrawy;
    }

    public Produkty getIdProduktu() {
        return idProduktu;
    }

    public void setIdProduktu(Produkty idProduktu) {
        this.idProduktu = idProduktu;
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
        if (!(object instanceof ProduktyWPotrawie)) {
            return false;
        }
        ProduktyWPotrawie other = (ProduktyWPotrawie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ProduktyWPotrawie[ id=" + id + " ]";
    }
    
}
