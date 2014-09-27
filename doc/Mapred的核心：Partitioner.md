###先介绍一下partitioner:
- 当mapper把输入数据转换格式以后，下一步就是把这些数据分给reducer，在每个reducer内部独立分析并输出。
如何分配给reducer是非常关键的问题。

- 一般的应用，按照key来分配数据，就是默认的hash方式，是不用设置的。
但是很多时候，我们是按照区间段来分析数据的。就需要设置我们自己的partitioner了。

###基本步骤

1.设置reducer的个数：

job.setNumReduceTasks(10);
job.setPartitionerClass(Partition.class);

2.一般的partitioner写法：

```
public int getPartition(Text key, Text value, int numReduceTask) {
			// TODO Auto-generated method stub
			String[] values = value.toString().split("\t");
			int age = Integer.valueOf(values[1]);
			if (age <= 20) {
				return 0;
			}
			// else if the age is between 20 and 50, assign partition 1
			if (age > 20 && age <= 50) {

				return 1 % numReduceTask;
			}
			// otherwise assign partition 2
			else
				return 2 % numReduceTask;
		}
```

###TotalOederPartitioner
- 还有一种常用需求就是要求全局有序。这个也是需要实现自己的partitioner，并借助外部的断点文件划分。hadoop实现了TotalOederPartitioner。
http://www.linuxidc.com/Linux/2013-07/87102.htm 非常好的解释了这种partitioner
- TotalOederPartitioner实现巧妙，基于一个外部排好序的断点辅助文件，分配数据到reducer。这个辅助文件的生成是关键，为了高效生成，采用了sampling的方式。
- 有了辅助文件，每次分配可以二分查找辅助文件，找到合适的reducer。
- 对于字符串值，每次分配就可以按照trie查找。


###还有keyfieldbasedPartition:
- KeyFieldBasedPartitioner,简单的附上解释，用的不多
org.apache.hadoop.mapreduce.lib.partition.KeyFieldBasedPartitioner<K,V>
can be used to partition the intermediate data based on parts of the key. A key can be split into
a set of fields by using a separator string. We can specify the indexes of the set of fields to be
considered when partitioning. We can also specify the index of the characters within fields as well.
