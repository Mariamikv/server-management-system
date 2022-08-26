package com.example.server_manager;

import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import com.example.server_manager.utils.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository repository) {
        return args -> {
            repository.save(new Server(
                    null,
                    "192,168,1,160",
                    "linux",
                    "8 GB",
                    "school pc",
                    "http://localhost:8086/server/image/ser1.png",
                    Status.SERVER_UP
            ));
        };
    }
}
