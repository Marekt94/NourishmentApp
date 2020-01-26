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
    @NamedQuery(name = "ProduktyWPotrawie.findByPpIloscWG", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppIloscWG = :ppIloscWG"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaBlonnik", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaBlonnik = :ppSumaBlonnik"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaKcal", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaKcal = :ppSumaKcal"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaSol", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaSol = :ppSumaSol"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaTluszcz", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaTluszcz = :ppSumaTluszcz"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpWaga", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppWaga = :ppWaga"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaBialko", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaBialko = :ppSumaBialko"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaCukryProste", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaCukryProste = :ppSumaCukryProste"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaCukryZlozone", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaCukryZlozone = :ppSumaCukryZlozone"),
    @NamedQuery(name = "ProduktyWPotrawie.findByPpSumaCukrySuma", query = "SELECT p FROM ProduktyWPotrawie p WHERE p.ppSumaCukrySuma = :ppSumaCukrySuma")})
public class ProduktyWPotrawie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "PP_ILOSC_W_G")
    private double ppIloscWG;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PP_SUMA_BLONNIK")
    private Double ppSumaBlonnik;
    @Column(name = "PP_SUMA_KCAL")
    private Double ppSumaKcal;
    @Column(name = "PP_SUMA_SOL")
    private Double ppSumaSol;
    @Column(name = "PP_SUMA_TLUSZCZ")
    private Double ppSumaTluszcz;
    @Column(name = "PP_WAGA")
    private Double ppWaga;
    @Column(name = "PP_SUMA_BIALKO")
    private Double ppSumaBialko;
    @Column(name = "PP_SUMA_CUKRY_PROSTE")
    private Double ppSumaCukryProste;
    @Column(name = "PP_SUMA_CUKRY_ZLOZONE")
    private Double ppSumaCukryZlozone;
    @Column(name = "PP_SUMA_CUKRY_SUMA")
    private Double ppSumaCukrySuma;
    @JoinColumn(name = "PP_ID_POTRAWY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Potrawy ppIdPotrawy;
    @JoinColumn(name = "PP_ID_PRODUKTU", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Produkty ppIdProduktu;

    public ProduktyWPotrawie() {
    }

    public ProduktyWPotrawie(Integer id) {
        this.id = id;
    }

    public ProduktyWPotrawie(Integer id, double ppIloscWG) {
        this.id = id;
        this.ppIloscWG = ppIloscWG;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPpIloscWG() {
        return ppIloscWG;
    }

    public void setPpIloscWG(double ppIloscWG) {
        this.ppIloscWG = ppIloscWG;
    }

    public Double getPpSumaBlonnik() {
        return ppSumaBlonnik;
    }

    public void setPpSumaBlonnik(Double ppSumaBlonnik) {
        this.ppSumaBlonnik = ppSumaBlonnik;
    }

    public Double getPpSumaKcal() {
        return ppSumaKcal;
    }

    public void setPpSumaKcal(Double ppSumaKcal) {
        this.ppSumaKcal = ppSumaKcal;
    }

    public Double getPpSumaSol() {
        return ppSumaSol;
    }

    public void setPpSumaSol(Double ppSumaSol) {
        this.ppSumaSol = ppSumaSol;
    }

    public Double getPpSumaTluszcz() {
        return ppSumaTluszcz;
    }

    public void setPpSumaTluszcz(Double ppSumaTluszcz) {
        this.ppSumaTluszcz = ppSumaTluszcz;
    }

    public Double getPpWaga() {
        return ppWaga;
    }

    public void setPpWaga(Double ppWaga) {
        this.ppWaga = ppWaga;
    }

    public Double getPpSumaBialko() {
        return ppSumaBialko;
    }

    public void setPpSumaBialko(Double ppSumaBialko) {
        this.ppSumaBialko = ppSumaBialko;
    }

    public Double getPpSumaCukryProste() {
        return ppSumaCukryProste;
    }

    public void setPpSumaCukryProste(Double ppSumaCukryProste) {
        this.ppSumaCukryProste = ppSumaCukryProste;
    }

    public Double getPpSumaCukryZlozone() {
        return ppSumaCukryZlozone;
    }

    public void setPpSumaCukryZlozone(Double ppSumaCukryZlozone) {
        this.ppSumaCukryZlozone = ppSumaCukryZlozone;
    }

    public Double getPpSumaCukrySuma() {
        return ppSumaCukrySuma;
    }

    public void setPpSumaCukrySuma(Double ppSumaCukrySuma) {
        this.ppSumaCukrySuma = ppSumaCukrySuma;
    }

    public Potrawy getPpIdPotrawy() {
        return ppIdPotrawy;
    }

    public void setPpIdPotrawy(Potrawy ppIdPotrawy) {
        this.ppIdPotrawy = ppIdPotrawy;
    }

    public Produkty getPpIdProduktu() {
        return ppIdProduktu;
    }

    public void setPpIdProduktu(Produkty ppIdProduktu) {
        this.ppIdProduktu = ppIdProduktu;
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
