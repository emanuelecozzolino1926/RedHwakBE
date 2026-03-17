package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Utente;
import emanueleCozzolino.redhawkbe.exceptions.UnauthorizedException;
import emanueleCozzolino.redhawkbe.payload.LoginDTO;
import emanueleCozzolino.redhawkbe.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtenteService utenteService;
    private final JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public AuthService(UtenteService utenteService, JWTTools jwtTools) {
        this.utenteService = utenteService;
        this.jwtTools = jwtTools;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente found = this.utenteService.findByUsername(body.username());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("Le credenziali inserite sono errate!");
        }
    }
}
