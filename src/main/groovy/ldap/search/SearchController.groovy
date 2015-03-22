package ldap.search

import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController {

    @Autowired
    LdapService ldapService

    @RequestMapping(value = "/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String search(@PathVariable String name) {
        return new JsonBuilder(ldapService.search(name)).toPrettyString()
    }

    @RequestMapping("/")
    def index() {
        "LDAP Search service is alive!"
    }
}
