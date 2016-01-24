/*jslint nomen: true, node: true*/
(function (electron) {
    "use strict";

    var app = electron.app,
        BrowserWindow = electron.BrowserWindow,
        mainWindow;

    function createWindow() {
        mainWindow = new BrowserWindow({width: 800, height: 600});
        mainWindow.loadURL("file://" + __dirname + "/target/cljsbuild/public/index.html");
        // mainWindow.webContents.openDevTools();
        mainWindow.on("closed", function () {
            mainWindow = null;
            app.quit();
        });
    }

    app.on("ready", createWindow);

    app.on("window-all-closed", function () {
        if (process.platform !== "darwin") {
            app.quit();
        }
    });

    app.on("activate", function () {
        if (mainWindow === null) {
            createWindow();
        }
    });
}(
    require("electron")
));
