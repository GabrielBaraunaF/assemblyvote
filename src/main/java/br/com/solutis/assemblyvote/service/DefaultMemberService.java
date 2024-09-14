package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Member;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultMemberService implements  MemberService{

    @Autowired
    private MemberRepository repository;

    @Override
    public Member findById(Integer id) {
        Optional<Member>member = repository.findById(id);
         if (member.isEmpty()){
             throw new ApplicationException("Nenhum usario Cadastrado com este ID");
         }
         return member.get();
    }
}
