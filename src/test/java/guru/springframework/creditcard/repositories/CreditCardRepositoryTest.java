package guru.springframework.creditcard.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import guru.springframework.creditcard.domain.CreditCard;
import guru.springframework.creditcard.services.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CreditCardRepositoryTest {

  private final String CREDIT_CARD = "123456789000000";

  @Autowired
  private CreditCardRepository creditCardRepository;

  @Autowired
  private EncryptionService encryptionService;

  @Test
  void testSaveAndStoreCreditCard() {
    var creditCard = new CreditCard();
    creditCard.setCreditCardNumber(CREDIT_CARD);
    creditCard.setCvv("123");
    creditCard.setExpirationDate("12/2028");

    var savedCC = creditCardRepository.saveAndFlush(creditCard);

    System.out.println("Getting CC from database: " + creditCard.getCreditCardNumber());

    System.out.println("CC At Rest: ");
    System.out.println(encryptionService.encrypt(CREDIT_CARD));

    var fetchedCC = creditCardRepository.findById(savedCC.getId());

    assertThat(savedCC.getCreditCardNumber()).isEqualTo(fetchedCC.get().getCreditCardNumber());
  }

}