1.设置：
```
Configuration conf = getConf();
//here
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
