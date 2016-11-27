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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
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
        final String parameter = params.getNamed().get("folder");
        LOG.info(parameter);
        Path path = Paths.get(parameter);
        WatchService watchService = null;
        try {
            watchService = path.getFileSystem().newWatchService();
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        final long l1 = System.currentTimeMillis();
        do {
            WatchKey key = null;
            try {
                key = watchService.take(); //wait for change
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (WatchEvent event : key.pollEvents()) {
                switch (event.kind().name()) {
                    case "OVERFLOW":
                        System.out.println("We lost some events");
                        break;
                    case "ENTRY_CREATE":
                        System.out.println("File " + event.context()
                                + " is created!");
                        break;
                    case "ENTRY_MODIFY":
                        System.out.println("File " + event.context()
                                + " is modified!");
                        break;
                    case "ENTRY_DELETE":
                        System.out.println("File " + event.context()
                                + " is deleted!");
                        break;
                }
            }
            key.reset();
        } while (System.currentTimeMillis() - l1 < 60000);
        stage.close();
        Platform.setImplicitExit(true);
        Platform.exit();
    }

}
