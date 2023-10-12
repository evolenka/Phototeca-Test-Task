package by.evlashkina.crypto.service;

import by.evlashkina.crypto.entity.CurrencyDetails;
import by.evlashkina.crypto.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final static String BASE_URL = "https://api.mexc.com/api/v3/ticker/price";
    private final RestTemplate restTemplate = new RestTemplate();
    private final CurrencyRepository currencyRepository;


    List<CurrencyDetails> response
            = restTemplate.exchange(
            BASE_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<CurrencyDetails>>() {
            }
    ).getBody();

    @Override
    public List<CurrencyDetails> requestPriceUpdate() {

        List<CurrencyDetails> newCurrencyPrices
                = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CurrencyDetails>>() {
                }
        ).getBody();

        log.info("newCurrencyPrices {}",newCurrencyPrices);

        List<CurrencyDetails> currentCurrencyPrices = currencyRepository.findAll();

        if (currentCurrencyPrices.isEmpty()) {
            Optional.ofNullable(newCurrencyPrices).ifPresent(currencyRepository::saveAll);
            log.info("currency prices have been saved");
            return List.of();
        } else {
            return getChangedCurrencyPrices(newCurrencyPrices, currentCurrencyPrices);
        }
    }

    public List<CurrencyDetails> getChangedCurrencyPrices
            (List<CurrencyDetails> newCurrencyPrices, List<CurrencyDetails> currentCurrencyPrices) {
        //List <CurrencyDetails> changedCurrencyPrices=
       // newCurrencyPrices.stream().forEach(c->get);
        log.info("currency prices have been updated {}", newCurrencyPrices );

        return newCurrencyPrices;
    }
}
