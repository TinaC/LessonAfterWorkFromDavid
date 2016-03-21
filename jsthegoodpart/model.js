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

// 由于语言的一个设计错误，js函数中的arguments并不是真正的数组，它只是一个“类似数组”的对象，拥有一个length属性，但它没有任何数组的方法。所以需要两个arguments数组上都应用数组的slice方法
Function.method('curry', function () {
	var slice = Array.prototype.slice,
		args = slice.apply(arguments),
		that = this;
	return function () {
		return that.apply(null, args.concat(slice.apply(arguments)));
	}
})

// momemoizer
var fibonacci = function () {
	var memo = [0,1];
	var fib = function (n) {
		var result = memo[n];
		if (typeof result !== 'number') {
			result = fib(n-1) + fib(n-2);
			memo[n] = result;
		}
		return result;
	}
}

for(var i = 0; i <= 10; i++){
	document.writeln("//" + i + ":" + fibonacci(i));
}