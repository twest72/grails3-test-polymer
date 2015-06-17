package com.itfactum.contact

import grails.converters.JSON
import grails.web.controllers.ControllerMethod
import org.hibernate.Hibernate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.security.access.prepost.PreAuthorize

class ContactSocketController {

    final def dateFormats = ['yyyy-MM-dd']
    SimpMessagingTemplate brokerMessagingTemplate

    @ControllerMethod
    @PreAuthorize("hasRole('ROLE_USER')")
    @MessageMapping("/contact/list")
    void listWs(String parameterText) {

        def parameter = JSON.parse(parameterText)
        int max = Math.min(parameter.max ?: 10, 100)
        sendListWs(max)
    }

    @ControllerMethod
    @PreAuthorize("hasRole('ROLE_USER')")
    //@SendTo("/topic/contact/list")
    void sendListWs(int max) {

        String responseText
        Contact.withTransaction {
            responseText = list(max)
        }

        brokerMessagingTemplate.convertAndSend "/topic/contact/list", responseText
        //return responseText
    }

    @ControllerMethod
    @MessageMapping("/contact/show")
    @SendToUser("/queue/contact/show")
    String showWs(String parameterText) {

        def parameter = JSON.parse(parameterText)
        int id = parameter.id
        String siteUid = parameter.siteUid

        String responseText
        Contact.withTransaction {
            responseText = show(id)
        }
        return responseText
    }

    private String list(int max) {

        Map response = [
                contactList : Contact.list([max: max]),
                contactCount: Contact.count(),
        ]

        return response as JSON
    }

    private String show(int id) {
        Contact contact = Contact.findById(id)
        Map response = [:]
        if (contact == null) {
            response.error = true;
            response.message = "Contact with id $id not found";
        } else {
            Hibernate.initialize contact.addresses
            JSON.use('deep') {
                response.ok = true
                response.contact = contact
            }
        }
        return response as JSON
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    def save() {

        Map jsonContact = request.JSON
        println jsonContact

        Contact contact
        if (jsonContact.id) {
            contact = Contact.findById(jsonContact.id)
        }

        if (contact) {
            contact.setProperties(jsonContact)
        } else {
            contact = new Contact(jsonContact)
        }

        contact.save(failOnError: true, flush: true)

        sendListWs(20)

        Map result = [
                id : contact.id,
                ok : true,
                rev: contact.version
        ]
        render result as JSON
    }
}
