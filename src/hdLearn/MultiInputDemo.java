package hdLearn;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MultiInputDemo extends Configured implements Tool{
	enum Counter
	{
		LINESKIP
	}
	public static class Map extends Mapper<Text, Text, Text, Text>
	{
		private Text timeStamp = new Text();
		public void map(Text key, Text value, Context context) throws IOException, InterruptedException
		{
			String line = value.toString();
			try
			{
				timeStamp.set(line.split(" ")[0]);
				context.write(timeStamp, key);	
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				context.getCounter(Counter.LINESKIP).increment(1);
				return;
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = getConf();
		//here
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
		
		Job job = new Job(conf, "MultiInputDemo");								//任务名
		job.setJarByClass(MultiInputDemo.class);								//指定Class
		
		//FileInputFormat.setInputPaths( job, new Path(args[0]) );			//输入路径
		MultipleInputs.addInputPath(job, new Path(args[0]), KeyValueTextInputFormat.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), KeyValueTextInputFormat.class);
		
		FileOutputFormat.setOutputPath( job, new Path(args[2]) );		//输出路径
		
		//here
		//job.setInputFormatClass(KeyValueTextInputFormat.class);

		job.setMapperClass( Map.class );								//调用上面Map类作为Map任务代码						//调用上面Reduce类作为Reduce任务代码
		job.setOutputFormatClass( TextOutputFormat.class );				//指定序列化的方法
		job.setOutputKeyClass( Text.class );							//指定输出的KEY的格式
		job.setOutputValueClass( Text.class );							//指定输出的VALUE的格式
		
		job.waitForCompletion(true);
		
		//输出任务完成情况
		System.out.println( "任务名称：" + job.getJobName() );
		System.out.println( "任务成功：" + ( job.isSuccessful()?"是":"否" ) );
		System.out.println( "输入行数：" + job.getCounters().findCounter("org.apache.hadoop.mapred.Task$Counter", "MAP_INPUT_RECORDS").getValue() );
		System.out.println( "输出行数：" + job.getCounters().findCounter("org.apache.hadoop.mapred.Task$Counter", "MAP_OUTPUT_RECORDS").getValue() );
		System.out.println( "跳过的行：" + job.getCounters().findCounter(Counter.LINESKIP).getValue() );

		return job.isSuccessful() ? 0 : 1;
	}
	public static void main(String[] args) throws Exception 
	{
		//记录开始时间
		DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date start = new Date();
		
		//运行任务
		int res = ToolRunner.run(new Configuration(), new MultiInputDemo(), args);

		//输出任务耗时
		Date end = new Date();
		float time =  (float) (( end.getTime() - start.getTime() ) / 60000.0) ;
		System.out.println( "任务开始：" + formatter.format(start) );
		System.out.println( "任务结束：" + formatter.format(end) );
		System.out.println( "任务耗时：" + String.valueOf( time ) + " 分钟" ); 

        System.exit(res);
	}
}
