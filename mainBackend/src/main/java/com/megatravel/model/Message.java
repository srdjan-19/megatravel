//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.01 at 01:27:15 AM CET 
//


package com.megatravel.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="agent" type="{http://www.megatravel.com/users}User"/>
 *         &lt;element name="client" type="{http://www.megatravel.com/users}User"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sentBy" type="{http://www.megatravel.com/users}roles"/>
 *         &lt;element name="status" type="{http://www.megatravel.com/message}messageStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", namespace = "http://www.megatravel.com/message", propOrder = {
    "id",
    "agent",
    "client",
    "content",
    "sentBy",
    "status"
})
@Entity
public class Message {


	@Id
	@GeneratedValue(generator = "message_sequence")
	@SequenceGenerator(initialValue=501, 
                        allocationSize=1,
                        name = "message_sequence", 
                        sequenceName="message_sequence")
	@NotNull
    protected long id;
    
    @XmlElement(required = true)
    @ManyToOne
    @NotNull
    protected User agent;
    
    @XmlElement(required = true)
    @ManyToOne
    @NotNull
    protected User client;
    
    @XmlElement(required = true)
    @NotEmpty(message = "Please fill the content of message.")
    protected String content;
    
    @XmlElement(required = true)
    @Enumerated(EnumType.STRING)
	@NotNull
    protected Roles sentBy;
    
    @XmlElement(required = true)
    @Enumerated(EnumType.STRING)
	@NotNull
    protected MessageStatus status;

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
     * Gets the value of the agent property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getAgent() {
        return agent;
    }

    /**
     * Sets the value of the agent property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setAgent(User value) {
        this.agent = value;
    }

    /**
     * Gets the value of the client property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getClient() {
        return client;
    }

    /**
     * Sets the value of the client property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setClient(User value) {
        this.client = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the sentBy property.
     * 
     * @return
     *     possible object is
     *     {@link Roles }
     *     
     */
    public Roles getSentBy() {
        return sentBy;
    }

    /**
     * Sets the value of the sentBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Roles }
     *     
     */
    public void setSentBy(Roles value) {
        this.sentBy = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link MessageStatus }
     *     
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageStatus }
     *     
     */
    public void setStatus(MessageStatus value) {
        this.status = value;
    }

}
