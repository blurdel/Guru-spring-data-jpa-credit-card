package com.blurdel.sdjpa.creditcard;

import com.blurdel.sdjpa.creditcard.domain.CreditCard;
import com.blurdel.sdjpa.creditcard.repositories.CreditCardRepository;
import com.blurdel.sdjpa.creditcard.services.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuruSpringDataJpaCreditCardApplicationTests {

	final String CREDIT_CARD = "12345678900000";

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	CreditCardRepository creditCardRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;


	@Test
	void testSaveAndStoreCreditCard() {
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber(CREDIT_CARD);
		cc.setCcv("123");
		cc.setExpirationDate("12/2028");

		CreditCard saved = creditCardRepo.saveAndFlush(cc);

		System.out.println("Getting CC from database:" + saved.getCreditCardNumber());

		System.out.println("CC at Rest");
		System.out.println("CC encrypted: " + encryptionService.encrypt(CREDIT_CARD));

		Map<String, Object> dbRow = jdbcTemplate.queryForMap("select * from credit_card where id = " + saved.getId());

		String dbCardValue = (String) dbRow.get("credit_card_number");

		assertThat(saved.getCreditCardNumber()).isNotEqualTo(dbCardValue);
		assertThat(dbCardValue).isEqualTo(encryptionService.encrypt(CREDIT_CARD));


		CreditCard fetched = creditCardRepo.findById(saved.getId()).get();

		assertThat(saved.getCreditCardNumber()).isEqualTo(fetched.getCreditCardNumber());
	}

}
