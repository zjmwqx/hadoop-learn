#补充：自定义FileInputFormat的实现
- 一般没有这个需求，但是自定义format可以把串联的两个Mapper压缩为一个。
- 可以实现schema映射，就是OEM，在项目庞大的时候适用。

好吧，让我们看看具体的实现：
- 1.第一步，继承并实现unimplemented method：
```
public class LogFileInputFormat extends FileInputFormat<LongWritable, LogWritable>
@Override
public RecordReader<LongWritable, LogWritable> createRecordReader(
		InputSplit inputSplit, TaskAttemptContext context) throws IOException,
		InterruptedException {
	// TODO Auto-generated method stub
	return new LogFileRecordReader();
}
```
- 2.实现私有类,继承自RecordReader，封装LineRecordReader。
- **RecordReader中的nextKeyValue的本质就是一个Generator。**
- **RecordReader的本质就是一个迭代器，类似于数据库中的cursor**

```
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
```
