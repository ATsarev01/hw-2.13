package com.github.atsarev01.mochito.service;

import com.github.atsarev01.mochito.exeption.IncorrectNameExeption;
import com.github.atsarev01.mochito.exeption.IncorrectSurnameExeption;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {
    public String validateName(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectNameExeption();
        }
        return StringUtils.capitalize(name.toLowerCase());
    }

    public String validateSurname(String surname) {
        String[] surnames = surname.split("-");
        for (int i = 0; i < surnames.length; i++) {
            String lastName = surnames[i];
            if (!StringUtils.isAlpha(surname)) {
                throw new IncorrectSurnameExeption();
            }
            surnames[i] = StringUtils.capitalize(lastName.toLowerCase());
        }
        return String.join("-", surnames);
    }
}
