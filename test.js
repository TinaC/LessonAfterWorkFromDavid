

// A[i]

// var gap[];

// var k = 0;
// for(var i = 0;i<A.length-1; i++) {
// 	gap[i] = A[i+1] - A[i];
// 	B[k] = A[i] 
// }

// B[i] = 
// 6(7,8,9)10 (11,12,13) 14 15 (16,17,18)19
var A = [6,10,14,15,19]
getRandomMissingNum(A);

function getRandomMissingNum(A) {
	// var A = new Array(7,10,11,15);
	// var A = new Array(0,10,14,15);
	
	var count = 0;
	for(var i=A[0]; i < A[A.length-1] - A.length+1; i++) {
		console.log(findB(count,A));
		count++ ;
	}

	// // 15 - 0 -4 = 11
	// // var Blength = Math.max.apply(null, A) - Math.min.apply(null, A) - A.length + 1;
	// var Blength = A[A.length-1] - A[0] - A.length + 1;
	// // console.log(Blength);
	// var index = Math.floor(Math.random()*Blength);
	// console.log(findB(index));
	// return findB(index,A);
}


function findB(index,A) {
	var gapsum = A[1] - A[0] -1 ;//3
	// index = 3
	for(var i = 0; i <  A.length-1; i++) {
		console.log("gapsum:" + gapsum);
		// 3 < 6
		if(index < gapsum ){
			// 6+ 3 +1
		// i= 
			if(i>1)
				return A[i] + (index - (A[i] - A[i-1])) + 1;
			else 
				return A[i] + index + 1;
		}
		else 
			gapsum += A[i+1] - A[i] -1;
	}
}
	// var gap = new Array;
	// var gapsum = new Array;

	// gap[0] =  A[1] - A[0] -1;
	// gapsum[0] = 0;
	// gapsum[1] = gap[0];
	// for(var i = 1; i<A.length; i++){
	// 	if(i<A.length-1)
	// 		gap[i] = A[i+1] - A[i]-1;
	// 	gapsum[i] = gap[i-1] + gapsum[i-1];
	// }

	// console.log("gap:"+gap);
	// console.log("gapsum:"+gapsum);

	// for(var x = 0; x<gapsum.length; x++) {
	// 	// console.log("x"+x)
	// 	// console.log("gapsum[x]"+gapsum[x])
	// 	// console.log("gapsum[x+1]"+gapsum[x+1])
	// 	if((k+1) > gapsum[x] && k+1 <= gapsum[x+1]){
	// 		var index = k+1-gapsum[x];
	// 		// console.log("=======");
	// 		// console.log(A[x] + index);
	// 		// console.log("=======");
	// 		return A[x] + index;
	// 	}
	// }

	// for(var i = 0;i<A.length-1; i++) {
	// 	gap[i] = A[i+1] - A[i]-1;
	// } 


	// gapsum[0] = 0;
	// gapsum[1] = A[1] - A[0] -1;
	// // gapsum[1] = gap[0];
	// for(var j = 2; j< gap.length+1; j++) {
	// 	gapsum[j] = gap[j-1] + gapsum[j-1];

	// }
// }

