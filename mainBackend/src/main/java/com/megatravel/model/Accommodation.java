//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.11 at 11:34:41 PM CEST 
//


package com.megatravel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Where;


/**
 * <p>Java class for Accommodation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Accommodation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.megatravel.com/codebook}AccommodationType"/>
 *         &lt;element name="category" type="{http://www.megatravel.com/codebook}AccommodationCategory"/>
 *         &lt;element name="ownedBy" type="{http://www.megatravel.com/users}Agent"/>
 *         &lt;element name="distance">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="image" type="{http://www.megatravel.com/accommodation}ImageResource" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="address" type="{http://www.megatravel.com/accommodation}Address"/>
 *         &lt;element name="capacity">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="pricelist" type="{http://www.megatravel.com/accommodation}pricelist" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="additionalServices" type="{http://www.megatravel.com/codebook}AdditionalService" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cancellation" type="{http://www.megatravel.com/accommodation}Cancellation"/>
 *         &lt;element name="rates" type="{http://www.megatravel.com/accommodation}Rate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.megatravel.com/accommodation}Comment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Accommodation", propOrder = {
    "id",
    "name",
    "type",
    "category",
    "ownedBy",
    "distance",
    "description",
    "images",
    "address",
    "capacity",
    "pricelist",
    "additionalServices",
    "cancellation",
    "rates",
    "comments"
})
@Entity
public class Accommodation {

	@Id
	@GeneratedValue
	@NotNull
    protected long id;
	
    @XmlElement(required = true)
    @NotBlank
    protected String name;
    
    @XmlElement(required = true)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @NotNull
    protected AccommodationType type;
    
    @XmlElement(required = true)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @NotNull
    protected AccommodationCategory category;
    
    @XmlElement(required = true)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @NotNull
    protected Agent ownedBy;
    
    protected double distance;
    
    @XmlElement(required = true)
    @NotBlank
    protected String description;
    
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    protected List<ImageResource> images;
    
    @XmlElement(required = true)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @NotNull
    protected Address address;
   
    @NotNull
    protected int capacity;
    
    @XmlElement(required = true)
    @OneToMany(fetch = FetchType.LAZY)
    @NotNull
    protected List<PriceInSeason> pricelist;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    protected List<AdditionalService> additionalServices;
    
    @XmlElement(required = true)
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    protected Cancellation cancellation;

    @OneToMany(fetch = FetchType.LAZY)
    protected List<Rate> rates;
    
    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "visible = true")
    protected List<Comment> comments;

    public Accommodation() {
    	this.rates = new ArrayList<Rate>();
    	this.comments = new ArrayList<Comment>();
    	this.additionalServices = new ArrayList<AdditionalService>();
    	this.pricelist = new ArrayList<PriceInSeason>();
    }
    
    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationType }
     *     
     */
    public AccommodationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationType }
     *     
     */
    public void setType(AccommodationType value) {
        this.type = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationCategory }
     *     
     */
    public AccommodationCategory getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationCategory }
     *     
     */
    public void setCategory(AccommodationCategory value) {
        this.category = value;
    }

    /**
     * Gets the value of the ownedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Agent }
     *     
     */
    public Agent getOwnedBy() {
        return ownedBy;
    }

    /**
     * Sets the value of the ownedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Agent }
     *     
     */
    public void setOwnedBy(Agent value) {
        this.ownedBy = value;
    }

    /**
     * Gets the value of the distance property.
     * 
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     */
    public void setDistance(double value) {
        this.distance = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the images property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the image property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImageResource }
     * 
     * 
     */
    public List<ImageResource> getImage() {
        if (images == null) {
            images = new ArrayList<ImageResource>();
        }
        return this.images;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(int value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the priceInSeason property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInSeason }
     *     
     */
    public List<PriceInSeason> getPriceInSeason() {
        return pricelist;
    }

    /**
     * Sets the value of the priceInSeason property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInSeason }
     *     
     */
    public void setPriceInSeason(List<PriceInSeason> value) {
        this.pricelist = value;
    }

    /**
     * Gets the value of the additionalServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalService }
     * 
     * 
     */
    public List<AdditionalService> getAdditionalServices() {
        if (additionalServices == null) {
            additionalServices = new ArrayList<AdditionalService>();
        }
        return this.additionalServices;
    }

    /**
     * Gets the value of the cancellation property.
     * 
     * @return
     *     possible object is
     *     {@link Cancellation }
     *     
     */
    public Cancellation getCancellation() {
        return cancellation;
    }

    /**
     * Sets the value of the cancellation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cancellation }
     *     
     */
    public void setCancellation(Cancellation value) {
        this.cancellation = value;
    }

    /**
     * Gets the value of the rates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rate }
     * 
     * 
     */
    public List<Rate> getRates() {
        if (rates == null) {
            rates = new ArrayList<Rate>();
        }
        return this.rates;
    }

    /**
     * Gets the value of the comments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comment }
     * 
     * 
     */
    public List<Comment> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comment>();
        }
        return this.comments;
    }

	public void setImage(List<ImageResource> images) {
		this.images = images;
	}

	public void setAdditionalServices(List<AdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
