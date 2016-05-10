window.hitoe_echo = function(str, callback) {
    cordova.exec(callback, function(err) {
        callback('!!Nothing to echo.' + err);
    }, "Hitoe_SDK", "hitoe_echo", [str]);
};

window.init = function(str, callback) {
    cordova.exec(callback, function(err) {
        callback('!!Nothing to echo.' + err);
    }, "Hitoe_SDK", "init", [str]);
};

