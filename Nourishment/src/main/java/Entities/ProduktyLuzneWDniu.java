/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Marek
 */
@Entity
@Table (name = "PRODUKTY_LUZNE_W_DNIU")
public class ProduktyLuzneWDniu implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ILOSC_W_G")
    private Double iloscWG;
    @JoinColumn(name = "ID_PRODUKTU", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Produkty produkt;
    @JoinColumn(name = "ID_DNIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PotrawyWDniu dzien; 

    public Double getIloscWG() {
        return iloscWG;
    }

    public void setIloscWG(Double iloscWG) {
        this.iloscWG = iloscWG;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PotrawyWDniu getDzien() {
        return dzien;
    }

    public void setDzien(PotrawyWDniu dzien) {
        this.dzien = dzien;
    }

    public Produkty getProdukt() {
        return produkt;
    }

    public void setProdukt(Produkty produkt) {
        this.produkt = produkt;
    }
    
    
}
