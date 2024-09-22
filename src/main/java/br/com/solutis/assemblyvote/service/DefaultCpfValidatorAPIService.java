package br.com.solutis.assemblyvote.service;

import br.com.solutis.assemblyvote.to.CpfValidatorTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultCpfValidatorAPIService implements CpfValidatorAPIService {

    @Value("endpoint.cpfValidator")
    private String endpointCpfValidator;

    @Override
    public boolean isValid(String cpf) {
        try {
            String apiSourceUrl = endpointCpfValidator + cpf;

            RestTemplate restTemplate = new RestTemplate();
            CpfValidatorTO cpfValidatorTO = restTemplate.getForObject(apiSourceUrl, CpfValidatorTO.class);
            if (cpfValidatorTO != null) {
                return cpfValidatorTO.getIsValid();
            }
        } catch (Exception ex) {
            System.out.println("erro ao se comunicar ao serviço de cpf");
        }
        return true;
    }
}
