import com.itfactum.contact.Address
import com.itfactum.contact.AddressType
import com.itfactum.contact.Contact
import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.core.GrailsApplication
import org.grails.web.converters.configuration.DefaultConverterConfiguration
import org.grails.web.converters.marshaller.json.DeepDomainClassMarshaller

import javax.servlet.ServletContext

@GrailsCompileStatic
class BootStrap {

    GrailsApplication grailsApplication

    def init = { ServletContext servletContext ->

        JSON.createNamedConfig('deep') { DefaultConverterConfiguration converterConfiguration ->
            converterConfiguration.registerObjectMarshaller(new DeepDomainClassMarshaller(false, grailsApplication))
            converterConfiguration.registerObjectMarshaller(Date) { Date date ->
                return date?.format("yyyy-MM-dd")
            }
        }

        new Contact(
                firstName: 'Thomas',
                lastName: 'Westphal',
                birthDate: new Date(),
                addresses: [new Address(
                        addressType: AddressType.HOME,
                        street: 'Käthe-Kollwitz-Straße 52',
                        city: 'Halle',
                        zip: '06116',
                        country: 'Germany'
                )]
        ).save(failOnError: true)

        new Contact(
                firstName: 'Heike',
                lastName: 'Westphal',
                birthDate: new Date(),
                addresses: [new Address(
                        addressType: AddressType.HOME,
                        street: 'Käthe-Kollwitz-Straße 52',
                        city: 'Halle',
                        zip: '06116',
                        country: 'Germany'
                )]
        ).save(failOnError: true)

        new Contact(
                firstName: 'Karl Lennart',
                lastName: 'Westphal',
                birthDate: new Date(),
                addresses: [new Address(
                        addressType: AddressType.HOME,
                        street: 'Käthe-Kollwitz-Straße 52',
                        city: 'Halle',
                        zip: '06116',
                        country: 'Germany'
                )]
        ).save(failOnError: true)
    }

    def destroy = {
    }
}
