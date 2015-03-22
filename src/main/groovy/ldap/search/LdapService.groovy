package ldap.search

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ldap.NamingException
import org.springframework.ldap.core.AttributesMapper
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.filter.AndFilter
import org.springframework.ldap.filter.EqualsFilter
import org.springframework.ldap.filter.LikeFilter
import org.springframework.stereotype.Service

import javax.naming.directory.Attributes

@Service
class LdapService {

    @Autowired
    LdapTemplate ldapTemplate

    def search(String name) {
        AndFilter filter = new AndFilter()
        filter.and(new EqualsFilter("objectClass", "person"))
        filter.and(new LikeFilter("cn", "*${name}*"))
        println filter.encode()
        ldapTemplate.search("",
                filter.encode(),
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        return attrs.get("cn").get()
                    }
                })
    }
}
