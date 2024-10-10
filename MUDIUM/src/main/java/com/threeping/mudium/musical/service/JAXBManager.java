package com.threeping.mudium.musical.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.dto.MusicalItem;
import com.threeping.mudium.musical.dto.MusicalListResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;

public class JAXBManager {
    private static final JAXBManager INSTANCE = new JAXBManager();
    private final JAXBContext jaxbContext;

    private JAXBManager() {
        try {
            this.jaxbContext = JAXBContext.newInstance(MusicalListResponse.class, MusicalItem.class);
        } catch (JAXBException e) {
            throw new CommonException(ErrorCode.JAXB_CONTEXT_ERROR);
        }
    }

    public static JAXBManager getInstance() {
        return INSTANCE;
    }

    public <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException {
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        return clazz.cast(unmarshaller.unmarshal(new StringReader(xml)));
    }
}
