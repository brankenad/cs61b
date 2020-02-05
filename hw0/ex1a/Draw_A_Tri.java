public class Draw_A_Tri{
	public static void main(String[] args){
		int line = 1;
		int num = 1;
		while(line <= 5){
			while(num <= line){
				if(num != line){
					System.out.print("*");
				} else if (num == line) {
					System.out.println("*");
				}
				num = num + 1;
			}
			num = 1;
			line = line + 1;
		}
	}
}