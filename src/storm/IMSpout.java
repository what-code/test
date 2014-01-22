package storm;

import java.util.List;

public class IMSpout {
	
	public static void main(String[] args) {
		new ISpout(){
			public void doSomeThing(){
				
			}

			@Override
			public List<Integer> emit(String s1, int i1) {
				// TODO Auto-generated method stub
				return null;
			};
		};
	}

}
