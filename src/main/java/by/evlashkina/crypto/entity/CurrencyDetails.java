package by.evlashkina.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currencies")
public class CurrencyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currencies_id")
    private Long id;

    @Column(name = "currency", nullable = false)
    String symbol;

    @Column(name = "price", nullable = false)
    Double price;

    @Override
    public String toString() {
        return "currency: " + symbol + ", price: " + price;
    }
}
