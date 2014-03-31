package threads;
/**
 * Title:Bar.java
 * 
 * Description:Bar.java
 * 
 * Copyright: Copyright (c) 2014-3-31
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Bar {
	//type1
	public static volatile String status1 = "";
	
	//type2,abc/012
	public volatile int status = 2;
	public Object obj1;
	public Object obj2;
	public Object obj3;
	Bar(){
		obj1 = new Object();
		obj2 = new Object();
		obj3 = new Object();
	}

	public void printA(){
		if(status == 2){
			synchronized(obj1){
				System.out.println("--A--");
				status = 0;
			}
		}
	}
	
	public void printB(){
		if(status == 0){
			synchronized(obj2){
				System.out.println("--B--");
				status = 1;
			}
		}
	}
	
	public void printC(){
		if(status == 1){
			synchronized(obj3){
				System.out.println("%%--C--%%");
				status = 2;
			}
		}
	}
	
	public void printS(){
		System.out.println("----------------------------------------------status-->" + status);
	}

	protected void execute(){
		
	}
	
	public static void main(String[] args){
		//type1(有问题)-------------------------------------------->
		/*Bar bar1 = new Bar1();
		Bar bar2 = new Bar2();
		Bar bar3 = new Bar3();
		
		for(int i = 0;i < 10;i++){
			new BarThread(bar1).start();
		}
		
		for(int i = 0;i < 10;i++){
			new BarThread(bar2).start();
		}
		
		for(int i = 0;i < 10;i++){
			new BarThread(bar3).start();
		}*/
		
		//type2-------------------------------------------->
		final Bar bar = new Bar();
		for(int i = 0;i < 10;i++){
			new Thread(new Runnable(){
				public void run(){
					while(true){
						bar.printA();
					}
				}
			}).start();
		}
		
		for(int i = 0;i < 10;i++){
			new Thread(new Runnable(){
				public void run(){
					while(true){
						bar.printB();
					}
				}
			}).start();
		}
		
		for(int i = 0;i < 10;i++){
			new Thread(new Runnable(){
				public void run(){
					while(true){
						bar.printC();
					}
				}
			}).start();
		}
	}
}
