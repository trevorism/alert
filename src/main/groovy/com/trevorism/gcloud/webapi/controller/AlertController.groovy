package com.trevorism.gcloud.webapi.controller

import com.trevorism.EmailClient
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.model.Alert
import com.trevorism.model.Email
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import java.util.logging.Logger

/**
 * @author tbrooks
 */
@Api("Alert Operations")
@Path("/alert")
class AlertController {

    private static final Logger log = Logger.getLogger(AlertController.class.name)
    EmailClient emailClient = new EmailClient()

    @ApiOperation(value = "Send an alert")
    @POST
    @Secure(Roles.USER)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Email sendAlert(@Context HttpHeaders headers, Alert inputData) {
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        if(!correlationId)
            correlationId = UUID.randomUUID().toString()

        log.info("Sending an alert with correlationId: ${correlationId}")

        Email data = createEmail(inputData, correlationId)
        emailClient.sendEmail(data, correlationId)
        return data
    }

    private Email createEmail(Alert inputData, String correlationId) {
        def data = new Email()
        data.recipients = ["alerts@trevorism.com"]
        data.subject = (inputData.subject) ? (inputData.subject).toString() : "Alert: ${correlationId}"
        data.body = (inputData.body) ? (inputData.body).toString() : buildAlertText(correlationId)
        data
    }

    private static String buildAlertText(String correlationId) {
        "Check logs for correlation id: ${correlationId}"
    }
    
}
