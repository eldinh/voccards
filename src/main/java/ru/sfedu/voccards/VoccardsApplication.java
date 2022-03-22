package ru.sfedu.voccards;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.sfedu.voccards.dao.CardDao;
import ru.sfedu.voccards.dao.RoleDao;
import ru.sfedu.voccards.dao.UserDao;
import ru.sfedu.voccards.entity.*;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class VoccardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoccardsApplication.class, args);

	}

	@Bean
	CommandLineRunner runner(RoleDao roleDao, CardDao cardDao, UserDao userDao){

//		if (cardDao.findAll().size() != 0)
//			return null;
//
//		roleDao.save(new Role(null, ERole.ROLE_USER));
//		roleDao.save(new Role(null, ERole.ROLE_TEACHER));
//		roleDao.save(new Role(null, ERole.ROLE_VIP));
//
//		cardDao.save(new Card(null, "яблоко", "й’аблака", "apple", "æpl"));
//		cardDao.save(new Card(null, "машина", "машына", "car", "kɑːr"));
//		cardDao.save(new Card(null, "подавать заявление", "падав`ат’ зай’ивл’`эн’ий’э", "apply for", "əˈplaɪ fə(r)"));
//		cardDao.save(new Card(null, "расширять", "раш:ыр’`ат’", "expand", "ɪkˈspænd"));
//		cardDao.save(new Card(null, "цель", "ц`эл’", "goal", "ɡəʊl"));
//		cardDao.save(new Card(null, "пропорция", "прап`орцый’а", "proportion", "prəˈpɔːʃn"));
//
//		UserApp user = new UserApp();
//		user.setUsername("eldinh");
//		user.setEmail("eldinh1337@gmail.com");
//		user.setPassword("123");
//		user.setRoles(new HashSet<>(Set.of(roleDao.findByName(ERole.ROLE_USER))));
//		CardSet cardSet = new CardSet();
//		cardSet.setCardList(cardDao.findAll());
//		cardSet.setCreator(user);
//		user.addOwnCardSet(cardSet);

		return args -> {
		};
	}

}
