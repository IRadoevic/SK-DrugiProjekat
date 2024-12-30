package com.raf.runner;


import com.raf.domain.User;
import com.raf.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private UserRepository userRepository;

    public TestDataRunner(UserRepository userRepository) {
             this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert admin
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole("ADMIN");
        admin.setDatumRodjenja(LocalDate.now());
        admin.setFirstName("a");
        admin.setLastName("aa");
        userRepository.save(admin);
    }
}
