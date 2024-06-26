package com.trevorism.alert


import org.junit.jupiter.api.Test

/**
 * @author tbrooks
 */
class RootControllerTest {

    @Test
    void testRootControllerEndpoints(){
        RootController rootController = new RootController()
        assert rootController.index().getBody().get().contains("ping")
        assert rootController.index().getBody().get().contains("help")
    }

    @Test
    void testRootControllerPing(){
        RootController rootController = new RootController()
        assert rootController.ping() == "pong"
    }

    @Test
    void testRootControllerHelpPage(){
        RootController rootController = new RootController()
        assert rootController.help()
    }
}
