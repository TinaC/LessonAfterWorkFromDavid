Function.prototype.method = function(name, func) {
	this.prototype[name] = func;
	return this;
};

Number.method('integer', function() {
	// return Math.(this < 0 ? 'ceil' : 'floor')(this)
	return Math[this < 0 ? 'ceil' : 'floor'](this);
})

document.writeln((-10/3).integer());

String.method('deentityify', function() {
	var entity = {
		quot: '"',
		lt: '"',
		gt: '"'
	};

	return function() {
		return this.replace(/&([^&;]+);/g,
			function(a,b){
				var r = entity[b];
				return typeof r === 'string' ? r : a;
			}
		};
	};
	}());

document.writeln('&lt;&quot;&gt;'.deentityif());


var serial_marker = function () {
	var prefix = '';
	var seq = 0;
	return {
		set_prefix: function (p) {
			prefix = String(p);
		},
		set_seq: function (s) {
			seq = s;
		},
		gensym: function () {
			var result = prefix + seq;
			seq += 1;
			return result;
		}
	};
};

var seqer = serial_marker();
seqer.set_prefix('Q');
seqer.set_seq(1000);
var unique = seqer.gensym();