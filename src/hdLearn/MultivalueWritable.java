package hdLearn;

import org.apache.hadoop.io.GenericWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;


public class MultivalueWritable extends GenericWritable{

	MultivalueWritable(){
		
	}
	//set val
	MultivalueWritable(Writable val)
	{
		set(val);
	}
	@SuppressWarnings("rawtypes")
	private static Class[] CLASSES =
			new Class[]
	{
		IntWritable.class,
		Text.class
	};
	@Override
	protected Class[] getTypes() {
		// TODO Auto-generated method stub
		return CLASSES;
	}

}
