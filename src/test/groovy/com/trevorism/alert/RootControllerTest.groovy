package com.trevorism.alert


import org.junit.jupiter.api.Test

/**
 * @author tbrooks
 */
class RootControllerTest {

    @Test
    void testRootControllerEndpoints(){
        com.trevorism.alert.RootController rootController = new com.trevorism.alert.RootController()
        assert rootController.index().getBody().get()[0].contains("ping")
        assert rootController.index().getBody().get()[1].contains("help")
    }

    @Test
    void testRootControllerPing(){
        com.trevorism.alert.RootController rootController = new com.trevorism.alert.RootController()
        assert rootController.ping() == "pong"
    }

    @Test
    void testRootControllerHelpPage(){
        com.trevorism.alert.RootController rootController = new com.trevorism.alert.RootController()
        assert rootController.help()
    }
}
