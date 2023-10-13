package by.evlashkina.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@Table(name = "currencies")
public class CurrencyDetails {
    @Id
    @Column(name = "currency", nullable = false)
    String symbol;

    @Column(name = "price", nullable = false)
    Double price;

    @Override
    public String toString() {
        return "currency: " + symbol + ", price: " + price;
    }
}
