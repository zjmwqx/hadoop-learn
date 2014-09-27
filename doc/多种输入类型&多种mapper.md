#输入的多路支持：
- 1.输入多种类型/或者多个路径到不同Mapper
- 2.多个路径输入同一Mapper
- 3.一般用来支持多种文件输入，但是是处理逻辑相同
- 4.只能有一个reducer

###例子：

- 多路径：
```
//in run()
Configuration conf = getConf();
//here:配置inputformat,详见inputformat
conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

Job job = new Job(conf, "MultiInputDemo");								//任务名
job.setJarByClass(MultiInputDemo.class);								//指定Class

//注释掉单输入
//FileInputFormat.setInputPaths( job, new Path(args[0]) );		
//多路径，同类型
MultipleInputs.addInputPath(job, new Path(args[0]), KeyValueTextInputFormat.class);
MultipleInputs.addInputPath(job, new Path(args[1]), KeyValueTextInputFormat.class);

FileOutputFormat.setOutputPath( job, new Path(args[2]) );		//输出路径
```
- 多类型往往需要多个Mapper：
```
//in run()
Configuration conf = getConf();
//here:配置inputformat,详见inputformat
conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");

Job job = new Job(conf, "MultiInputDemo");								//任务名
job.setJarByClass(MultiInputDemo.class);								//指定Class

//注释掉单输入
//FileInputFormat.setInputPaths( job, new Path(args[0]) );		
//多路径，多类型
MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, MapperA.class);
MultipleInputs.addInputPath(job, new Path(args[1]), KeyValueTextInputFormat.class, MapperB.class);

FileOutputFormat.setOutputPath( job, new Path(args[2]) );		//输出路径
```
