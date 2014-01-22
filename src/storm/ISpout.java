package storm;

import java.util.List;

public interface ISpout {
	public List<Integer> emit(String s1,int i1);
	
	public void doSomeThing();
}
