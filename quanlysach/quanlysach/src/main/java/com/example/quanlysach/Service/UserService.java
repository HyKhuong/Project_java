package com.example.quanlysach.Service;

import com.example.quanlysach.Repository.IRoleRepository;
import com.example.quanlysach.Repository.IUserRepository;
import com.example.quanlysach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserRepository userRepository;
    public void save(User user){
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if(roleId!=0&&userId!=0){
            userRepository.addRoleToUser(userId,roleId);
        }
    }
}
