package dialani.deepanshu.whoswho;

public class Data implements Comparable<Data>
{
	public String d;//date
	public Long h;//highscore
	public Data()
	{
		d="Nothing";
		h=45l;
	}
	public int compareTo(Data comp) 
	{
		 
		long t= ((Data)comp).h; 
 
		return (int)(this.h-t); //ascending
 
		//descending order
		//return compareQuantity - this.quantity;
 
	}	
}
