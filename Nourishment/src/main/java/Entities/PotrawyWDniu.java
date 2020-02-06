/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "PotrawyWDniu.findByData", query = "SELECT p FROM PotrawyWDniu p WHERE p.data = :data"),
    @NamedQuery(name = "PotrawyWDniu.findByMnoznik", query = "SELECT p FROM PotrawyWDniu p WHERE p.mnoznik = :mnoznik")})
public class PotrawyWDniu implements Serializable {

    @JoinColumn(name = "ID_POTRAWY", referencedColumnName = "ID")
    @ManyToOne
    private Potrawy idPotrawy;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MNOZNIK")
    private Double mnoznik;
    @JoinColumn(name = "TYP_POSILKU", referencedColumnName = "ID")
    @ManyToOne
    private TypPosilku typPosilku;

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

    public Double getMnoznik() {
        return mnoznik;
    }

    public void setMnoznik(Double mnoznik) {
        this.mnoznik = mnoznik;
    }

    public TypPosilku getTypPosilku() {
        return typPosilku;
    }

    public void setTypPosilku(TypPosilku typPosilku) {
        this.typPosilku = typPosilku;
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
        return "Potrawa w dniu";
    }

    public Potrawy getIdPotrawy() {
        return idPotrawy;
    }

    public void setIdPotrawy(Potrawy idPotrawy) {
        this.idPotrawy = idPotrawy;
    }
    
}
