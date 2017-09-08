import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;


public class lab6 {
	int checksamecordinate(ArrayList<knight> a,int j){
		int t=-1;
		for(int i=0;i<a.size();i++){
			if(a.get(j).getX()==a.get(i).getX()&&a.get(j).getY()==a.get(i).getY()&&i!=j){
				t=i;
			}
		}
		return t;
	}
	void queencheck(int x,int y,knight a) throws queenoverlap{
		if(a.getX()==x&&a.getY()==y){
			throw new queenoverlap("â€‹QueenFoundException:â€‹ â€‹ Queenâ€‹ â€‹ hasâ€‹ â€‹ beenâ€‹ â€‹ Found.â€‹ â€‹Abort!");
		}
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException,knightoverlap,notcoordinate,queenoverlap{
		PrintWriter aloo=new PrintWriter("output.txt","UTF-8");
		Scanner scan = new Scanner(System.in);
		int nknights=scan.nextInt();
		int no_knights=nknights;
		int iteration=scan.nextInt();
		int qx=scan.nextInt();
		int qy=scan.nextInt();
		ArrayList<knight> array=new ArrayList<knight>();
		while(no_knights>0){
			knight temp=new knight();
			String searchfile="/home/yash/Desktop/Lab_6/src/";
			searchfile=searchfile+Integer.toString(no_knights)+".txt";
			no_knights--;
			File file= new File(searchfile);
			Scanner sc=new Scanner(file);
			temp.setName(sc.next());
			temp.setX(sc.nextInt());
			temp.setY(sc.nextInt());
			String line;
			int linenumber=sc.nextInt();
			line=sc.nextLine();
			while(linenumber>0){
				line=sc.nextLine();
				temp.push(line);
				linenumber--;
			}
			array.add(temp);
		}
		lab6 bleh=new lab6();
		Collections.sort(array);
		int j=1;
		int flag=0;
		while(j<=iteration&&flag==0){
		for(int i=0;i<array.size();i++){
			try{
				String a=array.get(i).pop();
				aloo.println(j+" "+array.get(i).getName()+" "+array.get(i).getX()+" "+array.get(i).getY());
				try{
					array.get(i).coordinatecheck(a);
					String[]  foo= a.split(" ");
					array.get(i).setX(Integer.parseInt(foo[1]));
					array.get(i).setY(Integer.parseInt(foo[2]));
					int fo=bleh.checksamecordinate(array,i);
					if(fo>-1){
						aloo.println("OverlapException: Knights Overlap Exception "+array.get(fo).getName());
						array.remove(fo);
					}
					else{
						int f=0;
						try{
							bleh.queencheck(qx,qy,array.get(i));
							aloo.println("No exception "+array.get(i).getX()+" "+array.get(i).getY() );
							f=1;
						}
						catch(queenoverlap g){
							aloo.println("QueenFoundException: Queen has been Found. Abort!");
							flag=1;
						}
					}
					
				}
				catch(notcoordinate f){
					aloo.println(f.getMessage());
				}
				
			}
			catch(emptystack e){
				aloo.println(j+" "+array.get(i).getName()+" "+array.get(i).getX()+" "+array.get(i).getY());
				aloo.println(e.getMessage());
				array.remove(i);
			}
			
		}
		j++;
	}
		
		
		
		
		aloo.close();
		
	}

}
class notcoordinate extends Exception{
	public notcoordinate(String message){
		super(message);
	}
}
class knightoverlap extends Exception{
	public knightoverlap(String message){
		super(message);
	}
}
class queenoverlap extends Exception{
	public queenoverlap(String message){
		super(message);
	}
}
class emptystack extends Exception{
	public emptystack(String message){
		super(message);
	}
}
class knight implements Comparable<knight> {
	private String name;
	private int x,y;
	private Stack<String> stack;
	knight(){
		stack=new Stack<String>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int compareTo(knight a) {
		return this.getName().compareTo(a.getName());
	}
	public void push(String a){
		stack.push(a);
	}
	public String pop() throws emptystack{
		if(stack.isEmpty()){
			throw new emptystack("StackEmptyException: Stack Empty exception");
		}
		return stack.pop();
	}
	void coordinatecheck(String b) throws notcoordinate{
		String[] array = b.split(" ");
		if(!array[0].equals("Coordinate")){
			throw new notcoordinate("â€‹NonCoordinateException: Not a coordinate Exceptionâ€‹ "+array[1]);
		}
		else{
			this.x=Integer.parseInt(array[1]);
			this.y=Integer.parseInt(array[2]);
		}
	}
}
