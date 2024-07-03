package com.example.quanlysach.Validator;




import com.example.quanlysach.Validator.annotation.ValidUserId;
import com.example.quanlysach.entity.User;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidator;
public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null)
            return true;
        return user.getId() != null;
    }
}