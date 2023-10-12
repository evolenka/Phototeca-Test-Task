package by.evlashkina.crypto.service;

import by.evlashkina.crypto.entity.CurrencyDetails;

import java.util.List;

public interface CurrencyService {

   List<CurrencyDetails> requestPriceUpdate();
}
