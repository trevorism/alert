package com.trevorism.gcloud.webapi.controller

import io.swagger.annotations.ApiModelProperty

/**
 * @author tbrooks
 */
class Email {
    @ApiModelProperty(value = "The subject of the email", dataType = "string")
    String subject

    @ApiModelProperty(value = "A list of email addresses to receive the email")
    List<String> recipients

    @ApiModelProperty(value = "The content of the email", dataType = "string")
    String body
}
