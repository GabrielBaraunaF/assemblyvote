package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.Member;
import br.com.solutis.assemblyvote.exception.ApplicationException;
import br.com.solutis.assemblyvote.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultMemberServiceTest {

    @InjectMocks
    private DefaultMemberService service;
    @Mock
    private MemberRepository repository;

    @Test
    void shouldThrowExceptionWhenMemeberNotFound(){

        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> {
                    service.findById(anyInt());
                }
        );
        assertEquals("No user is registered with this ID.", exception.getMessage());
    }
    @Test
    void shouldReturnMemberWhenMemberExits(){
        Member member = getMember();
        Optional<Member> memberOptional= Optional.of(getMember());
        when(repository.findById(anyInt())).thenReturn(memberOptional);

        Member result = service.findById(anyInt());

        assertEquals(result, member);
    }
    private Member getMember(){
        Member member = new Member();
        member.setId(1);
        member.setCpf("092908384543");
        member.setName("gabriel");
        return member;
    }
}