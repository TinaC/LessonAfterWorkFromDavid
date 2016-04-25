cordova.define('cordova/plugin/my_own_plugin', function(require, exports, module) {
    var exec = require('cordova/exec');

    var MyOwnPlugin = function() {};

    MyOwnPlugin.prototype.doSomething = function(onSuccess, onFailed) {
        exec(onSuccess, onFailed, 'MyOwnPlugin', 'do_something', []);
    }

    MyOwnPlugin.prototype.doAnotherThing = function(parameter, onSuccess, onFailed) {
        exec(onSuccess, onFailed, 'MyOwnPlugin', 'do_another_thing', [parameter]);
    }

    MyOwnPlugin.prototype.doOtherThings = function(param1, param2, onSuccess, onFailed) {
        exec(onSuccess, onFailed, 'MyOwnPlugin', 'do_other_things', [param1, param2]);
    }

    module.exports = new MyOwnPlugin();
});