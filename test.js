

// A[i]

// var gap[];

// var k = 0;
// for(var i = 0;i<A.length-1; i++) {
// 	gap[i] = A[i+1] - A[i];
// 	B[k] = A[i] 
// }

// B[i] = 

var A = new Array(7,10,11,15);
var A = new Array(0,10,14,15);
var count = 0;
for(var i=A[0]; i < A[A.length-1] - A.length+1; i++) {
	findB(count);
	count++ ;
}


function findB(k) {
	var gap = new Array;
	var gapsum = new Array;

	for(var i = 0;i<A.length-1; i++) {

		gap[i] = A[i+1] - A[i]-1;
		
	} 
	// console.log("gap:"+gap);

	gapsum[0] = 0;
	gapsum[1] = gap[0];
	for(var j = 2; j< gap.length+1; j++) {
		gapsum[j] = gap[j-1] + gapsum[j-1];

	}
	// console.log("gapsum:"+gapsum);

	for(var x = 0; x<gapsum.length; x++) {
		// console.log("x"+x)
		// console.log("gapsum[x]"+gapsum[x])
		// console.log("gapsum[x+1]"+gapsum[x+1])
		if((k+1) > gapsum[x] && k+1 <= gapsum[x+1]){
			var index = k+1-gapsum[x];
			// console.log("=======");
			console.log(A[x] + index);
			// console.log("=======");
			return A[x] + index;
		}
	}
}

