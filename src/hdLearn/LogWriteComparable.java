package hdLearn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result
				+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogWriteComparable other = (LogWriteComparable) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}

	
	
}
