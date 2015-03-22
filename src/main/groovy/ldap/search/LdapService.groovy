package ldap.search

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ldap.NamingException
import org.springframework.ldap.core.AttributesMapper
import org.springframework.ldap.core.AttributesMapperCallbackHandler
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.filter.AndFilter
import org.springframework.ldap.filter.EqualsFilter
import org.springframework.ldap.filter.LikeFilter
import org.springframework.stereotype.Service

import javax.naming.SizeLimitExceededException
import javax.naming.directory.Attributes
import javax.naming.directory.SearchControls

@Service
class LdapService {

    @Autowired
    LdapTemplate ldapTemplate

    def search(String name) {
        AndFilter filter = new AndFilter()
        filter.and(new EqualsFilter("objectClass", "person"))
        filter.and(new LikeFilter("cn", "*${name}*"))
        println filter.encode()

        def searchPersonLdapAttributeMapper = new AttributesMapper() {
            public Object mapFromAttributes(Attributes attrs) throws NamingException {
                return attrs.get("cn").get()
            }
        }

        def searchControls = new SearchControls()
        searchControls.countLimit = 5
        searchControls.timeLimit = 0
        searchControls.searchScope = SearchControls.SUBTREE_SCOPE
        
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(searchPersonLdapAttributeMapper);
        try {
            ldapTemplate.search("",filter.encode(),searchControls, handler);
        } catch (ex) {
            // safely ignore. Spring LDAP handles this exception different to SUNs implementation.
            println "SizeLimitExceededException caught in  PersonLdapDAOImpl.findUserByName"
        }
        handler.list
    }
}
