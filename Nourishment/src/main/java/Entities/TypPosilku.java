/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TYP_POSILKU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypPosilku.findAll", query = "SELECT t FROM TypPosilku t"),
    @NamedQuery(name = "TypPosilku.findById", query = "SELECT t FROM TypPosilku t WHERE t.id = :id"),
    @NamedQuery(name = "TypPosilku.findByTyp", query = "SELECT t FROM TypPosilku t WHERE t.typ = :typ")})
public class TypPosilku implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TYP")
    private String typ;
    @OneToMany(mappedBy = "typPosilku")
    private Collection<PotrawyWDniu> potrawyWDniuCollection;

    public TypPosilku() {
    }

    public TypPosilku(Integer id) {
        this.id = id;
    }

    public TypPosilku(Integer id, String typ) {
        this.id = id;
        this.typ = typ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @XmlTransient
    public Collection<PotrawyWDniu> getPotrawyWDniuCollection() {
        return potrawyWDniuCollection;
    }

    public void setPotrawyWDniuCollection(Collection<PotrawyWDniu> potrawyWDniuCollection) {
        this.potrawyWDniuCollection = potrawyWDniuCollection;
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
        if (!(object instanceof TypPosilku)) {
            return false;
        }
        TypPosilku other = (TypPosilku) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return typ;
    }
    
}
