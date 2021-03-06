#OutputFormat
- 和InputFormat一样，也实现了对应的功能，比方说Text格式的输出，序列化输出，二进制输出，多文件输出。

###Example：通过序列化的OutputFormat来实现Object的传递，一个job的输出，另一个Job使用。
- 1.序列化后的编码后记录着对象的类名称。方便反序列化使用。和pickle类似。也就是说这和java的序列化有着很大的不同。
- 2.hdfs文件的读写也存在类似的情况，WriteLong, WriteChars等都会加入额外信息。只有WriteBytes是不添加其他辅助信息的写文件方式。
- 3.setMap....必须写，以保证map和reduce的输出不一样。
- 4.另外，multiOutputformat的介绍，参看：http://my.oschina.net/leejun2005/blog/94706

Write to sequence:
```
		job.setOutputFormatClass( SequenceFileOutputFormat.class );
		job.setMapOutputKeyClass(Text.class );//指定输出的KEY的格式
		job.setOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputValueClass( LogWritable.class );							//指定输出的VALUE的格式
		
		
		...
		
		public static class Reduce extends Reducer<Text, Text, NullWritable, LogWritable>
	{
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
		{
			String valueString;
			String out = "";
			
			for ( Text value : values )
			{
				valueString = value.toString();
				out += valueString + "|";
			}
			
			context.write( NullWritable.get(), new LogWritable(out, out, out, 1, 1) );
		}
	}
		
```

read from sequence file:

```
    job.setInputFormatClass(SequenceFileInputFormat.class);
    job.setMapperClass( Map.class );		//调用上面Map类作为Map任务代
    job.setOutputFormatClass( TextOutputFormat.class );
    job.setMapOutputKeyClass(Text.class );//指定输出的KEY的格式
    job.setMapOutputValueClass(Text.class);
    
    ...
    
    public void map(NullWritable key, LogWritable value, Context context) throws IOException, InterruptedException
		{
			try
			{
				context.write(new Text(value.getIp()), new Text(value.getRequest()));
						
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				context.getCounter(Counter.LINESKIP).increment(1);
				return;
			}
		}
```



