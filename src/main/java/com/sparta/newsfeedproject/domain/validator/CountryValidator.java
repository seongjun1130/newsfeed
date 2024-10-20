package com.sparta.newsfeedproject.domain.validator;

import com.sparta.newsfeedproject.domain.member.client.CountryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountryValidator implements ConstraintValidator<Country, String> {

    private final CountryService countryService;

    @Override
    public void initialize(Country constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return countryService.getCountryNames().contains(value);
    }
}

