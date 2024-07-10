package com.trevorism.alert

import com.trevorism.EmailClient
import com.trevorism.https.SecureHttpClient
import com.trevorism.model.Alert
import com.trevorism.model.Email
import io.micronaut.http.HttpHeaders
import org.junit.jupiter.api.Test


/**
 * @author tbrooks
 */
class AlertControllerTest {

    @Test
    void testSendAlert() {
        AlertController ac = new AlertController([post: { x, z -> "{}" }] as SecureHttpClient)
        Email result = ac.sendAlert(new Alert())

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject
    }

    @Test
    void testSendAlertWithCorrelationIdAndData() {
        AlertController ac = new AlertController([post: { x, z -> "{}" }] as SecureHttpClient)
        Email result = ac.sendAlert(new Alert(body: "test body"))

        assert result
        assert result.recipients
        assert result.recipients[0] == "alerts@trevorism.com"
        assert result.subject
        assert result.body.contains("test body")
    }
}
