package ldap.search

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.core.support.LdapContextSource

@Configuration
class LdapConfig {

    @Bean
    LdapContextSource ldapCtxSrc() {
        new LdapContextSource(
                url: 'ldap://127.0.0.1:10389',
                base: 'dc=example,dc=com',
                userDn: 'uid=admin,ou=system',
                password: 'secret')
    }

    @Bean
    LdapTemplate ldapTemplate() {
        new LdapTemplate(ldapCtxSrc())
    }

}
