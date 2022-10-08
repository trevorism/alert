package com.trevorism.gcloud.webapi.controller

import com.trevorism.EmailClient
import com.trevorism.https.SecureHttpClient
import com.trevorism.model.Alert
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
        EmailClient client = new EmailClient([post: { x, y, z -> "{}" }] as SecureHttpClient)
        ac.emailClient = client
        Email result = ac.sendAlert([getHeaderString:{ s -> null}] as HttpHeaders, new Alert())

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject
        assert result.body.contains("For logs, check")
    }

    @Test
    void testSendAlertWithCorrelationIdAndData() {
        AlertController ac = new AlertController()
        EmailClient client = new EmailClient([post: { x, y, z -> "{}" }] as SecureHttpClient)
        ac.emailClient = client
        Email result = ac.sendAlert([getHeaderString:{ s -> "432"}] as HttpHeaders, new Alert(body: "test body"))

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject.contains("432")
        assert result.body.contains("test body")
    }
}
