#最基本的map程序
- 基本任务计时模板：
  ```
  //记录开始时间
	DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	Date start = new Date();
	
	//运行任务
	int res = ToolRunner.run(new Configuration(), new MapOnly(), args);

	//输出任务耗时
	Date end = new Date();
	float time =  (float) (( end.getTime() - start.getTime() ) / 60000.0) ;
	System.out.println( "任务开始：" + formatter.format(start) );
	System.out.println( "任务结束：" + formatter.format(end) );
	System.out.println( "任务耗时：" + String.valueOf( time ) + " 分钟" ); 
  ```

- 基本工具类继承Tool & configured：

  ![ToolRunner](http://notes-wordpress.stor.sinaapp.com/uploads/2013/05/inherit.png)
```
public class MapOnly extends Configured implements Tool {	
```
- 脏数据行统计
```
  /**  
	 * 计数器
	 * 用于计数各种异常数据
	 */  
	enum Counter 
	{
		LINESKIP,	//出错的行
	}
	try{
	...}
	catch ( java.lang.ArrayIndexOutOfBoundsException e )
	{
		context.getCounter(Counter.LINESKIP).increment(1);	//出错令计数器+1
		return;
	}
```
- 非制表符输出的Mapper:
```
public static class Map extends Mapper<LongWritable, Text, NullWritable, Text> 
	{
		public void map ( LongWritable key, Text value, Context context ) throws IOException, InterruptedException 
		{
			String line = value.toString();				//读取源数据
			try
			{	
				//数据处理
				line...
				Text out = new Text(lineAfterProcess);
				
				context.write( NullWritable.get(), out);	//输出
			}
```
- 调用ToolRunner静态方法run，运行job
```
int res = ToolRunner.run(new Configuration(), new MapOnly(), args);
```
