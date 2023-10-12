package by.evlashkina.crypto.repository;

import by.evlashkina.crypto.entity.CurrencyDetails;
import by.evlashkina.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyDetails, Long> {
}
