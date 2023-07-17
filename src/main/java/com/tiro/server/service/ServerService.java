package com.tiro.server.service;

import com.tiro.server.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Server getServer(Long id);
    Server update(Server server);
    Collection<Server> list(int limit);
    Boolean delete(Long id);
    Server ping(String ipAddress) throws IOException;
}
