package ru.chsergeig.shoppy.impl.jwt;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.AccountRoleRepository;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.model.JwtUserDetails;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
//    private final SecurityProperties securityProperties;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        List<Accounts> accounts = accountRepository.fetch(
                ACCOUNTS.LOGIN,
                username
        );
        if (accounts.isEmpty()) {
            throw new UserPrincipalNotFoundException(username);
        }
        Accounts account = accounts.get(0);
//        if (!account.getSalted()) {
//            account.setPassword(TokenUtils.saltValue(securityProperties, account.getPassword()));
//            account.setSalted(true);
//            accountRepository.update(account);
//        }
        List<AccountRole> roles = accountRoleRepository.fetchRolesByLogin(username);
        return new JwtUserDetails(
                account.getId().longValue(),
                account.getLogin(),
                account.getPassword(),
                roles.stream().map(Object::toString).collect(Collectors.toList())
        );
    }
}
