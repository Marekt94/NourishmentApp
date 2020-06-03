/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

/**
 *
 * @author Marek
 */

@Entity
@Table(name = "POTRAWY_W_DNIU")
@FilterDefs({
    @FilterDef(name = "dataFilterBoth", parameters = {@ParamDef(name = "dataFrom", type="string"), @ParamDef(name = "dataTo", type="string")}),
    @FilterDef(name = "dataFilterFrom", parameters = {@ParamDef(name = "dataFrom", type="string")}),
    @FilterDef(name = "dataFilterTo", parameters = {@ParamDef(name = "dataTo", type="string")})
})
@Filters({
    @Filter(name="dataFilterBoth", condition = "data BETWEEN :dataFrom AND :dataTo"),
    @Filter(name="dataFilterFrom", condition = "data >= :dataFrom"),
    @Filter(name="dataFilterTo", condition = "data <= :dataTo"),
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PotrawyWDniu.findAll", query = "SELECT p FROM PotrawyWDniu p"),
    @NamedQuery(name = "PotrawyWDniu.findById", query = "SELECT p FROM PotrawyWDniu p WHERE p.id = :id"),
    @NamedQuery(name = "PotrawyWDniu.findByData", query = "SELECT p FROM PotrawyWDniu p WHERE p.data = :data"),
    @NamedQuery(name = "PotrawyWDniu.findByCzy5dni", query = "SELECT p FROM PotrawyWDniu p WHERE p.czy5dni = :czy5dni"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikSniadanie", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikSniadanie = :mnoznikSniadanie"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikDrugieSniadanie", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikDrugieSniadanie = :mnoznikDrugieSniadanie"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikObiad", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikObiad = :mnoznikObiad"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikPodwieczorek", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikPodwieczorek = :mnoznikPodwieczorek"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikKolacja", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikKolacja = :mnoznikKolacja"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikLunch", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikLunch = :mnoznikLunch")})
