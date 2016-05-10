window.echo = function(str, callback) {
    cordova.exec(callback, function(err) {
        callback('!!Nothing to echo.' + err);
    }, "Echo", "echo", [str]);
};
