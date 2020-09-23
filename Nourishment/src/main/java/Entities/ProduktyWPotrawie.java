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
    private Double sumaBlonnik;
    private Double sumaKcal;
    private Double sumaSol;
    private Double sumaTluszcz;
    private Double sumaBialko;
    private Double sumaCukryProste;
    private Double sumaCukryZlozone;
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
        if (idProduktu != null){
            sumaBlonnik = (idProduktu.getBlonnik() * iloscWG) / 100.0;
        }
        else
            sumaBlonnik = 0.0;
        return sumaBlonnik;
    }

    public Double getSumaKcal() {
        if (idProduktu != null){
            sumaKcal = (idProduktu.getKcalNa100g() * iloscWG) / 100.0;
        }
        else
            sumaKcal = 0.0;
        return sumaKcal;
    }

    public Double getSumaSol() {
        if (idProduktu != null){
            sumaSol = (idProduktu.getSol() * iloscWG) / 100.0;
        }
        else
            sumaSol = 0.0;
        return sumaSol;
    }

    public Double getSumaTluszcz() {
        if (idProduktu != null){
            sumaTluszcz = (idProduktu.getTluszcz() * iloscWG) / 100.0;
        }
        else
            sumaTluszcz = 0.0;
        return sumaTluszcz;        
    }

    public Double getSumaBialko() {
        if (idProduktu != null){
            sumaBialko = (idProduktu.getBialko()* iloscWG) / 100.0;
        }
        else
            sumaBialko = 0.0;
        return sumaBialko;
    }

    public Double getSumaCukryProste() {
        if (idProduktu != null){
            sumaCukryProste = (idProduktu.getCukryProste() * iloscWG) / 100.0;
        }
        else
            sumaCukryProste = 0.0;
        return sumaCukryProste;
    }

    public Double getSumaCukryZlozone() {
        if (idProduktu != null){
            sumaCukryZlozone = (idProduktu.getCukryZlozone() * iloscWG) / 100.0;
        }
        else
            sumaCukryZlozone = 0.0;
        return sumaCukryZlozone;
    }

    public Double getSumaCukrySuma() {
        if (idProduktu != null){
            sumaCukrySuma = (idProduktu.getCukrySuma() * iloscWG) / 100.0;
        }
        else
            sumaCukrySuma = 0.0;
        return sumaCukrySuma;
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
