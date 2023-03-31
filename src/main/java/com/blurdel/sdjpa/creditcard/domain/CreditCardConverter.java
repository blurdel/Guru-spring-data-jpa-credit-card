package com.blurdel.sdjpa.creditcard.domain;

import com.blurdel.sdjpa.creditcard.config.SpringContextHelper;
import com.blurdel.sdjpa.creditcard.services.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        System.out.println("CreditCardConverter encrypt called ...");
        return getEncryptionService().encrypt((attribute));
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        System.out.println("CreditCardConverter decrypt called ...");
        return getEncryptionService().decrypt(dbData);
    }

    private EncryptionService getEncryptionService() {
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }

}
