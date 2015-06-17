package com.itfactum

import grails.converters.JSON

class PolymerController {

    def index() {}

    def chartData() {
        List data = [
                ["Month", "Days"],
                ["Jan", 31],
                ["Feb", 28],
                ["Mar", 31],
                ["Apr", 30],
                ["May", 31],
                ["June", 30],
                ["July", 31],
                ["Aug", 31],
                ["Sept", 30],
                ["Oct", 31],
                ["Nov", 30],
                ["Dec", 31],
        ]
//        render data as JSON
        render(contentType: 'application/json') { data }
    }

    def contact() {
        Map data = [
                firstName: 'Thomas',
                lastName : 'Westphal',
                address  : [
                        street: 'Käthe-Kollwitz-Straße 52'
                ]

        ]
        render data as JSON
    }
}
