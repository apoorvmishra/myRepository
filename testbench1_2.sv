module testbench1_2();
logic [7:0]A;
logic [3:0]B;
logic op;
logic [7:0]Y;
reg clk;
int total_cases = 10;
logic [7:0]expectedY;
logic [31:0]i, error;
logic [20:0] tstvctr[1000:0];
part1part2 dut(A, B, op, Y);
always
	begin
		clk = 1; #10; clk = 0; #10; 
	end
initial
	begin
		$readmemb("part1part2.tv", tstvctr);
		i=0; error=0;
	end
always @(posedge clk)
	begin
		{op,A,B,expectedY} = tstvctr[i]; #10;
		$display("%d, %d, %d, %d",op,A,B,expectedY);
	end
always @(negedge clk)
	begin
		if(expectedY !== Y) begin
			$display("Incorrect output for Y for inputs %d, %d", A, B);
			error = error + 1;
		end
		i=i+1;
		if(i === total_cases) begin
			$display("Finished %d completed tests with %d errors", i, error);
			$finish;
		end
	end
endmodule
