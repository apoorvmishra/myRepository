/*Integrating two functionalities of ALU in a single module. First functionality is Square and Modulus and second is Cellular Automata*/
module part1part2( input logic [7:0]A, input logic [3:0]B, input logic op,
		output logic [7:0]Y);

always_comb begin
case(op)
	
	1'b0:   Y = (B*B)%A;
 	
	1'b1:	
			foreach(A[idx]) begin
				
				if (A[(idx+1)%8] == 0 && A[idx] == 0)
					begin
						 Y[idx] = B[0];
					end
				else if (A[(idx+1)%8] == 0 && A[idx] == 1)
					begin
						 Y[idx] = B[1];
					end
				else if (A[(idx+1)%8] == 1 && A[idx] == 0)
					begin
						 Y[idx] = B[2];
					end
				else if (A[(idx+1)%8] == 1 && A[idx] == 1)
					begin
						 Y[idx] = B[3];
					end
			end
		
		
 endcase
end
endmodule
