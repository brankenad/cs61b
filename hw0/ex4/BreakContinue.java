public class BreakContinue{
	public static void windowPosSum(int[] m, int num){
		int max_num = num;
		int max_index = m.length - 1;
		for(int i = 0; i <= max_index; i = i + 1){
			if (num >  max_index - i){
				max_num = max_index - i;
			}
			for(int j = 0; j < max_num; j = j + 1){
				if(m[i] < 0 && j == 0){
				/*need "j == 0", because in the case m[i] >= 0
				but m[i] + m[j] < 0,we will not break*/
				/*another way to avoid this is to use intermediate var
				instead of using m[i] directly.*/
					break;
				} else{
					m[i] = m[i] + m[i + j + 1];
				}
			}
		}
	}
	public static void main(String[] args){
		int[] a = {1, 2, -3, 4, 5, 4};
		int n = 3;
		windowPosSum(a, n);
		System.out.println(java.util.Arrays.toString(a));
	} 
}