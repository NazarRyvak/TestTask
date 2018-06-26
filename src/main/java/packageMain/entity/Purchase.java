package packageMain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
//import packageMain.entity.enums.Currency;

@Data
@Table(name = "purchase")
@Entity
public class Purchase extends BaseEntity {

	private LocalDate date;

	private String name;

	@Column(columnDefinition = "DECIMAL(10,2)")
	private BigDecimal price;

	private String currency;
	/*@Enumerated(EnumType.ORDINAL)
	private Currency currency;*/

}
