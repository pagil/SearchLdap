package ldap.search

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.core.support.LdapContextSource

@Configuration
@PropertySource("classpath:ldap.properties")
class LdapConfig {

    @Value('${sample.ldap.url}')
    String ldapUrl
    @Value('${sample.ldap.base}')
    String ldapBase
    @Value('${sample.ldap.userDn}')
    String ldapUserDn
    @Value('${sample.ldap.password}')
    String ldapPassword

    @Bean
    LdapContextSource ldapCtxSrc() {
        println ldapUrl
        println ldapBase
        println ldapUserDn
        println ldapPassword
        new LdapContextSource(url: ldapUrl,
                base: ldapBase,
                userDn: ldapUserDn,
                password: ldapPassword)
    }

    @Bean
    LdapTemplate ldapTemplate() {
        new LdapTemplate(ldapCtxSrc())
    }

    /**
     * As opposed to using XML namespace element, the Java @PropertySource annotation does not
     * automatically register a PropertySourcesPlaceholderConfigurer with Spring. Instead, 
     * the bean must be explicitly defined in the configuration to get the property resolution
     * mechanism working. The reasoning behind this unexpected behavior is by design and
     * documented on this issue.
     *  
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
