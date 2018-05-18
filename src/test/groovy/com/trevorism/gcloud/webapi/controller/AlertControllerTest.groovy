package com.trevorism.gcloud.webapi.controller

import com.trevorism.event.EventProducer
import org.junit.Test

import javax.ws.rs.core.HttpHeaders

/**
 * @author tbrooks
 */
class AlertControllerTest {

    @Test
    void testSendAlert() {
        AlertController ac = new AlertController()
        ac.eventProducer = [sendEvent:{e,c,s ->}] as EventProducer
        assert ac.sendAlert([getHeaderString:{s -> ""}] as HttpHeaders, [:])


    }
}
