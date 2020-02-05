public class Draw_Tri{
	public static void Draw_Tri_with_N(int n){
		int num = 1;
		int line = 1;
		while(line <= n){
			while (num <= line){
				if (num == line){
					System.out.println("*");
				} else{
					System.out.print("*");
				}
				num = num + 1;
			}
			num = 1;
			line = line + 1;
		}
	}
	public static void main(String[] args){
		Draw_Tri_with_N(10);
	}
}