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
    EventProducer<Map> eventProducer = new PingingEventProducer<>()

    @ApiOperation(value = "Send an alert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    boolean sendAlert(@Context HttpHeaders headers, Map data) {
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        log.info("Sending an alert with correlationId: ${correlationId}")

        data["recipients"] = ["alerts@trevorism.com"]
        data["subject"] = (data["subject"]) ?: "Alert: ${correlationId}"
        data["body"] = (data["body"]) ?: buildAlertText(correlationId, data)


        eventProducer.sendEvent("email", data, correlationId)
    }

    private static String buildAlertText(String correlationId, Map data) {
        "${data}\nCorrelation ID: ${correlationId}"
    }
}
