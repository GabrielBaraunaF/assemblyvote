package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Session;

import java.util.List;

public interface SessionService {
    Session save(Session session);

    Session update(Session session);

    List<Session> findAllOpenSession();

    Session findById(Integer id);
}
