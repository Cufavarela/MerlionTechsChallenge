package com.merliontechs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.merliontechs.domain.enumeration.State;

/**
 * A Sales.
 */
@Entity
@Table(name = "sales")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "comprador")
    private String comprador;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "pagado")
    private String pagado;

    @Column(name = "precio")
    private String precio;

    @ManyToOne
    @JsonIgnoreProperties(value = "sales", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public Sales state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getComprador() {
        return comprador;
    }

    public Sales comprador(String comprador) {
        this.comprador = comprador;
        return this;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getFecha() {
        return fecha;
    }

    public Sales fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPagado() {
        return pagado;
    }

    public Sales pagado(String pagado) {
        this.pagado = pagado;
        return this;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public String getPrecio() {
        return precio;
    }

    public Sales precio(String precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Product getProduct() {
        return product;
    }

    public Sales product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sales)) {
            return false;
        }
        return id != null && id.equals(((Sales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sales{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", comprador='" + getComprador() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", pagado='" + getPagado() + "'" +
            ", precio='" + getPrecio() + "'" +
            "}";
    }
}
