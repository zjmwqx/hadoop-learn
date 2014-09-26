Writable:
```
package hdLearn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class LogWritable implements Writable {
	private Text timeStamp, ip, request;
	private IntWritable responseSize, status;
	LogWritable(){//default constructor should be preserved
		this.timeStamp = new Text();
		this.ip = new Text();
		this.request = new Text();
		this.responseSize = new IntWritable();
		this.status =  new IntWritable();
	}
	LogWritable(String timeStamp, String ip, String request, Integer size,
			Integer status){//default constructor should be preserved
		this.timeStamp = new Text(timeStamp);
		this.ip = new Text(ip);
		this.request = new Text(request);
		this.responseSize = new IntWritable(size);
		this.status =  new IntWritable(status);
	}
	public Text getTimeStamp() {
		return timeStamp;
	}

	public Text getIp() {
		return ip;
	}

	public Text getRequest() {
		return request;
	}

	public IntWritable getResponseSize() {
		return responseSize;
	}

	public IntWritable getStatus() {
		return status;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		timeStamp.readFields(in);
		ip.readFields(in);
		request.readFields(in);
		responseSize.readFields(in);
		status.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		timeStamp.write(out);
		ip.write(out);
		request.write(out);
		responseSize.write(out);
		status.write(out);
	}
	@Override
	public int hashCode() {
	...
	}
	@Override
	public boolean equals(Object obj) {
	...
	}
	
	
}

```

WriteComparable:

```
public class LogWriteComparable implements WritableComparable<LogWriteComparable> {
	private Text timeStamp, ip, request;
	private IntWritable responseSize, status;
	LogWriteComparable(){//default constructor should be preserved
		this.timeStamp = new Text();
		this.ip = new Text();
		this.request = new Text();
		this.responseSize = new IntWritable();
		this.status =  new IntWritable();
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		timeStamp.readFields(in);
		ip.readFields(in);
		request.readFields(in);
		responseSize.readFields(in);
		status.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		timeStamp.write(out);
		ip.write(out);
		request.write(out);
		responseSize.write(out);
		status.write(out);
	}
	@Override
	public int hashCode() {
...
	}
	@Override
	public int compareTo(LogWriteComparable o) {
		// TODO Auto-generated method stub
		if(ip.compareTo(o.ip) == 0)
			return timeStamp.compareTo(o.timeStamp);
		else
			return ip.compareTo(o.ip);
	}
	@Override
	public boolean equals(Object obj) {
...
	}
}
```
