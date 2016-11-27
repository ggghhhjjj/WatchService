/*
 * Copyright (c) 2016, George Shumakov <george.shumakov@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following ™disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package george.watch.service;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 * A NIO watch service example.
 * 
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class Main extends Application {

    private static Logger LOG = Logger.getLogger(Main.class.getName());

    public Main() {
        super();
    }

    public static void main(String[] args) {
        launch(args); 
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final Parameters params = getParameters();
        final List<String> parameters = params.getRaw();
        parameters.forEach((parameter) -> {
            LOG.info(parameter);
            System.out.println("sout: " + parameter);
        });
        stage.close();
        Platform.setImplicitExit(true);
        Platform.exit();
    }

}
