package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Member;

public interface MemberService {

    Member findById(Integer id);
}
