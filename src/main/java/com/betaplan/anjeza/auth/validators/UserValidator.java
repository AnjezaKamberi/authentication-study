package com.betaplan.anjeza.auth.validators;

import com.betaplan.anjeza.auth.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    /**
     * An instance of a User can be validated with this validator
     * @param c the {@link Class} that this {@link Validator} is
     * being asked if it can {@link #validate(Object, Errors) validate}
     * @return
     */
    @Override
    public boolean supports(Class<?> c) {
        return User.class.equals(c);
    }

    /**
     * Implement validations here
     * @param object the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}