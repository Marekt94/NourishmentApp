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
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PotrawyWDniu.findAll", query = "SELECT p FROM PotrawyWDniu p"),
    @NamedQuery(name = "PotrawyWDniu.findById", query = "SELECT p FROM PotrawyWDniu p WHERE p.id = :id"),
    @NamedQuery(name = "PotrawyWDniu.findByCzy5dni", query = "SELECT p FROM PotrawyWDniu p WHERE p.czy5dni = :czy5dni"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikSniadanie", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikSniadanie = :mnoznikSniadanie"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikDrugieSniadanie", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikDrugieSniadanie = :mnoznikDrugieSniadanie"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikObiad", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikObiad = :mnoznikObiad"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikPodwieczorek", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikPodwieczorek = :mnoznikPodwieczorek"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikKolacja", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikKolacja = :mnoznikKolacja"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznikLunch", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznikLunch = :mnoznikLunch")})
public class PotrawyWDniu implements Serializable {

    private Double sumabialko = 0.0;
    private Double sumablonnik = 0.0;
    private Double sumacukryproste = 0.0;
    private Double sumacukrysuma = 0.0;
    private Double sumacukryzlozone = 0.0;
    private Double sumakcal = 0.0;
    private Double sumasol = 0.0;
    private Double sumatluszcz = 0.0;
    private String nazwa;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
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
        sumakcal = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumakcal += sniadanie.getSumaKcal();
            }
            if (drugieSniadanie != null) {
                sumakcal += drugieSniadanie.getSumaKcal();
            }
            if (obiad != null) {
                sumakcal += obiad.getSumaKcal();
            }
            if (podwieczorek != null) {
                sumakcal += podwieczorek.getSumaKcal();
            }
            if (kolacja != null) {
                sumakcal += kolacja.getSumaKcal();
            }
        } else {
            if (sniadanie != null) {
                sumakcal += sniadanie.getSumaKcal();
            }
            if (lunch != null) {
                sumakcal += lunch.getSumaKcal();
            }
            if (drugieSniadanie != null) {
                sumakcal += drugieSniadanie.getSumaKcal();
            }
            if (kolacja != null) {
                sumakcal += kolacja.getSumaKcal();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumakcal += produktyLuzneWDniu.get(i).getKcal();
            }
        }
        return sumakcal;
    }

    public Double getSumaBialko() {
        sumabialko = 0.0;
        try{
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumabialko += sniadanie.getSumaBialko();
            }
            if (drugieSniadanie != null) {
                sumabialko += drugieSniadanie.getSumaBialko();
            }
            if (obiad != null) {
                sumabialko += obiad.getSumaBialko();
            }
            if (podwieczorek != null) {
                sumabialko += podwieczorek.getSumaBialko();
            }
            if (kolacja != null) {
                sumabialko += kolacja.getSumaBialko();
            }
        } else {
            if (sniadanie != null) {
                sumabialko += sniadanie.getSumaBialko();
            }
            if (lunch != null) {
                sumabialko += lunch.getSumaBialko();
            }
            if (drugieSniadanie != null) {
                sumabialko += drugieSniadanie.getSumaBialko();
            }
            if (kolacja != null) {
                sumabialko += kolacja.getSumaBialko();
            }
        }
        }
        catch (Exception e){
            System.out.println("Entities.PotrawyWDniu.getSumaBialko()");        
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumabialko += produktyLuzneWDniu.get(i).getBialko();
            }
        }
        return sumabialko;
    }

    public Double getSumaCukryProste() {
        sumacukryproste = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumacukryproste += sniadanie.getSumaCukryProste();
            }
            if (drugieSniadanie != null) {
                sumacukryproste += drugieSniadanie.getSumaCukryProste();
            }
            if (obiad != null) {
                sumacukryproste += obiad.getSumaCukryProste();
            }
            if (podwieczorek != null) {
                sumacukryproste += podwieczorek.getSumaCukryProste();
            }
            if (kolacja != null) {
                sumacukryproste += kolacja.getSumaCukryProste();
            }
        } else {
            if (sniadanie != null) {
                sumacukryproste += sniadanie.getSumaCukryProste();
            }
            if (lunch != null) {
                sumacukryproste += lunch.getSumaCukryProste();
            }
            if (drugieSniadanie != null) {
                sumacukryproste += drugieSniadanie.getSumaCukryProste();
            }
            if (kolacja != null) {
                sumacukryproste += kolacja.getSumaCukryProste();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumacukryproste += produktyLuzneWDniu.get(i).getCukryZlozone();
            }
        }
        return sumacukryproste;
    }

    public Double getSumaCukrySuma() {
        sumacukrysuma = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumacukrysuma += sniadanie.getSumaCukrySuma();
            }
            if (drugieSniadanie != null) {
                sumacukrysuma += drugieSniadanie.getSumaCukrySuma();
            }
            if (obiad != null) {
                sumacukrysuma += obiad.getSumaCukrySuma();
            }
            if (podwieczorek != null) {
                sumacukrysuma += podwieczorek.getSumaCukrySuma();
            }
            if (kolacja != null) {
                sumacukrysuma += kolacja.getSumaCukrySuma();
            }
        } else {
            if (sniadanie != null) {
                sumacukrysuma += sniadanie.getSumaCukrySuma();
            }
            if (lunch != null) {
                sumacukrysuma += lunch.getSumaCukrySuma();
            }
            if (drugieSniadanie != null) {
                sumacukrysuma += drugieSniadanie.getSumaCukrySuma();
            }
            if (kolacja != null) {
                sumacukrysuma += kolacja.getSumaCukrySuma();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumacukrysuma += produktyLuzneWDniu.get(i).getCukrySuma();
            }
        }

        return sumacukrysuma;
    }

    public Double getSumaCukryZlozone() {
        sumacukryzlozone = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumacukryzlozone += sniadanie.getSumaCukryZlozone();
            }
            if (drugieSniadanie != null) {
                sumacukryzlozone += drugieSniadanie.getSumaCukryZlozone();
            }
            if (obiad != null) {
                sumacukryzlozone += obiad.getSumaCukryZlozone();
            }
            if (podwieczorek != null) {
                sumacukryzlozone += podwieczorek.getSumaCukryZlozone();
            }
            if (kolacja != null) {
                sumacukryzlozone += kolacja.getSumaCukryZlozone();
            }
        } else {
            if (sniadanie != null) {
                sumacukryzlozone += sniadanie.getSumaCukryZlozone();
            }
            if (lunch != null) {
                sumacukryzlozone += lunch.getSumaCukryZlozone();
            }
            if (drugieSniadanie != null) {
                sumacukryzlozone += drugieSniadanie.getSumaCukryZlozone();
            }
            if (kolacja != null) {
                sumacukryzlozone += kolacja.getSumaCukryZlozone();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumacukryzlozone += produktyLuzneWDniu.get(i).getCukryZlozone();
            }
        }
        return sumacukryzlozone;
    }

    public Double getSumaTluszcz() {
        sumatluszcz = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumatluszcz += sniadanie.getSumaTluszcz();
            }
            if (drugieSniadanie != null) {
                sumatluszcz += drugieSniadanie.getSumaTluszcz();
            }
            if (obiad != null) {
                sumatluszcz += obiad.getSumaTluszcz();
            }
            if (podwieczorek != null) {
                sumatluszcz += podwieczorek.getSumaTluszcz();
            }
            if (kolacja != null) {
                sumatluszcz += kolacja.getSumaTluszcz();
            }
        } else {
            if (sniadanie != null) {
                sumatluszcz += sniadanie.getSumaTluszcz();
            }
            if (lunch != null) {
                sumatluszcz += lunch.getSumaTluszcz();
            }
            if (drugieSniadanie != null) {
                sumatluszcz += drugieSniadanie.getSumaTluszcz();
            }
            if (kolacja != null) {
                sumatluszcz += kolacja.getSumaTluszcz();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumatluszcz += produktyLuzneWDniu.get(i).getTluszcz();
            }
        }
        return sumatluszcz;
    }

    public Double getSumaBlonnik() {
        sumablonnik = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumablonnik += sniadanie.getSumaBlonnik();
            }
            if (drugieSniadanie != null) {
                sumablonnik += drugieSniadanie.getSumaBlonnik();
            }
            if (obiad != null) {
                sumablonnik += obiad.getSumaBlonnik();
            }
            if (podwieczorek != null) {
                sumablonnik += podwieczorek.getSumaBlonnik();
            }
            if (kolacja != null) {
                sumablonnik += kolacja.getSumaBlonnik();
            }
        } else {
            if (sniadanie != null) {
                sumablonnik += sniadanie.getSumaBlonnik();
            }
            if (lunch != null) {
                sumablonnik += lunch.getSumaBlonnik();
            }
            if (drugieSniadanie != null) {
                sumablonnik += drugieSniadanie.getSumaBlonnik();
            }
            if (kolacja != null) {
                sumablonnik += kolacja.getSumaBlonnik();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumablonnik += produktyLuzneWDniu.get(i).getBlonnik();
            }
        }
        return sumablonnik;
    }

    public Double getSumaSol() {
        sumasol = 0.0;
        if (czy5dni == '1') {
            if (sniadanie != null) {
                sumasol += sniadanie.getSumaSol();
            }
            if (drugieSniadanie != null) {
                sumasol += drugieSniadanie.getSumaSol();
            }
            if (obiad != null) {
                sumasol += obiad.getSumaSol();
            }
            if (podwieczorek != null) {
                sumasol += podwieczorek.getSumaSol();
            }
            if (kolacja != null) {
                sumasol += kolacja.getSumaSol();
            }
        } else {
            if (sniadanie != null) {
                sumasol += sniadanie.getSumaSol();
            }
            if (lunch != null) {
                sumasol += lunch.getSumaSol();
            }
            if (drugieSniadanie != null) {
                sumasol += drugieSniadanie.getSumaSol();
            }
            if (kolacja != null) {
                sumasol += kolacja.getSumaSol();
            }
        }
        if (produktyLuzneWDniu != null) {
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sumasol += produktyLuzneWDniu.get(i).getSol();
            }
        }
        return sumasol;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

}
