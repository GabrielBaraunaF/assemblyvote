package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.entity.VoteCounting;
import br.com.solutis.assemblyvote.repository.VoteCoutingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultVoteCoutingServiceTest {
    @InjectMocks
    private DefaultVoteCoutingService service;
    @Mock
    private VoteCoutingRepository repository;
    @Test
     void save_shouldSetStatusToAberta_andSave() {
        VoteCounting voteCounting = new VoteCounting();
        VoteCounting savedVoteCounting = new VoteCounting();
        savedVoteCounting.setStatus("Aberta");

        when(repository.save(voteCounting)).thenReturn(savedVoteCounting);

        VoteCounting returnedVoteCounting = service.save(voteCounting);

        assertEquals("Aberta", returnedVoteCounting.getStatus());
        verify(repository).save(voteCounting);
    }

    @Test
     void findBySessionId_shouldReturnVoteCounting_whenFound() {
        Integer sessionId = 1;
        VoteCounting expectedVoteCounting = new VoteCounting();

        when(repository.findBySessionId(sessionId)).thenReturn(expectedVoteCounting);

        VoteCounting actualVoteCounting = service.findBySessionId(sessionId);

        assertEquals(expectedVoteCounting, actualVoteCounting);
    }

    @Test
     void findBySessionId_shouldReturnNull_whenNotFound() {
        Integer sessionId = 2;

        when(repository.findBySessionId(sessionId)).thenReturn(null);

        VoteCounting actualVoteCounting = service.findBySessionId(sessionId);

        assertNull(actualVoteCounting);
    }
    @Test
     void saveAll_shouldDelegateToRepository() {
        List<VoteCounting> voteCountingList = new ArrayList<>();
        voteCountingList.add(new VoteCounting()); // Add some sample VoteCounting objects

        service.saveAll(voteCountingList);

        verify(repository).saveAll(voteCountingList);
    }

}