package com.trevorism.alert

import com.trevorism.EmailClient
import com.trevorism.https.SecureHttpClient
import com.trevorism.model.Alert
import com.trevorism.model.Email
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.logging.Logger

@Controller("/alert")
class AlertController {

    private static final Logger log = Logger.getLogger(AlertController.class.name)
    EmailClient emailClient

    AlertController(SecureHttpClient secureHttpClient) {
        this.emailClient = new EmailClient(secureHttpClient)
    }

    @Tag(name = "Alert Operations")
    @Operation(summary = "Send an alert")
    @Post(value = "/", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @Secure(value = Roles.USER, allowInternal = true)
    Email sendAlert(HttpHeaders headers, @Body Alert inputData) {
        String correlationId = headers?.getValue("X-Correlation-Id")
        if(!correlationId)
            correlationId = UUID.randomUUID().toString()

        log.info("Sending an alert with correlationId: ${correlationId}")
        Email data = createEmail(inputData, correlationId)
        emailClient.sendEmail(data)
        return data
    }

    private static Email createEmail(Alert inputData, String correlationId) {
        Email data = new Email()
        data.recipients = ["alerts@trevorism.com"]
        data.subject = (inputData.subject) ? (inputData.subject).toString() : "Alert: ${correlationId}"
        data.body = buildAlertText(inputData.body, correlationId)
        return data
    }

    private static String buildAlertText(String body, String correlationId) {
        String firstPart = body ? "${body}\n\n" : ""
        "${firstPart}For logs, check: https://console.cloud.google.com/logs/query;query=${correlationId}?authuser=1&project=trevorism-action"
    }
    
}
