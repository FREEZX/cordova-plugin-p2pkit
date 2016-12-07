var exec = require('cordova/exec');

exports.coolMethod = function(arg0, success, error) {
    exec(success, error, "p2pkit", "coolMethod", [arg0]);
};

exports.isP2PServicesAvailable = function(success, error) {
	exec(success, error, 'p2pkit', 'isP2PServicesAvailable');
};

exports.enableP2PKit = function(apikey, success, error){
	exec(success, error, 'p2pkit', 'enableP2PKit', [apikey]);
};

exports.createP2pDiscoveryListener = function(opts){
	exec(function(json) {
		console.log('success called '+json);
		var data = JSON.parse(json);
		if(opts[data.type]) {
			opts[data.type](data);
		}
	}, null, 'p2pkit', 'createP2pDiscoveryListener');
};