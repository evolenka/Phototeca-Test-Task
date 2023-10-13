package by.evlashkina.crypto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
