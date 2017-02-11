//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.05 at 05:10:41 PM CET 
//


package nl.mikero.spiner.core.twine.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

import nl.mikero.spiner.core.twine.extended.model.XtwMetadata;


/**
 * <p>Java class for tw-storydata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tw-storydata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="style" type="{}style" minOccurs="0"/>
 *         &lt;element name="script" type="{}script" minOccurs="0"/>
 *         &lt;element name="xtw-metadata" type="{}xtw-metadata" minOccurs="0"/>
 *         &lt;element name="tw-passagedata" type="{}tw-passagedata" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="startnode" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="creator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="creator-version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ifid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="options" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="hidden" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tw-storydata", propOrder = {
    "style",
    "script",
    "xtwMetadata",
    "twPassagedata"
})
public class TwStorydata {

    protected Style style;
    protected Script script;
    @XmlElement(name = "xtw-metadata")
    protected XtwMetadata xtwMetadata;
    @XmlElement(name = "tw-passagedata", required = true)
    protected List<TwPassagedata> twPassagedata;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "startnode")
    protected Integer startnode;
    @XmlAttribute(name = "creator")
    protected String creator;
    @XmlAttribute(name = "creator-version")
    protected String creatorVersion;
    @XmlAttribute(name = "ifid")
    protected String ifid;
    @XmlAttribute(name = "format")
    protected String format;
    @XmlAttribute(name = "options")
    protected String options;
    @XmlAttribute(name = "hidden")
    protected String hidden;

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link Style }
     *     
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link Style }
     *     
     */
    public void setStyle(Style value) {
        this.style = value;
    }

    /**
     * Gets the value of the script property.
     * 
     * @return
     *     possible object is
     *     {@link Script }
     *     
     */
    public Script getScript() {
        return script;
    }

    /**
     * Sets the value of the script property.
     * 
     * @param value
     *     allowed object is
     *     {@link Script }
     *     
     */
    public void setScript(Script value) {
        this.script = value;
    }

    /**
     * Gets the value of the xtwMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link XtwMetadata }
     *     
     */
    public XtwMetadata getXtwMetadata() {
        return xtwMetadata;
    }

    /**
     * Sets the value of the xtwMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link XtwMetadata }
     *     
     */
    public void setXtwMetadata(XtwMetadata value) {
        this.xtwMetadata = value;
    }

    /**
     * Gets the value of the twPassagedata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the twPassagedata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTwPassagedata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TwPassagedata }
     * 
     * 
     */
    public List<TwPassagedata> getTwPassagedata() {
        if (twPassagedata == null) {
            twPassagedata = new ArrayList<TwPassagedata>();
        }
        return this.twPassagedata;
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
     * Gets the value of the startnode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartnode() {
        return startnode;
    }

    /**
     * Sets the value of the startnode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartnode(Integer value) {
        this.startnode = value;
    }

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreator(String value) {
        this.creator = value;
    }

    /**
     * Gets the value of the creatorVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorVersion() {
        return creatorVersion;
    }

    /**
     * Sets the value of the creatorVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorVersion(String value) {
        this.creatorVersion = value;
    }

    /**
     * Gets the value of the ifid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIfid() {
        return ifid;
    }

    /**
     * Sets the value of the ifid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIfid(String value) {
        this.ifid = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptions(String value) {
        this.options = value;
    }

    /**
     * Gets the value of the hidden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * Sets the value of the hidden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHidden(String value) {
        this.hidden = value;
    }

}
