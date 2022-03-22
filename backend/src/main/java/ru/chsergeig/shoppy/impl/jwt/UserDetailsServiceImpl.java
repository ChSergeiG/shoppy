package ru.chsergeig.shoppy.impl.jwt;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.AccountRoleRepository;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.model.JwtUserDetails;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        List<Accounts> accounts = accountRepository.fetchByLogin(username);
        if (accounts == null || accounts.isEmpty()) {
            throw new UserPrincipalNotFoundException(username);
        }
        List<AccountRole> roles = accountRoleRepository.fetchRolesByLogin(username);
        return new JwtUserDetails(
                accounts.get(0).getId().longValue(),
                accounts.get(0).getLogin(),
                passwordEncoder.encode(accounts.get(0).getPassword()),
                roles.stream().map(Object::toString).collect(Collectors.toList())
        );
    }
}
