package storm;
/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-3
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author shengjie.guo
 * 
 * @version 1.0
 */
public class TestVolatile {
	public static void main(String[] args){
		final Volatiles vt = new Volatiles();
		int threads = 5;
		Thread ts[] = new Thread[threads];
		//t1
		for(int i = 0;i < threads;i++){
			ts[i] = new Thread(new Runnable(){
				public void run(){
					System.out.println("-----thread " + Thread.currentThread().getId() + " start----");
					
					//while(!vt.a){
						if(vt.b){
							System.out.println("vt is true--->" + vt.b + "---thread--->" + Thread.currentThread().getId());
							//break;
						}
					//}
				}
			});
			
			ts[i].start();
		}
		
		/*try {
			//Thread.sleep(30000);
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//vt.a = true;

		/*try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//t2
		new Thread(new Runnable(){
			public void run(){
				System.out.println("-----------------------------thread t2 start--------------------");
				vt.b = true;
			}
		}).start();
		
	}
}
