package com.itfactum.contact
/**
 * Created by westphal on 08.06.15.
 */
class Address {

    AddressType addressType
    String street
    String city
    String zip
    String state
    String country

    static constraints = {
        addressType(required: true)

        street(nullable: true)
        city(nullable: true)
        zip(nullable: true)
        state(nullable: true)
        country(nullable: true)
    }
}