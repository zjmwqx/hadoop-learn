##KeyValueTextInoutFormat把输入的一行变成key，value形式，分隔符可以设置：
- conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
1.设置：
```
Configuration conf = getConf();
//here for hadoop 1.2.1
conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

Job job = new Job(conf, "InputFormatDemo");								//任务名
job.setJarByClass(InputFormatDemo.class);								//指定Class

FileInputFormat.setInputPaths( job, new Path(args[0]) );			//输入路径
FileOutputFormat.setOutputPath( job, new Path(args[1]) );		//输出路径

//here
job.setInputFormatClass(KeyValueTextInputFormat.class);
```
2.使用:
public void map(Text key, Text value, Context context) throws IOException, InterruptedException

##还有其他很多种TextInputFormat:
- NLineInputFormat也比较常用，每个逻辑分割为多行，需要调用静态方法设置
```
NLineInputFormat.setNumLinesPerSplit(job,50);
```
- SequenceFileInputFormat专门读取压缩数据：
  - SequenceFileAsBinaryInputFormat： key (BytesWritable) and the value (BytesWritable) pairs
  - SequenceFileAsTextInputFormat：key (Text) and the value (Text) pairs
