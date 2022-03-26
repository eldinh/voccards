package ru.sfedu.voccards;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.voccards.dao.*;
import ru.sfedu.voccards.dto.*;
import ru.sfedu.voccards.entity.*;
import ru.sfedu.voccards.service.AuthService;
import ru.sfedu.voccards.service.MainService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@Transactional
public class BaseTest extends TestCase {

	protected final Logger log = LogManager.getLogger(this.getName());


	@Autowired
	protected AuthService authService;

	@Autowired
	protected MainService mainService;

	@Autowired
	protected CardDao cardDao;

	@Autowired
	protected CardSetDao cardSetDao;

	@Autowired
	protected RoleDao roleDao;

	@Autowired
	protected UserDao userDao;

	@Autowired
	protected ReviewDao reviewDao;

    protected Card card = new Card(null, "тест", "ру_тест", "test", "en_test");
    protected Card cardToUpdate = new Card(null, "тест_обнова", "ру_тест", "test_update", "en_test");
    protected List<Card> cardList = new ArrayList<>(List.of(
            		new Card(null, "яблоко", "й’аблака", "apple", "æpl"),
		new Card(null, "машина", "машына", "car", "kɑːr"),
		new Card(null, "подавать заявление", "падав`ат’ зай’ивл’`эн’ий’э", "apply for", "əˈplaɪ fə(r)"),
		new Card(null, "расширять", "раш:ыр’`ат’", "expand", "ɪkˈspænd"),
		new Card(null, "цель", "ц`эл’", "goal", "ɡəʊl"),
		new Card(null, "пропорция", "прап`орцый’а", "proportion", "prəˈpɔːʃn")
    ));

	protected final String username = "eldinh";

	protected final String password = "password";

	protected final String email = "eldinh1337@gmail.com";

	protected LoginRequest loginRequest = new LoginRequest(username, password);

	protected SignupRequest signupRequest = new SignupRequest(username, email, password);

	protected MessageResponse messageResponse= new MessageResponse();

	protected AddRoleRequest addRoleRequest;

	protected JwtResponse jwtResponse;

	protected ResponseEntity<?> responseEntity;

	protected Optional<Card> cardOptional;

	protected Optional<UserApp> userOptional;

	protected Optional<CardSet> cardSetOptional;

	@Before
	public void before() {
		userDao.deleteAll();
		cardSetDao.deleteAll();
		cardDao.deleteAll();
		roleDao.deleteAll();
		reviewDao.deleteAll();

		cardDao.saveAll(cardList);
		roleDao.save(new Role(null, ERole.ROLE_USER));
		roleDao.save(new Role(null, ERole.ROLE_TEACHER));
		roleDao.save(new Role(null, ERole.ROLE_VIP));
	}
}
