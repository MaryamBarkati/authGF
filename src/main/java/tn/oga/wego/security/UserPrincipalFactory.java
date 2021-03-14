package tn.oga.wego.security;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import tn.oga.wego.model.User;


public class UserPrincipalFactory {
public static UserPrincipal build(User user){
	
    List<GrantedAuthority> authorities =
    user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
    return new UserPrincipal(user.getEmail(), user.getPassword(), authorities);
}
}

