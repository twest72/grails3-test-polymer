package com.itfactum.contact

import grails.converters.JSON
import grails.transaction.Transactional
import org.hibernate.Hibernate
import org.springframework.security.access.prepost.PreAuthorize

@Transactional(readOnly = true)
class ContactController {

    static responseFormats = ['json']
    final def dateFormats = ['yyyy-MM-dd']

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Contact.list(params), model: [contactCount: Contact.count()]
    }

    def show(Integer id) {
        Contact contact = Contact.findById(id)
        if (contact == null) {
            render status: 404
        } else {
            Hibernate.initialize contact.addresses
            JSON.use('deep'){
                render contact as JSON
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    def save() {

        Map jsonContact = request.JSON
        println jsonContact

        Contact contact
        if(jsonContact.id) {
            contact = Contact.findById(jsonContact.id)
        }

        if(contact) {
            contact.setProperties(jsonContact)
        } else {
            contact = new Contact(jsonContact)
        }

        contact.save(failOnError: true, flush: true)

        Map result = [
                id: contact.id,
                ok: true,
                rev: contact.version
        ]
        render result as JSON
    }
}
