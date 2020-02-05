public class ClassNameHere{
	public static int forMax(int[] m){
		int max = 0;
		for(int i = 0; i < m.length; i = i + 1){
			if (m[i] > max){
				max = m[i];
			}
		}
		return max;
	}
	public static void main(String[] args){
		int[] numbers = new int[]{9, 2, 15 , -13, 22, 10, 6};
		System.out.println(forMax(numbers));
	}
}