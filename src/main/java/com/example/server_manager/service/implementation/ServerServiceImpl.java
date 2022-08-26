package com.example.server_manager.service.implementation;

import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import com.example.server_manager.service.ServerService;
import com.example.server_manager.utils.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());

        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);

        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);

        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("fetching all servers");

        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("fetching server by Id: {}", id);

        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("update server: {}", server.getName());

        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("delete server by Id: {}", id);
        serverRepository.deleteById(id);

        return true;
    }

    private String setServerImageUrl() {
        String[] images = { "ser1.png", "ser2.png", "ser13.png", "ser4.png" };
        Random random = new Random();
        int index = random.nextInt(images.length);

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("server/image/" + images[index]).toUriString();
    }
}
