package com.tiro.server.service;

import com.tiro.server.enumeration.Status;
import com.tiro.server.model.Server;
import com.tiro.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService{
    private final ServerRepository serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server getServer(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server: {}", id);
        serverRepo.deleteById(id);
        return true;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        try{
            InetAddress address = InetAddress.getByName(ipAddress);
            server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
            serverRepo.save(server);
        }catch (UnknownHostException e){
            server.setStatus(Status.UNKNOWN_HOST);
            serverRepo.save(server);
        }
        return server;
    }

    private String setServerImageUrl() {
        String[] ImagesName = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + ImagesName[new Random().nextInt(4)]).toUriString();
    }
}
