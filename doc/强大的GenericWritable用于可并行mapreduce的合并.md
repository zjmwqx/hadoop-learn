#强大的GenericWritable用于可并行mapreduce的合并
- GenericWritable的子类可以封装不同类型的Writable数据
- 在Mapper中，不同的if条件生成不同的Writable类型，放入GenericWritable的子类对象
- 在reducer中，不同的if条件处理不同的Writable类型。
- 但是最后汇总到同一个输出，一般拼接输出
- ***之所以GenericWritable强大，是因为结合MultipleInputs基本可以实现所有的多Mapper任务和一部分的多Reducer任务的合并***

###例子：
- 先定义GenericWritable子类，：
```
public class MultivalueWritable extends GenericWritable{

	MultivalueWritable(){
		
	}
	//set接口 父类中的value值，并识别class：利用下面定义的getTypes得到classesList，依次用isinstanceof识别
	MultivalueWritable(Writable val)
	{
		set(val);
	}
	//注册不同的Writable类型，最为封装类的成员List
	@SuppressWarnings("rawtypes")
	private static Class[] CLASSES =
			new Class[]
	{
		IntWritable.class,
		Text.class
	};
	//重写getTypes接口，供父类方法调用
	@Override
	protected Class[] getTypes() {
		// TODO Auto-generated method stub
		return CLASSES;
	}

}

```
- 然后在Mapper中
```
String line = value.toString();
String[]  info = line.split(" ");
try
{
	userHostText.set(info[0]);
	requestText.set(info[2]);
	responseSize.set(Integer.valueOf(info[3]));
	context.write(userHostText, new MultivalueWritable(requestText));
	context.write(userHostText, new MultivalueWritable(responseSize));
}
```

- 在reducer中：
```
for(MultivalueWritable val : values)
{
	Writable i = val.get();
	if(i instanceof IntWritable)
	{
		sum = ((IntWritable)i).get() + sum;
	}
	else
	{
		sb.append(((Text)i).toString() + "\t");
	}
}
Text result = new Text();
result.set(sum + "\t" + sb.toString());
context.write(key, result);
```

