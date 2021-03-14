package tn.oga.wego.controller;




import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import tn.oga.wego.dto.TokenDto;
import tn.oga.wego.enums.RolNombre;
import tn.oga.wego.security.jwt.JwtProvider;
import tn.oga.wego.service.RolService;
import tn.oga.wego.service.UserService;
import tn.oga.wego.model.Rol;
import tn.oga.wego.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/oauth")
@CrossOrigin
public class OauthController {

    String googleClientId="988959612827-3o0ojv231c21cctl9lfcghjgu1ansfa7.apps.googleusercontent.com";

    String secretPsw="kasdjhfkadhsY776ggTyUU65khaskdjfhYuHAwjñlji";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;



    @PostMapping("/google")
    public ResponseEntity<TokenDto> google(@RequestBody TokenDto tokenDto) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDto.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        User user = new User();
        if(userService.existsEmail(payload.getEmail()))
            user = userService.getByEmail(payload.getEmail()).get();
        else
            user = saveUser(payload.getEmail());
        TokenDto tokenRes = login(user);
        return new ResponseEntity(tokenRes, HttpStatus.OK);
    }

    @PostMapping("/facebook")
    public ResponseEntity<TokenDto> facebook(@RequestBody TokenDto tokenDto) throws IOException {
        Facebook facebook = new FacebookTemplate(tokenDto.getValue());
        final String [] fields = {"email", "picture"};
        User user = facebook.fetchObject("me", User.class, fields);
        User user1 = new User();
        if(userService.existsEmail(user1.getEmail()))
            user1 = userService.getByEmail(user1.getEmail()).get();
        else
            user1 = saveUser(user1.getEmail());
        TokenDto tokenRes = login(user1);
        return new ResponseEntity(tokenRes, HttpStatus.OK);

    }

    private TokenDto login(User user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), secretPsw)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setValue(jwt);
        return tokenDto;
    }
    
	@PostMapping("/save")
    private User saveUser(String email){
        User user = new User(email, passwordEncoder.encode(secretPsw));
        Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        user.setRoles(roles);
        return userService.saveUser(user);
    }

}