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

    @Column(name = "SUMABIALKO")
    private Double sumabialko;
    @Column(name = "SUMABLONNIK")
    private Double sumablonnik;
    @Column(name = "SUMACUKRYPROSTE")
    private Double sumacukryproste;
    @Column(name = "SUMACUKRYSUMA")
    private Double sumacukrysuma;
    @Column(name = "SUMACUKRYZLOZONE")
    private Double sumacukryzlozone;
    @Column(name = "SUMAKCAL")
    private Double sumakcal;
    @Column(name = "SUMASOL")
    private Double sumasol;
    @Column(name = "SUMATLUSZCZ")
    private Double sumatluszcz;
    @Column(name = "NAZWA")
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
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaKcal();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaKcal();
            }
            if (obiad != null){
                sum += obiad.getSumaKcal();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaKcal();
            }
            if (kolacja != null){
                sum += kolacja.getSumaKcal();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getKcal();
            }
        }
        return sum;
    }

    public Double getSumaBialko() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaBialko();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaBialko();
            }
            if (obiad != null){
                sum += obiad.getSumaBialko();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaBialko();
            }
            if (kolacja != null){
                sum += kolacja.getSumaBialko();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getBialko();
            }
        }        
        return sum;
    }

    public Double getSumaCukryProste() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaCukryProste();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaCukryProste();
            }
            if (obiad != null){
                sum += obiad.getSumaCukryProste();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaCukryProste();
            }
            if (kolacja != null){
                sum += kolacja.getSumaCukryProste();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getCukryZlozone();
            }
        }        
        return sum;
    }

    public Double getSumaCukrySuma() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaCukrySuma();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaCukrySuma();
            }
            if (obiad != null){
                sum += obiad.getSumaCukrySuma();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaCukrySuma();
            }
            if (kolacja != null){
                sum += kolacja.getSumaCukrySuma();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getCukrySuma();
            }
        }
        
        return sum;
    }

    public Double getSumaCukryZlozone() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaCukryZlozone();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaCukryZlozone();
            }
            if (obiad != null){
                sum += obiad.getSumaCukryZlozone();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaCukryZlozone();
            }
            if (kolacja != null){
                sum += kolacja.getSumaCukryZlozone();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getCukryZlozone();
            }
        }        
        return sum;
    }

    public Double getSumaTluszcz() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaTluszcz();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaTluszcz();
            }
            if (obiad != null){
                sum += obiad.getSumaTluszcz();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaTluszcz();
            }
            if (kolacja != null){
                sum += kolacja.getSumaTluszcz();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getTluszcz();
            }
        }        
        return sum;
    }

    public Double getSumaBlonnik() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaBlonnik();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaBlonnik();
            }
            if (obiad != null){
                sum += obiad.getSumaBlonnik();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaBlonnik();
            }
            if (kolacja != null){
                sum += kolacja.getSumaBlonnik();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getBlonnik();
            }
        }        
        return sum;
    }

    public Double getSumaSol() {
        double sum = 0.0;
        if (czy5dni == '1'){
            if (sniadanie != null){
                sum += sniadanie.getSumaSol();
            }
            if (drugieSniadanie != null){
                sum += drugieSniadanie.getSumaSol();
            }
            if (obiad != null){
                sum += obiad.getSumaSol();
            }
            if (podwieczorek != null){
                sum += podwieczorek.getSumaSol();
            }
            if (kolacja != null){
                sum += kolacja.getSumaSol();
            }            
        }
        else{
        }
        if (produktyLuzneWDniu != null){
            for (int i = 0; i < produktyLuzneWDniu.size(); i++) {
                sum += produktyLuzneWDniu.get(i).getSol();
            }
        }        
        return sum;
    }

    public Double getSumabialko() {
        return sumabialko;
    }

    public void setSumabialko(Double sumabialko) {
        this.sumabialko = sumabialko;
    }

    public Double getSumablonnik() {
        return sumablonnik;
    }

    public void setSumablonnik(Double sumablonnik) {
        this.sumablonnik = sumablonnik;
    }

    public Double getSumacukryproste() {
        return sumacukryproste;
    }

    public void setSumacukryproste(Double sumacukryproste) {
        this.sumacukryproste = sumacukryproste;
    }

    public Double getSumacukrysuma() {
        return sumacukrysuma;
    }

    public void setSumacukrysuma(Double sumacukrysuma) {
        this.sumacukrysuma = sumacukrysuma;
    }

    public Double getSumacukryzlozone() {
        return sumacukryzlozone;
    }

    public void setSumacukryzlozone(Double sumacukryzlozone) {
        this.sumacukryzlozone = sumacukryzlozone;
    }

    public Double getSumakcal() {
        return sumakcal;
    }

    public void setSumakcal(Double sumakcal) {
        this.sumakcal = sumakcal;
    }

    public Double getSumasol() {
        return sumasol;
    }

    public void setSumasol(Double sumasol) {
        this.sumasol = sumasol;
    }

    public Double getSumatluszcz() {
        return sumatluszcz;
    }

    public void setSumatluszcz(Double sumatluszcz) {
        this.sumatluszcz = sumatluszcz;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
}
