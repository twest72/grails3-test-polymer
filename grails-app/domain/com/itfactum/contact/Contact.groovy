package com.itfactum.contact

import org.grails.databinding.BindingFormat

/**
 * Created by westphal on 08.06.15.
 */
class Contact {

    String firstName
    String lastName

    @BindingFormat('yyyy-MM-dd')
    Date birthDate

    static hasMany = [addresses: Address]

    static constraints = {
        firstName(required: true)
        lastName(required: true)

        birthDate(nullable: true)
    }
}