package pl.pdec.city.common.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pdec.city.common.application.service.exceptions.UserAlreadyExistsException;
import pl.pdec.city.common.application.service.payload.CreateUserPayload;
import pl.pdec.city.common.domain.model.User;
import pl.pdec.city.common.domain.model.vo.Email;
import pl.pdec.city.common.domain.model.vo.Phone;
import pl.pdec.city.common.domain.model.vo.UserContact;
import pl.pdec.city.common.domain.repository.UserRepository;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(CreateUserPayload payload) throws UserAlreadyExistsException {
        User existed = userRepository.findByUsername(payload.getUsername());
        if (null != existed) {
            throw new UserAlreadyExistsException();
        }

        Phone phone = null;
        if (null != payload.getPhoneType() && null != payload.getPhoneValue()) {
            phone = new Phone(payload.getPhoneType(), payload.getPhoneValue());
        }

        Email email = null;
        if (null != payload.getEmail()) {
            email = new Email(payload.getEmail());
        }

        UserContact contact = new UserContact(payload.getFirstName(), payload.getLastName(), phone, email);

        User user = new User(payload.getUsername(), passwordEncoder.encode(payload.getPassword()), contact, payload.getAuthorities());
        userRepository.saveAndFlush(user);
    }
}
