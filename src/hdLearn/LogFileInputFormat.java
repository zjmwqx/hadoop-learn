package hdLearn;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class LogFileInputFormat extends FileInputFormat<LongWritable, LogWritable>{
	
	@Override
	public RecordReader<LongWritable, LogWritable> createRecordReader(
			InputSplit inputSplit, TaskAttemptContext context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return new LogFileRecordReader();
	}
	class LogFileRecordReader extends RecordReader<LongWritable,LogWritable>
	{
		private LineRecordReader line;
		private LogWritable value;
		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			line.close();
		}

		@Override
		public LongWritable getCurrentKey() throws IOException,
				InterruptedException {
			// TODO Auto-generated method stub
			return line.getCurrentKey();
		}

		@Override
		public LogWritable getCurrentValue() throws IOException,
				InterruptedException {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return line.getProgress();
		}

		@Override
		public void initialize(InputSplit inputSplit, TaskAttemptContext context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			line = new LineRecordReader();
			line.initialize(inputSplit, context);
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			if(!line.nextKeyValue())//interator, yield
			{
				return false;
			}
			else
			{
				String[] lineStr = line.getCurrentValue().toString().split(" ");
				value = new LogWritable(lineStr[0],lineStr[1],lineStr[2],
						Integer.valueOf(lineStr[3]),Integer.valueOf(lineStr[4]));
				return true;
			}
		}
		
	}
}
