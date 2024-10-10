package com.threeping.mudium.musical.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "db")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class MusicalItem {

    @XmlElement(name = "mt20id")
    private String externalId;

    @XmlElement(name = "prfnm")
    private String title;

    @XmlElement(name = "poster")
    private String poster;
}
