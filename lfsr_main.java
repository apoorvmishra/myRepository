package edu.uic.lfsr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class lfsr_main {
	//defining the fields of the 32 bit machine code 
	static ArrayList<Integer> machine_code = new ArrayList<Integer>();
	static ArrayList<Integer> opcode = new ArrayList<Integer>();
	static ArrayList<Integer> r_addr = new ArrayList<Integer>();
	static ArrayList<Integer> loc = new ArrayList<Integer>();
	static ArrayList<Integer> tap = new ArrayList<Integer>();
	static ArrayList<Integer> seed = new ArrayList<Integer>();
	static ArrayList<Integer> num = new ArrayList<Integer>();
	static ArrayList<Integer> numberOfCycles = new ArrayList<Integer>();
	static ArrayList<Integer> hamming = new ArrayList<Integer>();
	static int r_addr_dec = 0;
	static int num_dec = 0;
	static int op = 0;
	static int avg_HD = 0;
	static int hammingDistance;
	static int numberOfCycles_Decimal;
	static ArrayList<Integer> q = new ArrayList<Integer>();
	static ArrayList<Integer> lfsr = new ArrayList<Integer>();
	static ArrayList<Integer> q_prev = new ArrayList<Integer>();
	static ArrayList<ArrayList<Integer>> memory = new ArrayList<ArrayList<Integer>>();
	static ArrayList<Integer> average_hamming = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		String file_name="C:/Users/Apoorv Mishra/UIC/workspace/lfsr/src/edu/uic/lfsr/lfsr5b.txt";//Text file with all machine codes.
		String line = null;
		FileReader fileReader = new FileReader(file_name);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		char[] machine_code_char = null;
		for(int i=0; i<8; i++){
			q_prev.add(0);	//Initialising every field to zero
			seed.add(0);
			lfsr.add(0);
			q.add(0);
			loc.add(0);
			r_addr.add(0);
			num.add(0);
			numberOfCycles.add(0);
			hamming.add(0);
		}
		for(int i=0; i<6; i++){
			opcode.add(0);
		}
		for(int i=0; i<7; i++){
			tap.add(0);
		}
		for(int i=0; i<32; i++){
			machine_code.add(0);
		}
		for(int i=0; i<1000; i++){
			memory.add(q);
		}
		// Reading all the machine codes from the text file
		while((line = bufferReader.readLine())!= null){
			machine_code_char = line.toCharArray();
			for(int i=0; i<machine_code_char.length; i++){
				machine_code.set(i, machine_code_char[i] - '0'); // reading the machine code as binary
			}
			for(int i = 0; i<6; i++){
				opcode.add(i,machine_code.get(i)); // storing the opcode
			}

			op = opcode_decimal(opcode);	// method call to convert 6 bit binary opcode to decimal
			//config_L block
			if(op==1){
				for(int i=0; i<7; i++){
					tap.set(i,machine_code.get(i+25));	//storing the tap value
				}	
			}
			//init_L block
			else if(op==3){	
				for(int i=0; i<8; i++){
					seed.set(i,machine_code.get(i+14));	//storing the seed value
					lfsr.set(i,seed.get(i));	//storing the lfsr content 
					q_prev.set(i,seed.get(i));	//storing the q previous value
				}
			}

			// run_L block
			else if(op==7){
				lfsr_process(1);	//method call for run_L
			}

			//init_add_loc block
			else if(op==42){
				for(int i=0; i<8; i++){
					loc.add(i,machine_code.get(i+6));	//storing the value for loc
					r_addr.set(i, loc.get(i));	//storing the value for r_addr
				}
			}
			//st_M_L block
			else if(op==15){
				r_addr_dec = r_addr_decimal(r_addr);
				store_lfsr();	//store method call
			}

			//block for load
			else if(op==31){
				load_seed();	// load method call
			}

			// add addr_num block
			else if(op==0){
				for(int i=0; i<8; i++){
					num.set(i,machine_code.get(i+14));	// storing the value for num
				}
				add_address();	// add address call
				System.out.print("Memory Content:"+memory.get(r_addr_dec));	//printing the memory content at M[r_addr]
			}

			//multicycle run block
			else if(op==32){
				for(int i=0; i<8; i++){
					numberOfCycles.set(i, machine_code.get(i+6));
				}
				numberOfCycles_Decimal = num_cycle_decimal(numberOfCycles);
				lfsr_process(numberOfCycles_Decimal);
			}

			// store Hamming Distance block
			else if(op==21){
				r_addr_dec = r_addr_decimal(r_addr);
				store_HD();
			}
			
			//calculating average hamming distance
			else if(op==48){
				for(int i=0; i<8; i++){
					numberOfCycles.set(i, machine_code.get(i+6));
					numberOfCycles_Decimal = num_cycle_decimal(numberOfCycles);
				}
				lfsr_process(numberOfCycles_Decimal); // method call for run_L multicycle
				for(int i=0; i<numberOfCycles_Decimal; i++){
					avg_HD = avg_HD + average_hamming.get(i); 
				}
				avg_HD = avg_HD/numberOfCycles_Decimal;
				System.out.println("Average Hamming: " + avg_HD);
			}

			System.out.println("LFSR Content:"+lfsr);	//printing the lfsr content
			opcode.clear();	//clearing the opcode for next cycle
			loc.clear();	//clearing the loc for next cycle
			num_dec=0;
			r_addr_dec = 0;

		}
		bufferReader.close();	
		fileReader.close();
	}

	//method to convert 6 bit binary opcode to decimal 
	public static int opcode_decimal(ArrayList<Integer> opcode1){
		int op1 = 0;
		for(int i=0; i<6; i++){
			op1 = (int) (op1 + opcode1.get(5-i)*Math.pow(2, i));
		}
		return op1;
	}

	//method to convert 8 bit binary r_addr value to decimal 
	public static int r_addr_decimal (ArrayList<Integer> r_addr){
		int reg_dec = 0;
		for(int i=0; i<8; i++){
			reg_dec = (int) (reg_dec + r_addr.get(7-i)*Math.pow(2, i));
		}
		return reg_dec;
	}

	//method to get decimal value for number of cycles
	public static int num_cycle_decimal (ArrayList<Integer> numCycle){
		int cycle_dec = 0;
		for(int i=0; i<8; i++){
			cycle_dec = (int) (cycle_dec + numCycle.get(7-i)*Math.pow(2, i));
		}
		return cycle_dec;
	}

	//method for the run_L process
	public static void lfsr_process(int numCycle){
		for(int j =0; j<numCycle; j++){
			q.set(0, q_prev.get(7));
			for(int i=1; i<8; i++){
				if(tap.get(i-1) == 1){

					q.set(i,((q_prev.get(i-1))^(q_prev.get(7))));
				}
				else{
					q.set(i,(q_prev.get(i-1)));
				}
			}
			// for calculating hamming distances
			hammingDistance = 0;
			for(int i=0; i<8; i++){
				if(q.get(i)!=q_prev.get(i)){
					hammingDistance++;
				}
			}
			average_hamming.add(hammingDistance); // storing the hamming distances for multi cycle
			
			//storing the new q value to lfsr and updating previous q value 
			for(int i=0; i<8; i++){
				lfsr.set(i, q.get(i));
				q_prev.set(i, q.get(i));
			}
		}
		
		//System.out.println("Hamming Distance   " + hammingDistance+ "  "); // Hamming distance
	}

	//method for store lfsr content to memory
	public static void store_lfsr(){
		memory.set(r_addr_dec,lfsr);
		System.out.print("Memory Content:"+memory.get(r_addr_dec));
	}

	//method to store Hamming Distance
	public static void store_HD(){
		int temp = hammingDistance;
		int bin = 0;
		int i=7;
		while(temp>0){
			bin = temp%2;
			hamming.set(i--,bin);
			temp = temp/2;
		}
		memory.set(r_addr_dec,hamming);
		System.out.print("Memory Content:"+memory.get(r_addr_dec));
	}

	//method for load
	public static void load_seed() {
		ArrayList<Integer> q1 = new ArrayList<Integer>();
		q1 = memory.get(r_addr_dec);
		for(int i=0; i<8; i++){
			seed.set(i,(q1.get(i)));
		}
	}

	//method for add addr_num
	public static void add_address(){
		r_addr_dec = r_addr_decimal(r_addr);
		if(num.get(0)==0){
			for(int i=0; i<8; i++){
				num_dec = (int) (num_dec + num.get(7-i)*Math.pow(2, i));
			}
			r_addr_dec  = r_addr_dec + num_dec;
		}
		else{
			for(int i=0; i<8; i++){
				if(num.get(i)==1){
					num.set(i, 0);
				}
				else if(num.get(i)==0){
					num.set(i, 1);
				}
			}
			for(int i=0; i<8; i++){
				if(num.get(7-i)==1){
					num.set(7-i, 0);
				}
				else{
					num.set(7-i,1);
					break;
				}
			}
			for(int i=0; i<8; i++){
				num_dec = (int) (num_dec + num.get(7-i)*Math.pow(2, i));
			}
			r_addr_dec  = r_addr_dec - num_dec;
		}
	}
}
