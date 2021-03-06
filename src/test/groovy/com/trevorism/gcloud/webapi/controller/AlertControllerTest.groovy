package com.trevorism.gcloud.webapi.controller

import com.trevorism.event.EventProducer
import com.trevorism.gcloud.model.Email
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
        Email result = ac.sendAlert([getHeaderString:{ s -> null}] as HttpHeaders, [:])

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject

        assert result.body.contains("[:]")
        assert result.body.contains("Correlation ID:")
    }

    @Test
    void testSendAlertWithCorrelationIdAndData() {
        AlertController ac = new AlertController()
        ac.eventProducer = [sendEvent:{e,c,s ->}] as EventProducer
        Email result = ac.sendAlert([getHeaderString:{ s -> "432"}] as HttpHeaders, ["test":"value"])

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject.contains("432")
        assert result.body.contains("[test:value]")
        assert result.body.contains("Correlation ID: 432")
    }
}
