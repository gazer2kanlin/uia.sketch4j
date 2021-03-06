//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.05 at 06:03:32 PM CST 
//


package uia.sketch.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhotoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhotoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zoom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="viewWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="viewHeight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dragTarget" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="layers" type="{http://sketch.uia/model/xml}LayerListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhotoType", propOrder = {
    "name",
    "path",
    "zoom",
    "viewWidth",
    "viewHeight",
    "offset",
    "dragTarget",
    "layers"
})
public class PhotoType {

    @XmlElement(required = true, defaultValue = "Undefined")
    protected String name;
    @XmlElement(required = true)
    protected String path;
    @XmlElement(defaultValue = "1.0")
    protected double zoom;
    protected int viewWidth;
    protected int viewHeight;
    @XmlElement(required = true, defaultValue = "0,0")
    protected String offset;
    @XmlElement(required = true, defaultValue = "PHOTO")
    protected String dragTarget;
    @XmlElement(required = true)
    protected LayerListType layers;

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
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the zoom property.
     * 
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * Sets the value of the zoom property.
     * 
     */
    public void setZoom(double value) {
        this.zoom = value;
    }

    /**
     * Gets the value of the viewWidth property.
     * 
     */
    public int getViewWidth() {
        return viewWidth;
    }

    /**
     * Sets the value of the viewWidth property.
     * 
     */
    public void setViewWidth(int value) {
        this.viewWidth = value;
    }

    /**
     * Gets the value of the viewHeight property.
     * 
     */
    public int getViewHeight() {
        return viewHeight;
    }

    /**
     * Sets the value of the viewHeight property.
     * 
     */
    public void setViewHeight(int value) {
        this.viewHeight = value;
    }

    /**
     * Gets the value of the offset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOffset(String value) {
        this.offset = value;
    }

    /**
     * Gets the value of the dragTarget property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDragTarget() {
        return dragTarget;
    }

    /**
     * Sets the value of the dragTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDragTarget(String value) {
        this.dragTarget = value;
    }

    /**
     * Gets the value of the layers property.
     * 
     * @return
     *     possible object is
     *     {@link LayerListType }
     *     
     */
    public LayerListType getLayers() {
        return layers;
    }

    /**
     * Sets the value of the layers property.
     * 
     * @param value
     *     allowed object is
     *     {@link LayerListType }
     *     
     */
    public void setLayers(LayerListType value) {
        this.layers = value;
    }

}
