public class ClassNameHere{
	public static int max(int[] m){
		int length = m.length;
		int max = 0;
		int index = 0;
		while(index < length - 1){
			if (m[index] > max){
				max = m[index];
			}
			index = index + 1;
		}
		return max;
	}
	public static void main(String[] args){
		int[] numbers = new int[]{9, 2, 15 , -13, 22, 10, 6};
		System.out.println(max(numbers));
	}
}