package com.example.quanlysach.Validator;

import com.example.quanlysach.Repository.IUserRepository;
import com.example.quanlysach.Validator.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername,String> {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public boolean isValid(String username,ConstraintValidatorContext context){
        if(userRepository ==null){
            return true;
        }
        return userRepository.findByUsername(username)==null;
    }
}
