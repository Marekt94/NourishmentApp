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
@Table(name = "KATEGORIA_PRODUKTU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KategoriaProduktu.findAll", query = "SELECT k FROM KategoriaProduktu k"),
    @NamedQuery(name = "KategoriaProduktu.findByIdKategorii", query = "SELECT k FROM KategoriaProduktu k WHERE k.id = :id"),
    @NamedQuery(name = "KategoriaProduktu.findByNazwaKategorii", query = "SELECT k FROM KategoriaProduktu k WHERE k.nazwaKategorii = :nazwaKategorii")})
public class KategoriaProduktu implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAZWA_KATEGORII")
    private String nazwaKategorii;
    @OneToMany(mappedBy = "kategoria")
    private Collection<Produkty> produktyCollection;

    public KategoriaProduktu() {
    }

    public String getNazwaKategorii() {
        return nazwaKategorii;
    }

    public void setNazwaKategorii(String nazwaKategorii) {
        this.nazwaKategorii = nazwaKategorii;
    }

    @XmlTransient
    public Collection<Produkty> getProduktyCollection() {
        return produktyCollection;
    }

    public void setProduktyCollection(Collection<Produkty> produktyCollection) {
        this.produktyCollection = produktyCollection;
    }

    @Override
    public String toString() {
        return nazwaKategorii;
    }

    public KategoriaProduktu(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof KategoriaProduktu)) {
            return false;
        }
        KategoriaProduktu other = (KategoriaProduktu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
