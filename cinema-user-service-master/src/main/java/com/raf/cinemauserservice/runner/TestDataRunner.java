package com.raf.cinemauserservice.runner;

import com.raf.cinemauserservice.domain.Role;
import com.raf.cinemauserservice.domain.User;
import com.raf.cinemauserservice.domain.UserStatus;
import com.raf.cinemauserservice.repository.RoleRepository;
import com.raf.cinemauserservice.repository.UserRepository;
import com.raf.cinemauserservice.repository.UserStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserStatusRepository userStatusRepository;

    public TestDataRunner(RoleRepository roleRepository, UserRepository userRepository, UserStatusRepository userStatusRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleUser = new Role("ROLE_USER", "User role");
        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        //Insert admin
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        admin.setNumberOfReservations(7);
        userRepository.save(admin);
        //User statuses
        userStatusRepository.save(new UserStatus(0, 5, 0));
        userStatusRepository.save(new UserStatus(6, 10, 10));
        userStatusRepository.save(new UserStatus(11, 20, 20));
    }
}