public class PotrawyWDniu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "CZY_5DNI")
    private Character czy5dni;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MNOZNIK_SNIADANIE")
    private Double mnoznikSniadanie;
    @Column(name = "MNOZNIK_DRUGIE_SNIADANIE")
    private Double mnoznikDrugieSniadanie;
    @Column(name = "MNOZNIK_OBIAD")
    private Double mnoznikObiad;
    @Column(name = "MNOZNIK_PODWIECZOREK")
    private Double mnoznikPodwieczorek;
    @Column(name = "MNOZNIK_KOLACJA")
    private Double mnoznikKolacja;
    @Column(name = "MNOZNIK_LUNCH")
    private Double mnoznikLunch;
    @JoinColumn(name = "SNIADANIE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Potrawy sniadanie;
    @JoinColumn(name = "DRUGIE_SNIADANIE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Potrawy drugieSniadanie;
    @JoinColumn(name = "OBIAD", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Potrawy obiad;
    @JoinColumn(name = "LUNCH", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Potrawy lunch;
    @JoinColumn(name = "PODWIECZOREK", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Potrawy podwieczorek;
    @JoinColumn(name = "KOLACJA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Potrawy kolacja;
    @Column(name = "SUMA_KCAL")
    private Double sumaKcal;
    @Column(name = "SUMA_BIALKO")
    private Double sumaBialko;
    @Column(name = "SUMA_CUKRY_PROSTE")
    private Double sumaCukryProste;
    @Column(name = "SUMA_CUKRY_SUMA")
    private Double sumaCukrySuma;
    @Column(name = "SUMA_CUKRY_ZLOZONE")
    private Double sumaCukryZlozone;
    @Column(name = "SUMA_TLUSZCZ")
    private Double sumaTluszcz;
    @Column(name = "SUMA_BLONNIK")
    private Double sumaBlonnik;
    @Column(name = "SUMA_SOL")
    private Double sumaSol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dzien", fetch = FetchType.LAZY)
    private List<ProduktyLuzneWDniu> produktyLuzneWDniu;

    public List<ProduktyLuzneWDniu> getProduktyLuzneWDniu() {
        return produktyLuzneWDniu;
    }

    public void setProduktyLuzneWDniu(List<ProduktyLuzneWDniu> produktyLuzneWDniu) {
        this.produktyLuzneWDniu = produktyLuzneWDniu;
    }

    public PotrawyWDniu() {
    }

    public PotrawyWDniu(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Character getCzy5dni() {
        return czy5dni;
    }

    public void setCzy5dni(Character czy5dni) {
        this.czy5dni = czy5dni;
    }

    public Double getMnoznikSniadanie() {
        return mnoznikSniadanie;
    }

    public void setMnoznikSniadanie(Double mnoznikSniadanie) {
        this.mnoznikSniadanie = mnoznikSniadanie;
    }

    public Double getMnoznikDrugieSniadanie() {
        return mnoznikDrugieSniadanie;
    }

    public void setMnoznikDrugieSniadanie(Double mnoznikDrugieSniadanie) {
        this.mnoznikDrugieSniadanie = mnoznikDrugieSniadanie;
    }

    public Double getMnoznikObiad() {
        return mnoznikObiad;
    }

    public void setMnoznikObiad(Double mnoznikObiad) {
        this.mnoznikObiad = mnoznikObiad;
    }

    public Double getMnoznikPodwieczorek() {
        return mnoznikPodwieczorek;
    }

    public void setMnoznikPodwieczorek(Double mnoznikPodwieczorek) {
        this.mnoznikPodwieczorek = mnoznikPodwieczorek;
    }

    public Double getMnoznikKolacja() {
        return mnoznikKolacja;
    }

    public void setMnoznikKolacja(Double mnoznikKolacja) {
        this.mnoznikKolacja = mnoznikKolacja;
    }

    public Double getMnoznikLunch() {
        return mnoznikLunch;
    }

    public void setMnoznikLunch(Double mnoznikLunch) {
        this.mnoznikLunch = mnoznikLunch;
    }

    public Potrawy getObiad() {
        return obiad;
    }

    public void setObiad(Potrawy obiad) {
        this.obiad = obiad;
    }

    public Potrawy getSniadanie() {
        return sniadanie;
    }

    public void setSniadanie(Potrawy sniadanie) {
        this.sniadanie = sniadanie;
    }

    public Potrawy getKolacja() {
        return kolacja;
    }

    public void setKolacja(Potrawy kolacja) {
        this.kolacja = kolacja;
    }

    public Potrawy getPodwieczorek() {
        return podwieczorek;
    }

    public void setPodwieczorek(Potrawy podwieczorek) {
        this.podwieczorek = podwieczorek;
    }

    public Potrawy getDrugieSniadanie() {
        return drugieSniadanie;
    }

    public void setDrugieSniadanie(Potrawy drugieSniadanie) {
        this.drugieSniadanie = drugieSniadanie;
    }

    public Potrawy getLunch() {
        return lunch;
    }

    public void setLunch(Potrawy lunch) {
        this.lunch = lunch;
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
        if (!(object instanceof PotrawyWDniu)) {
            return false;
        }
        PotrawyWDniu other = (PotrawyWDniu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.PotrawyWDniu[ id=" + id + " ]";
    }

    public Double getSumaKcal() {
        return sumaKcal;
    }

    public void setSumaKcal(Double sumaKcal) {
        this.sumaKcal = sumaKcal;
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

    public Double getSumaCukrySuma() {
        return sumaCukrySuma;
    }

    public void setSumaCukrySuma(Double sumaCukrySuma) {
        this.sumaCukrySuma = sumaCukrySuma;
    }

    public Double getSumaCukryZlozone() {
        return sumaCukryZlozone;
    }

    public void setSumaCukryZlozone(Double sumaCukryZlozone) {
        this.sumaCukryZlozone = sumaCukryZlozone;
    }

    public Double getSumaTluszcz() {
        return sumaTluszcz;
    }

    public void setSumaTluszcz(Double sumaTluszcz) {
        this.sumaTluszcz = sumaTluszcz;
    }

    public Double getSumaBlonnik() {
        return sumaBlonnik;
    }

    public void setSumaBlonnik(Double sumaBlonnik) {
        this.sumaBlonnik = sumaBlonnik;
    }

    public Double getSumaSol() {
        return sumaSol;
    }

    public void setSumaSol(Double sumaSol) {
        this.sumaSol = sumaSol;
    }
    
}
