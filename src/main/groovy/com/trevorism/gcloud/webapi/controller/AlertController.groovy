package com.trevorism.gcloud.webapi.controller

import com.trevorism.event.EventProducer
import com.trevorism.event.PingingEventProducer
import com.trevorism.event.model.WorkComplete
import com.trevorism.http.headers.HeadersHttpClient
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
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
    EventProducer<Email> eventProducer = new PingingEventProducer<>()

    @ApiOperation(value = "Send an alert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    boolean sendAlert(@Context HttpHeaders headers, Map inputData) {
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        if(!correlationId)
            correlationId = UUID.randomUUID().toString()

        log.info("Sending an alert with correlationId: ${correlationId}")

        Email data = createEmail(inputData, correlationId)
        eventProducer.sendEvent("email", data, correlationId)
        return true
    }

    private Email createEmail(Map inputData, String correlationId) {
        def data = new Email()
        data.recipients = ["alerts@trevorism.com"]
        data.subject = (inputData["subject"]) ? (inputData["subject"]).toString() : "Alert: ${correlationId}"
        data.body = (inputData["body"]) ? (inputData["body"]).toString() : buildAlertText(correlationId, inputData)
        data
    }

    private static String buildAlertText(String correlationId, Map inputData) {
        "${inputData}\nCorrelation ID: ${correlationId}"
    }
}
