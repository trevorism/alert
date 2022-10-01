package com.trevorism.gcloud.webapi.controller

import com.trevorism.EmailClient
import com.trevorism.https.SecureHttpClient
import com.trevorism.model.Email
import org.junit.Test

import javax.ws.rs.core.HttpHeaders

/**
 * @author tbrooks
 */
class AlertControllerTest {

    @Test
    void testSendAlert() {
        AlertController ac = new AlertController()
        EmailClient client = new EmailClient([post: { x, y, z -> "true" }] as SecureHttpClient)
        ac.emailClient = client
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
        EmailClient client = new EmailClient([post: { x, y, z -> "true" }] as SecureHttpClient)
        ac.emailClient = client
        Email result = ac.sendAlert([getHeaderString:{ s -> "432"}] as HttpHeaders, ["test":"value"])

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject.contains("432")
        assert result.body.contains("[test:value]")
        assert result.body.contains("Correlation ID: 432")
    }
}
